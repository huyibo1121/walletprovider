package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.*;
import com.kgc.hyb.walletprovider.service.BackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/16 15:42
 */
@Service
public class BackServiceImpl implements BackService {
    @Autowired
    BackDao bd;

    @Autowired
    UserDao ud;

    @Autowired
    CreditHistoryDao chd;

    @Autowired
    OverdueDao od;

    @Autowired
    BillDao billDao;

    /**
     * 手动还款
     * @param map
     * @return
     */
    @Override
    public int shoudongBack(Map<String, String> map) {
        Long chid= Long.valueOf(map.get("chid"));
        String type=map.get("type");
        String paypwd=map.get("paypwd");

        CreditHistory history=chd.queryCreditHistoryById(chid);
        Long uid= Long.valueOf(history.getUid());
        User user=ud.queryUserDetailByid(uid.intValue());
        //判断支付密码
        if (!user.getPaypwd().equals(Integer.parseInt(paypwd))){
            return 0;
        }
        Bill bill=new Bill();
        List<Back> backList=bd.queryBackByChid(chid);
        BigDecimal sum=BigDecimal.valueOf(0);
        System.out.println("这是我相加前的sum"+sum);
        for (Back back:backList){
            if (back.getBackstate()==0){
                sum=sum.add(back.getBackmonth());
                System.out.println("这是我的backmonth"+back.getBackmonth());
                System.out.println("这是我相加过的sum"+sum);
                bill.setDetailed(sum);
                BigDecimal assets=user.getAssets();
                System.out.println(assets+"这是我的用户余额");
                if (assets.intValue()>sum.intValue()){
                    System.out.println(assets.subtract(sum)+"lalalalalalalalal");
                    System.out.println(assets+"lalalalalalalalala");
                    bill.setNowmoney(assets.subtract(sum));
                    ud.updateUserAssets(uid.intValue(),assets.subtract(sum));
                }else {
                    return 00;
                }
            }else if (back.getBackstate()==2){
                List<Overdue> overdueList=od.queryByBack(back.getBackid());
                for (Overdue overdue:overdueList){
                   sum= sum.add(overdue.getNextmoney());
                    bill.setDetailed(sum);
                    BigDecimal assets=user.getAssets();
                    if (assets.intValue()>sum.intValue()){
                        ud.updateUserAssets(uid.intValue(),assets.subtract(sum));
                    }else {
                        return 00;
                    }
                }
            }
            bd.updateBackState(back.getBackid(),1);
        }
        bill.setUid(uid.intValue());
        bill.setCreid(history.getCreid());
        //银行卡支付
        if ("2".equals(type)){
            bill.setBilltype(8);
            bill.setBankid(map.get("bankid"));
        }else if ("3".equals(type)){
            //支付宝支付
            bill.setBilltype(9);
        }
        //余额支付
        bill.setBilltype(10);

        return billDao.addBill(bill);
    }
}

