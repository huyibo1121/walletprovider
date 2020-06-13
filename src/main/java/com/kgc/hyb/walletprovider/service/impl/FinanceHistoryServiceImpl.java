package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.FinanceHistoryDao;
import com.kgc.hyb.walletprovider.dao.TemfinanceDao;
import com.kgc.hyb.walletprovider.dao.UserDao;
import com.kgc.hyb.walletprovider.service.BillService;
import com.kgc.hyb.walletprovider.service.FinanceHistoryService;
import com.kgc.hyb.walletprovider.service.FinanceService;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet.bean.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/12 10:36
 */
@Service
public class FinanceHistoryServiceImpl implements FinanceHistoryService {
    @Autowired
    FinanceHistoryDao fhd;
    @Autowired
    BillService bs;
    @Autowired
    FinanceService fs;
    @Autowired
    UserDao ud;
    @Autowired
    TemfinanceDao tfd;
    @Autowired
    RedisUtils redisUtil;

    @Override
    public List<FinanceHistory> queryFinanceHistoryById(Integer uid) {
        System.out.println("uid="+uid);
        return fhd.queryFinanceHistoryById(uid);
    }

    /**
     * 订单支付
     * @param map
     * @return
     */
    @Override
    @Transactional
    public int addFinanceHistory(Map<String,String> map) {
            //获取临时订单
            Object redisHistory1 = redisUtil.get(map.get("key"));
            Temfinance temfinance=(Temfinance) redisHistory1;
            System.out.println(temfinance.getUid()+"+++++++++++++++++++++++++++++++-");
            //拿到金额，然后加锁并判断加锁是否成功
            BigDecimal nowMoney=temfinance.getMoney();
            //获取理财产品信息
            Finance finance = fs.queryFinanceById(temfinance.getFinid());

            while (!redisUtil.lock(finance.getTotal().toString())) {
             try{
                Thread.sleep(20);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            FinanceHistory redisHistory=new FinanceHistory();
            //获取支付密码
            Integer paypwd = Integer.parseInt(map.get("paypwd"));
            //获取支付类型
            String type = map.get("type");
            //获取用户信息
            System.out.println(temfinance.getUid().intValue() + "+++++++++++++++++++++++");
            User user = ud.queryUserDetailByid(temfinance.getUid().intValue());

            //判断甚于金额是否大于购买金额
            if(finance.getTotal().intValue()<temfinance.getMoney().intValue()){
                redisUtil.unLock(finance.getTotal().toString());
                redisUtil.delete(finance.getTotal().toString());
                return 0;
            }

//            System.out.println(user.getPaypwd()+"userpwd");
//            System.out.println(paypwd+"paypwd");
//            System.out.println(user.getPaypwd().equals(paypwd));
            //判断支付密码
            if (!user.getPaypwd().equals(paypwd)) {
                redisUtil.unLock(finance.getTotal().toString());
                redisUtil.delete(finance.getTotal().toString());
                return 0;
            }
            //获取当前时间
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            redisHistory.setDealtime(sdf.format(date));
            //当前时间加上理财周期计算出预计到账时间
            calendar.add(Calendar.DAY_OF_MONTH, finance.getCycle());
            date = calendar.getTime();
            System.out.println("预计到账时间" + sdf.format(date));
            redisHistory.setExpect(sdf.format(date));
            redisHistory.setUid(temfinance.getUid().longValue());
            redisHistory.setFinid(temfinance.getFinid());
            redisHistory.setDealmoney(temfinance.getMoney());
            //计算预计收益利息
            redisHistory.setProfit(temfinance.getMoney().multiply(finance.getRate()).multiply(BigDecimal.valueOf(0.97)));
            fhd.addFinanceHistory(redisHistory);
            Bill bill = new Bill();
            //1、余额支付
            if ("1".equals(type)) {
                //将信息添加到账单信息
                bill.setBilltype(2);
                bill.setDetailed(nowMoney);
                bill.setUid(temfinance.getUid());
                bill.setFinid(temfinance.getFinid());
                int i=bs.addBill(bill);
                //如资产不满足购买则失败
                if (i == 2) {
                    redisUtil.unLock("finance" + temfinance.getFinid());
                    redisUtil.delete(map.get("key"));
                }
                //修改用户余额
                BigDecimal money=temfinance.getMoney();
                BigDecimal Assets=ud.queryAssetsById(temfinance.getUid()).getAssets();
                BigDecimal newAssets=Assets.subtract(money);
                int a=ud.updateUserAssets(temfinance.getUid(),newAssets);
                if (i>0&&a>0){
                    //发送消息队列
                    //省略
                }
            } else if ("2".equals(type)) {
                String bankid = map.get("bankid");
                redisHistory.setBankid(bankid);
                bill.setBilltype(8);//银行卡支付
                bill.setDetailed(nowMoney);
                bill.setUid(temfinance.getUid());
                bill.setFinid(temfinance.getFinid());
                bill.setBankid(redisHistory.getBankid());
                bs.addBill(bill);
            } else if ("3".equals(type)) {
                bill.setBilltype(9);//支付宝支付
                bill.setDetailed(nowMoney);
                bill.setUid(temfinance.getUid());
                bill.setFinid(temfinance.getFinid());
                bs.addBill(bill);
            }


            BigDecimal money=temfinance.getMoney();
            BigDecimal total = finance.getTotal().subtract(money);
            //修改库存金额
            fs.UpdateFinanceTotal(temfinance.getFinid(),total);
            return 1;

    }

    /**
     * 添加临时订单，时长为15分钟
     * @param map
     * @return
     */
    @Override
    public Set<Object> RedisFinanceHistory(Map<String,String> map) {
        Temfinance temfinance=new Temfinance();
        temfinance.setUid(Integer.parseInt(map.get("uid")));
        temfinance.setFinid(Integer.parseInt(map.get("finid")));
        BigDecimal money=new BigDecimal(map.get("money"));
        //得到基金的总购买额度
        Finance finance=fs.queryFinanceById(Integer.parseInt(map.get("finid")));
        temfinance.setMoney(money);
        redisUtil.setTemfinance(temfinance);
        String key = map.get("uid");
        Set<Object> set = redisUtil.getTemfinance(Long.valueOf(key));
        Set<Object> set1 = new HashSet<Object>();

        //判断用户购买金额是否大于总额度
        if(finance.getTotal().intValue()>money.intValue()) {
            for (Object getset : set) {
                Temfinance temfinance1 = (Temfinance) redisUtil.get(getset.toString());
                System.out.println(temfinance1.getUid() + "++" + temfinance1.getMoney() + "hahahahahahaha");
                temfinance1.setFtime(getset.toString());
                set1.add(temfinance1);

            }
            return set1;
        }else {
            for (Object getset : set) {
                Temfinance temfinance1 = (Temfinance) redisUtil.get(getset.toString());
                System.out.println(temfinance1.getUid() + "++" + temfinance1.getMoney() + "hahahahahahaha");
                temfinance1.setFtime("余额不足了");
                set1.add(temfinance1);
            }
            return set1;
        }
    }

    @Override
    public int addFinanceHistory(FinanceHistory redisHistory) {
        return fhd.addFinanceHistory(redisHistory);
    }

    /**
     * 查询用户所有临时订单
     * @param uid
     * @return
     */
    @Override
    public Set<Object> queryRedisFinanceHistory(Long uid) {
        return redisUtil.getTemfinance(uid);
    }
}
