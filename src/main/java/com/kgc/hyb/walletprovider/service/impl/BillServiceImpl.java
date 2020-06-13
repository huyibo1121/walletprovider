package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.BillDao;
import com.kgc.hyb.walletprovider.dao.UserDao;
import com.kgc.hyb.walletprovider.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet.bean.Bill;
import wallet.bean.Page;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 15:53
 */
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillDao bd;
    @Autowired
    UserDao ud;

    @Override
    @Transactional
    public int addBill(Bill bill) {


        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
        String returntime = dateFormat.format(date);
        bill.setBilltime(returntime);
        //获取到当前用户的现有金额
        BigDecimal nowmoney = ud.queryUserDetailByid(bill.getUid()).getAssets();
        //当前用户转入的金额
        BigDecimal detailed = bill.getDetailed();
        switch (bill.getBilltype()) {
            case 3:
                //相加后的金额
                BigDecimal nowdeta = nowmoney.add(detailed);
                /* 返回-1 前者<后者  返回0 前者=后者 返回1 前者>后者 */
                if (nowmoney.compareTo(BigDecimal.valueOf(100000)) <= 0 && nowdeta.compareTo(BigDecimal.valueOf(100000)) <= 0) {
                    //设置给新的明细记录中
                    bill.setNowmoney(nowdeta);
                    BigDecimal assets = nowdeta;
                    ud.updateUserAssets(bill.getUid(), assets);
                } else {
                    String a = "1234";
                    Integer b = Integer.valueOf(a);
                    return b;
                }
                break;
            case 4:

                if (nowmoney.compareTo(detailed) >= 0 && detailed != BigDecimal.valueOf(0)) {
                    BigDecimal nowdeta1 = nowmoney.subtract(detailed);
                    //设置给新的明细记录中
                    bill.setNowmoney(nowdeta1);
                    BigDecimal assets1 = nowdeta1;
                    ud.updateUserAssets(bill.getUid(), assets1);
                } else {
                    String a = "123";
                    Integer b = Integer.valueOf(a);
                    return b;
                }
                break;
        }
        return bd.addBill(bill);


//        switch (bill.getBilltype()){
//            case 3:
//                Date date=new Date();
//                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String billtime=dateFormat.format(date);
//                bill.setBilltime(billtime);
//                //获取用户的现有金额
//                BigDecimal nowmoney=ud.queryUserDetailByid(bill.getUid()).getAssets();
//                //获取当前用户的转入金额
//                BigDecimal detailed=bill.getDetailed();
//
//                //将现有金额和转入金额相加
//                BigDecimal adddeta=nowmoney.add(detailed);
//                //设置给新的记录
//                bill.setNowmoney(adddeta);
//                BigDecimal assets=adddeta;
//                ud.updateUserAssets(bill.getUid(),assets);
//                break;
//            case 4:
//                Date date1=new Date();
//                SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String billtime1=dateFormat1.format(date1);
//                bill.setBilltime(billtime1);
//                //获取用户的现有金额
//                BigDecimal nowmoney1=ud.queryUserDetailByid(bill.getUid()).getAssets();
//                //获取当前用户的转出金额
//                BigDecimal detailed1=bill.getDetailed();
//                //将现有金额和转入金额相减
//                BigDecimal adddeta1=nowmoney1.subtract(detailed1);
//                //设置给新的记录
//                bill.setNowmoney(adddeta1);
//                BigDecimal assets1=adddeta1;
//                ud.updateUserAssets(bill.getUid(),assets1);
//                break;
//        }
//        return bd.addBill(bill);
    }

    @Override
    public int addBill1(Bill bill) {
        return bd.addBill1(bill);
    }

    @Override
    public Page<Bill> queryByIdBill(Integer uid, Integer pageon) {
        Page<Bill> page = new Page<Bill>();
        if (pageon == null || pageon <= 0) {
            pageon = 1;
        }
        page.setPageon(pageon);
        int count = bd.countqueryByIdBill(uid);
        page.setCount(count);
        if (count % 5 == 0) {
            page.setPages(count / 5);
        } else {
            page.setPages((count / 5) + 1);
        }
        page.setList(bd.queryByIdBill(uid, (pageon - 1) * 5));
        return page;
    }

    @Override
    public int countqueryByIdBill(Integer uid) {
        return bd.countqueryByIdBill(uid);
    }

    @Override
    public List<Bill> querynowmoneybill(Integer uid, String startdate, Integer billtype, String enddate) {
        return bd.querynowmoneybill(uid,billtype,startdate,enddate);
    }


    @Override
    public List<Bill> queryBillByUidAndStartEnd(Map<String,String> map) {
        Long uid=Long.parseLong(map.get("uid"));
        String starttime=map.get("starttime");
        String endtime=map.get("endtime");
        Integer billtype;
        if (map.get("billtype")==null){
            billtype=null;
        }else {
            billtype=Integer.parseInt(map.get("billtype"));
        }
        Integer pageon=Integer.parseInt(map.get("pageon"));
        return bd.queryBillByUidAndStartEnd(uid,starttime,endtime,billtype,pageon);
    }
}
