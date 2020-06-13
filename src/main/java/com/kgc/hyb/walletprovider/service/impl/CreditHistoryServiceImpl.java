package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.BackDao;
import com.kgc.hyb.walletprovider.dao.CreditDao;
import com.kgc.hyb.walletprovider.dao.CreditHistoryDao;
import com.kgc.hyb.walletprovider.dao.UserDao;
import com.kgc.hyb.walletprovider.service.BillService;
import com.kgc.hyb.walletprovider.service.CreditHistoryService;
import com.kgc.hyb.walletprovider.service.CreditrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet.bean.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 19:31
 */
@Service
public class CreditHistoryServiceImpl implements CreditHistoryService {
    @Autowired
    CreditHistoryDao cd;
    @Autowired
    UserDao ud;
    @Autowired
    BillService bs;
    @Autowired
    CreditDao creditDao;
    @Autowired
    CreditrateService crs;
    @Autowired
    BackDao bd;
    @Override
    public List<CreditHistory> queryLoanHistoryByid(Integer uid) {
        return cd.queryLoanHistoryByid(uid);
    }


    /**
     * 贷款(最新的)
     * @param map
     * @return
     */
    @Override
    @Transactional
    public Integer addcrdhis(Map<String,String> map) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dqtime = sdf.format(new Date());
        //初始化贷款利率记录表
        CreditHistory creditHistory = new CreditHistory();
        //初始化账单记录表
        Bill bill = new Bill();
        //获取用户要贷款的金额

        String cremon =  map.get("money");
        BigDecimal cremoney = new BigDecimal(cremon);
        //获取前台传的贷款利率
        String crid = map.get("crid");
        //获取贷款利率对象  通过crid
        CreditRates creditRates = crs.queryCreditRatesByid(Integer.valueOf(crid));
        //获取用户贷款的那个产品  通过creid
        Credit credit =creditDao.queryCreditByCrid(creditRates.getCreid()) ;
        System.out.println(credit.getMaxcycle());
        //获取这个贷款产品的每个人购买最大限额
        BigDecimal quota =new BigDecimal(credit.getQuota());
        //获取用户信息
        User user = ud.queryUserDetailByid(Integer.parseInt(map.get("uid")));
        System.out.println(user.toString()+"--------");
        System.out.println("paypwd:"+user.getPaypwd());
        System.out.println(user.getPaypwd().equals(Integer.parseInt(map.get("paypwd")))+"++++++++");
        //获取用户贷款的周期
        Integer zhouqi = creditRates.getCycle();
        System.out.println(zhouqi+"这是我的贷款天数");
        //用户贷款的时长   单位：月    默认30一个月
        Integer month = zhouqi/30;
        System.out.println(month+"这是我的贷款月数");
        if(zhouqi%30!=0){
            month=month+1;
        }
        //获取前台传来的密码  与用户支密码比较
        if (user.getPaypwd().equals(Integer.parseInt(map.get("paypwd")))){
            System.out.println("用户支付密码正确    进入");
            //判断贷款额度是否合理
            if (quota.compareTo(cremoney)>=0){
                System.out.println("贷款额度正常   进入");
                //全部满足才能进入
                bill.setUid(user.getUid().intValue());
                bill.setBilltype(6);
                bill.setDetailed(cremoney);
                bill.setNowmoney(cremoney.add(user.getAssets()));
                bill.setCreid(credit.getCreid());
                if (bs.addBill(bill)==1){
                    creditHistory.setCreid(credit.getCreid());
                    creditHistory.setUid(user.getUid());
                    creditHistory.setStarttime(dqtime);
                    creditHistory.setCremoney(cremoney);
                    creditHistory.setCycle(credit.getMaxcycle());
                    //获取贷款的还款方式
                    Integer repaymethod = Integer.valueOf(map.get("repaymethod"));
                    creditHistory.setRepaymethod(repaymethod);
                    creditHistory.setCrid(creditRates.getCrid());

                    sdf=new SimpleDateFormat("yyyy-MM-dd");
                    calendar.add(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,10);
                    date=calendar.getTime();
                    creditHistory.setPayoff(sdf.format(date));
                    cd.addCreditHistory(creditHistory);
                    //添加还款记录表   根据周期

                    //Integer zhouqi = creditRates.getCycle();

                   //添加还款记录表
                    Back back=new Back();
                    for(int i=1;i<=month;i++){
                        System.out.println("这是cremoney"+creditHistory.getCremoney());
                        back.setChid(Long.valueOf(creditHistory.getChid()));
                        back.setLoan(creditHistory.getCremoney());
                        //计算总还款金额
                        back.setLoanback(creditHistory.getCremoney().add(creditRates.getRate().multiply(creditHistory.getCremoney())));
                        calendar.add(Calendar.MINUTE,1);
                        calendar.set(Calendar.DAY_OF_MONTH,10);
                        date=calendar.getTime();
                        back.setBacktime(sdf.format(date));
                        if(repaymethod==1){
                            System.out.println(creditHistory.getCremoney().add(creditRates.getRate().multiply(creditHistory.getCremoney())).divide(BigDecimal.valueOf(month))+"-------------------");
                            back.setBackmonth(creditHistory.getCremoney().add(creditRates.getRate().multiply(creditHistory.getCremoney())).divide(BigDecimal.valueOf(month)));
                        }else {
                            if(i!=month){
                                back.setBackmonth(creditRates.getRate().multiply(creditHistory.getCremoney()).divide(BigDecimal.valueOf(month)));
                            }else {
                                back.setBackmonth(creditRates.getRate().multiply(creditHistory.getCremoney()).divide(BigDecimal.valueOf(month)).add(creditHistory.getCremoney()));
                            }
                        }
                        back.setBackstate(0);
                        bd.addBack(back);
                    }
                    //获取用户当前的金额
                    BigDecimal assets = ud.queryAssetsById(Integer.parseInt(map.get("uid"))).getAssets();
                    BigDecimal money=new BigDecimal(map.get("money"));
                    //将贷款的金额进入用户的账户
                    ud.updateUserAssets(Integer.parseInt(map.get("uid")), assets.add(money));
                }else{
                    System.out.println("添加账单记录失败");
                    return 2;
                }
            }else {
                System.out.println("贷款额度超出");
                return 2;
            }
        }else{
            System.out.println("用户支付密码错误");
            return 2;
        }
        return 1;

    }
}
