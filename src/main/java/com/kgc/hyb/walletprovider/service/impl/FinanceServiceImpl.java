package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.BillDao;
import com.kgc.hyb.walletprovider.dao.FinanceDao;
import com.kgc.hyb.walletprovider.dao.TemfinanceDao;
import com.kgc.hyb.walletprovider.dao.UserDao;
import com.kgc.hyb.walletprovider.service.FinanceService;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet.bean.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/12 15:12
 */
@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    FinanceDao fd;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    UserDao ud;
    @Autowired
    BillDao bd;
    @Autowired
    TemfinanceDao td;

    @Override
    public int addFinance(Finance finance) {
        return fd.addFinance(finance);
    }

    @Override
    public List<Finance> queryAllFinance() {
        return fd.queryAllFinance();
    }

    @Override
    public int updateFinance(Finance finance) {
        return fd.updateFinance(finance);
    }

    @Override
    public Finance queryFinanceHistoryByid(Integer uid) {
        return fd.queryFinanceHistoryByid(uid);
    }

    @Override
    public List<Finance> selectFinByname(String finname) {
        return fd.selectFinByname(finname);
    }

    @Override
    public List<Finance> queryFinanceCycle() {
        Object o=redisUtils.get("finance");
        if(o==null){
            List<Finance> list=fd.queryFinanceCycle();
            redisUtils.set("finance",list);
            return list;
        }else{
            return (List<Finance>)o;
        }
    }

    @Override
    public List<Finance> queryFinancereta() {
        Object o=redisUtils.get("finance1");
        if(o==null){
            List<Finance> list=fd.queryFinancereta();
            redisUtils.set("finance1",list);
            return list;
        }else{
            return (List<Finance>)o;
        }
    }

    @Override
    public Page<Finance> queryFinanceByName(Integer pageon, String finname) {
        Page<Finance> p=new Page<Finance>();
        if (pageon==null){
            pageon=1;
        }

        p.setPageon(pageon);
        int count=fd.count(finname);

        p.setCount(count);
        if (count%4==0) {
            p.setPages(count/4);
        }else {
            p.setPages((count/4)+1);
        }
        List<Finance> list=fd.queryFinanceByName(((pageon-1)*4),finname);
        p.setList(list);
        return p;
    }

    @Override
    public List<Finance> queryFinaceByReleasetime() {
        return fd.queryFinaceByReleasetime();
    }

    @Override
    public List<Finance> queryFinanceRateByCycle(Integer cycle) {
        return fd.queryFinanceRateByCycle(cycle);
    }

    @Override
    public Finance queryFinanceById(Integer finid) {
        return fd.queryFinanceById(finid);
    }

    @Override
    public int UpdateFinanceTotal(Integer finid, BigDecimal total) {
        return fd.UpdateFinanceTotal(finid,total);
    }

    //通过银行卡购买定期基金(1:成功；2:失败)
    @Transactional
    @Override
    public synchronized int buyFinance(Integer uid, BigDecimal money,Integer finid,String bankid) {
        Finance finance= fd.queryFinanceById(finid);
        //获取当前该基金的剩余发售金额
        BigDecimal total= finance.getTotal();
        //获取当前该基金的年利率
        BigDecimal rate=finance.getRate();
        //获取该基金的周期
        Integer cycle=finance.getCycle();
        //购买金额大于当前该基金剩余发售金额
        if (money.intValue()>total.intValue()){
            return 2;
        }else {
            BigDecimal countMoney=total.subtract(money);
            int i=fd.UpdateFinanceTotal(finid,countMoney);
            if (i==1){//如果基金总额减少成功；添加一条用户账单记录
                Bill bill=new Bill();
                bill.setUid(uid);
                //设置账单类型(6:定期投资)
                bill.setBilltype(6);
                //设置变更金额
                bill.setDetailed(money);
                //用户当前账户余额
                bill.setNowmoney(ud.queryAssetsById(uid).getAssets());
                //设置账单时间
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
                String billtime = dateFormat.format(date);
                bill.setBilltime(billtime);
                //设置理财产品Id
                bill.setFinid(finid);
                //设置银行卡号
                bill.setBankid(bankid);
                return bd.addBill(bill);

            }else {
                return 2;
            }
        }

    }

    //通过活期余额购买定期基金
    @Transactional
    @Override
    public synchronized int buyFinanceByassets(Integer uid, BigDecimal money, Integer finid) {
        Finance finance= fd.queryFinanceById(finid);
        //获取当前该基金的剩余发售金额
        BigDecimal total= finance.getTotal();
        //获取当前该基金的年利率
        BigDecimal rate=finance.getRate();
        //获取该基金的周期
        Integer cycle=finance.getCycle();
        //购买金额大于当前该基金剩余发售金额
        if (money.intValue()>total.intValue()){
            return 2;
        }else {
            BigDecimal countMoney=total.subtract(money);
            //减少基金库存总额
            int i=fd.UpdateFinanceTotal(finid,countMoney);
            if (i==1){//减少成功，说明购买成功
                //减少用户资产金额
                BigDecimal assets= ud.queryAssetsById(uid).getAssets();
                ud.updateUserAssets(uid,assets.subtract(money));
                //添加账单信息
                Bill bill=new Bill();
                bill.setUid(uid);
                //(7:活期投资)
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
                String billtime = dateFormat.format(date);
                bill.setBilltime(billtime);
                bill.setBilltype(7);
                bill.setDetailed(money);
                bill.setNowmoney(ud.queryAssetsById(uid).getAssets());
                bill.setFinid(finid);
                return bd.addBill(bill);
            }else {
                return 2;
            }

        }
    }


    /**
     * 新增临时订单
     * @param uid
     * @param money
     * @param finid
     * @return
     */
    @Override
    public int GoumaiFinance(Integer uid, BigDecimal money, Integer finid) {
        //获取当前基金的的剩余发售金额
        Finance finance=fd.queryFinanceById(finid);
        BigDecimal total=finance.getTotal();
        //判断用户购买金额是否小于总金额
        if(money.intValue()<total.intValue()){
            BigDecimal countMoney=total.subtract(money);
            //减少基金库存总额
            int i=fd.UpdateFinanceTotal(finid,countMoney);
            if(i==1) {
                Temfinance temfinance = new Temfinance();
                int tid=(int)((Math.random()*9+1)*100000);
                temfinance.setTid(tid);
                temfinance.setUid(uid);
                temfinance.setFinid(finid);
                temfinance.setMoney(money);
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
                String ftime = dateFormat.format(date);
                temfinance.setFtime(ftime);
                return td.addTemfinance(temfinance);
            }else {
                return 123;
            }
        }else {
            //用户申请金额大于余额，直接购买余额
            BigDecimal totals=new BigDecimal(0);
            //减少基金库存总额,基金金额为0
            int i=fd.UpdateFinanceTotal(finid,totals);
            if(i==1) {
                Temfinance temfinance = new Temfinance();
                int tid=(int)((Math.random()*9+1)*100000);
                temfinance.setTid(tid);
                temfinance.setUid(uid);
                temfinance.setFinid(finid);
                temfinance.setMoney(total);
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
                String ftime = dateFormat.format(date);
                temfinance.setFtime(ftime);
                return td.addTemfinance(temfinance);
            }else {
                return 1234;
            }
        }
    }

    @Transactional
    @Override
    public int ZhifuTemfinance(Integer uid,Integer tid) {
        Temfinance temfinance=td.queryTemfinanceById(tid);
        BigDecimal umoney=ud.queryAssetsById(uid).getAssets();
        BigDecimal nowMoney=umoney.subtract(temfinance.getMoney());
        int i=ud.updateUserAssets(uid,nowMoney);
        if (i == 1) {
            Bill bill=new Bill();
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
            String billtime = dateFormat.format(date);
            bill.setUid(uid);
            bill.setBilltime(billtime);
            bill.setBilltype(5);
            bill.setDetailed(temfinance.getMoney());
            bill.setNowmoney(ud.queryAssetsById(uid).getAssets());
            bill.setFinid(temfinance.getFinid());
            bd.addBill(bill);

            return td.deleteTemfinanceById(tid);
        }else {
            //获取理财表的剩余发售金额
            Finance finance=fd.queryFinanceById(temfinance.getFinid());
            BigDecimal total=finance.getTotal();
            //获取临时订单表的操作金额
            BigDecimal TemMoney=temfinance.getMoney();
            //修改finance
            return fd.UpdateFinanceTotal(temfinance.getUid(),TemMoney.add(total));
        }

    }

    @Override
    public List<Finance> queryAllOfFinance() {
        return fd.queryAllOfFinance();
    }


}
