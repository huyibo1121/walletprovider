package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.dao.BackDao;
import com.kgc.hyb.walletprovider.dao.BillDao;
import com.kgc.hyb.walletprovider.dao.CreditHistoryDao;
import com.kgc.hyb.walletprovider.dao.OverdueDao;
import com.kgc.hyb.walletprovider.service.BackService;
import com.kgc.hyb.walletprovider.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Back;
import wallet.bean.Bill;
import wallet.bean.CreditHistory;
import wallet.bean.Overdue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/17 9:03
 */
@RestController
public class Reparyment {
    @Autowired
    BackDao bd;
    @Autowired
    CreditHistoryDao chd;
    @Autowired
    BillService bs;
    @Autowired
    OverdueDao od;

    /**
     * 第一次自动还款
     */
    @RequestMapping("BackMoney")
    @Transactional
    public void BackMoney(){
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        List<Back> backs=bd.queryBackByTime(sdf.format(date));
        List<Back> backs=bd.queryBackByTime("2020-03-10");
        //初始化账单信息
        Bill bill=new Bill();
        bill.setBilltype(7);
        for (Back back:backs){
            //查询贷款记录
            CreditHistory history=chd.queryCreditHistoryById(back.getChid());
            bill.setCreid(history.getCreid());
            bill.setUid(history.getUid().intValue());
            //判断还款方式是否是到期还款
            if (history.getRepaymethod()==0){
                //将还款金额放入账单信息
                bill.setDetailed(back.getBackmonth());
                //判断是否是最后还款日期
                if (history.getPayoff().equals(sdf.format(date))){
                    //归还本金加利息
                    bill.setDetailed(back.getBackmonth().add(back.getLoan()));
                }
            }else {
                //将还款金额放入账单信息
                bill.setDetailed(back.getBackmonth());
            }
            //如果还款成功
            if (bs.addBill(bill)==1){
                bd.updateBackState(back.getBackid(),1);
            }
        }
    }

    /**
     * 第二次自动还款
     */
    @RequestMapping("nextBackMoney")
    @Transactional
    public void nextBackMoney(){
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<Back> backs=bd.queryBackByTime("2020-03-10");
        //初始化账单信息
        Bill bill=new Bill();
        bill.setBilltype(7);
        for (Back back:backs){
            //查询贷款记录
            CreditHistory history=chd.queryCreditHistoryById(back.getChid());
            bill.setCreid(history.getCreid());
            bill.setUid(history.getUid().intValue());
            //判断还款方式是否是到期还款
            if (history.getRepaymethod()==0){
                //将还款金额放入账单信息
                bill.setDetailed(back.getBackmonth());
                //判断是否是最后还款日期
                if (history.getPayoff().equals(sdf.format(date))){
                    //归还本金加利息
                    bill.setDetailed(back.getBackmonth().add(back.getLoan()));
                }
            }else {
                //将还款金额放入账单信息
                bill.setDetailed(back.getBackmonth());
            }

            int n=bs.addBill(bill);
            //如果还款成功
            if (n==1){
                bd.updateBackState(back.getBackid(),1);
            }else if (n==7){
                //初始化逾期记录
                Overdue overdue=new Overdue();
                //存入还款id
                overdue.setBackid(back.getBackid());
                //存入用户id
                overdue.setUid(history.getUid());
                //存入逾期金额
                overdue.setOverduemoney(bill.getDetailed());
                //存入逾期天数
                overdue.setOverduetime("1");
                //存入逾期产生利息
                overdue.setOverdueinterst(overdue.getOverduemoney().multiply(BigDecimal.valueOf(0.0005)));
                //存入逾期还款金额
                overdue.setNextmoney(overdue.getOverduemoney().add(overdue.getOverdueinterst()));
                //添加逾期记录
                od.addOverdue(overdue);
                bd.updateBackState(back.getBackid(),2);
            }
        }
    }

    /**
     * 逾期自动还款
     */
    @RequestMapping("backOverdue")
    @Transactional
    public void backOverdue(){
        List<Back> backList=bd.queryBackByState(2);
        Bill bill=new Bill();
        bill.setBilltype(7);
        for (Back back:backList){
            List<Overdue> overdueList=od.queryByBack(back.getBackid());
            CreditHistory history=chd.queryCreditHistoryById(back.getChid());
            bill.setCreid(history.getCreid());
            bill.setUid(history.getUid().intValue());
            bill.setCreid(history.getCreid());
            for (Overdue overdue:overdueList){
                bill.setDetailed(overdue.getNextmoney());

                int n=bs.addBill(bill);
                //如果还款成功
                if (n==1){
                    bd.updateBackState(back.getBackid(),1);
                    od.deleteOverdue(overdue.getOverdueid());
                }else if (n==7){
                    overdue.setOverduetime(overdue.getOverduetime()+1);
                    overdue.setOverduemoney(overdue.getNextmoney());
                    overdue.setOverdueinterst(overdue.getOverduemoney().multiply(BigDecimal.valueOf(0.0005)));
                    overdue.setNextmoney(overdue.getOverduemoney().add(overdue.getOverdueinterst()));
                    od.updateOverdue(overdue);
                }
            }
        }
    }


}
