package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.*;
import com.kgc.hyb.walletprovider.service.CreditService;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet.bean.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/5 16:36
 */
@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    CreditDao cd;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    UserDao ud;
    @Autowired
    BillDao bd;
    @Autowired
    CreditHistoryDao chd;
    @Autowired
    CreditrateDao crd;
    @Autowired
    TemcreditDao td;
    @Autowired
    BackDao backDao;
    @Override
    public int addCredit(Credit credit) {
        return cd.addCredit(credit);
    }

    @Override
    public List<Credit> queryAllCredit() {
        return cd.queryAllCredit();
    }

    @Override
    public int updateCredit(Credit credit) {
        return cd.updateCredit(credit);
    }

    @Override
    public List<Credit> queryLoanRate() {
        return cd.queryLoanRate();
    }

    @Override
    public List<Credit> selectLoanByname(String crename) {
        return cd.selectLoanByname(crename);
    }

    @Override
    public List<Credit> queryCredit() {
        Object o=redisUtils.get("credit");
        if(o==null){
            List<Credit> list=cd.queryCredit();
            redisUtils.set("credit",list);
            return list;
        }else{
            return (List<Credit>)o;
        }
    }

    @Override
    public Page<Credit> queryByCrename(Integer pageon, String crename) {
        Page<Credit> p=new Page<Credit>();
        if (pageon==null){
            pageon=1;
        }

        p.setPageon(pageon);
        int count=cd.count(crename);

        p.setCount(count);
        if (count%4==0) {
            p.setPages(count/4);
        }else {
            p.setPages((count/4)+1);
        }
        List<Credit> list=cd.queryByCrename(((pageon-1)*4),crename);
        p.setList(list);
        return p;
    }

    @Override
    public List<Credit> queryCreditByReleasetime() {
        return cd.queryCreditByReleasetime();
    }

    @Override
    public List<Credit> queryCreditByMaxCycle(Integer cycle) {
        return cd.queryCreditByMaxCycle(cycle);
    }

    @Override
    public List<Credit> queryCreditByQuoTa(Integer quotaMin, Integer quotaMax) {
        return cd.queryCreditByQuoTa(quotaMin,quotaMax);
    }

    /**
     * 申请贷款1
     * @param uid
     * @param money
     * @param creid
     * @param crid
     * @return
     */
    @Override
    public int applyCredit(Long uid, BigDecimal money, Integer creid, Integer crid,Integer paypwd) {
        Credit credit=cd.queryCreditByCrid(creid);
        Integer quota=credit.getQuota();
        Integer userpaypwd=ud.queryUserDetailByid(uid.intValue()).getPaypwd();
        BigDecimal u=ud.queryAssetsById(uid.intValue()).getAssets();
        System.out.println(userpaypwd+"+++++++"+paypwd+"+++"+u);
        if(userpaypwd.equals(paypwd)) {
            if (quota > money.intValue()) {
                //获取用户当前的金额
                BigDecimal assets = ud.queryAssetsById(uid.intValue()).getAssets();
                //将贷款的金额进入用户的账户
                ud.updateUserAssets(uid.intValue(), assets.add(money));
                //添加账单信息
                Bill bill = new Bill();
                bill.setUid(uid.intValue());
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
                String billtime = dateFormat.format(date);
                bill.setBilltime(billtime);
                bill.setBilltype(6);
                bill.setDetailed(money);
                bill.setNowmoney(ud.queryAssetsById(uid.intValue()).getAssets());
                bill.setCreid(creid);
                bd.addBill(bill);
                //添加历史购买记录
                CreditHistory creditHistory = new CreditHistory();
                creditHistory.setCreid(creid);
                creditHistory.setCrid(crid);
                creditHistory.setUid(uid);
                creditHistory.setStarttime(cd.queryCreditByCrid(creid).getReleasetime());
                creditHistory.setCremoney(money);
                creditHistory.setCycle(crd.queryCreditRatesByCrid(crid).getCycle());
                creditHistory.setRepaymethod(cd.queryCreditByCrid(creid).getRepaytype());
                chd.addCreditHistory(creditHistory);
                //还款记录表插入数据


                return 0;
            } else {
                return 123;
            }
        }else {
            return 456;
        }
    }

    @Override
    public List<Credit> queryAllOfCredit() {
        return cd.queryAllOfCredit();
    }

//
//    /**
//     * 添加贷款临时订单
//     * @param uid
//     * @param money
//     * @param creid
//     * @return
//     */
//    @Override
//    public int GoumaiCredit(Integer uid, BigDecimal money, Integer creid,Integer crid) {
//        //获取当前贷款允许贷款的额度
//        Credit credit=cd.queryCreditByCrid(creid);
//        Integer quota=credit.getQuota();
//        //判断用户贷款的金额是否小于该贷款的额度
//        if(quota>money.intValue()){
//            //添加贷款订单
//            Temcredit temcredit=new Temcredit();
//            Date date = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
//            String ctime = dateFormat.format(date);
//            int tid=(int)((Math.random()*9+1)*100000);
//            temcredit.setTid(tid);
//            temcredit.setUid(uid);
//            temcredit.setCreid(creid);
//            temcredit.setMoney(money);
//            temcredit.setCtime(ctime);
//            temcredit.setCrid(crid);
//            System.out.println("monry:"+temcredit.getMoney());
//            System.out.println("time"+temcredit.getCtime());
//            return td.addTemcredit(temcredit);
//        }
//        return 123;
//    }
//
//    /**
//     * 申请贷款2
//     * @param uid
//     * @param tid
//     * @return
//     */
//    @Transactional
//    @Override
//    public int ZhifuTemcredit(Integer uid, Integer tid) {
//        Temcredit temcredit=td.queryTemcreditById(tid);
//        BigDecimal unomey=ud.queryAssetsById(uid).getAssets();
//        BigDecimal nowmoney=unomey.add(temcredit.getMoney());
//        int i=ud.updateUserAssets(uid,nowmoney);
//        if(i==1){
//            //添加账单信息
//            Bill bill=new Bill();
//            Date date = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String billtime = dateFormat.format(date);
//            bill.setUid(uid);
//            bill.setBilltime(billtime);
//            bill.setBilltype(6);
//            bill.setDetailed(temcredit.getMoney());
//            bill.setNowmoney(ud.queryAssetsById(uid.intValue()).getAssets());
//            bill.setCreid(temcredit.getCreid());
//            bd.addBill(bill);
//            //添加历史购买记录
//            CreditHistory creditHistory=new CreditHistory();
//            creditHistory.setCreid(temcredit.getCreid());
//            creditHistory.setCrid(1);
//            creditHistory.setUid(uid.longValue());
//            creditHistory.setStarttime(cd.queryCreditByCrid(temcredit.getCreid()).getReleasetime());
//            creditHistory.setCremoney(temcredit.getMoney());
//            creditHistory.setCycle(crd.queryCreditRatesByCrid(temcredit.getCrid()).getCycle());
//            creditHistory.setRepaymethod(cd.queryCreditByCrid(temcredit.getCreid()).getRepaytype());
//            chd.addCreditHistory(creditHistory);
//            return td.deleteTemcredit(tid);
//        }
//        return 123;
//    }
}
