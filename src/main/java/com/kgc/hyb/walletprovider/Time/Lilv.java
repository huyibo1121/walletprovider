package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.service.BillService;
import com.kgc.hyb.walletprovider.service.FinancerateService;
import com.kgc.hyb.walletprovider.service.UserService;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Bill;
import wallet.bean.User;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/8 20:14
 */
@Component
@RestController
public class Lilv {

    @Autowired
    BillService bs;
    @Autowired
    UserService us;
    @Autowired
    RedisUtils utils;
    @Autowired
    FinancerateService frs;

    //  记录用户可计算利息的初始本金，并设置到redis中
    @RequestMapping("/nowAssets")
    //@Scheduled(cron = "0 0 24 * * *")
    public void countqueryassetsById() {
        //获取用户总个数
        int countyonghu = us.countqueryassetsById();
        //utils.set("usercount",countyonghu);
        for (int i = 1; i <= countyonghu; i++) {
            User user = us.queryAssetsById(i);
            //获取用户的现有金额
            BigDecimal assets = user.getAssets();
            String key = Integer.toString(i);
            utils.set(key, assets);
            utils.setTime(key,assets,48);
            System.out.println(assets);
        }
    }

    /**
     * 测试redis的过期时间
     */
    @RequestMapping("aaa")
    public void aaa(){
        List<User> userlist=us.queryAllUsers();
        for(User u:userlist) {
            Integer uid = u.getUid().intValue();
            String key = Integer.toString(uid);
            BigDecimal initialMoney = (BigDecimal) utils.get(key);
            System.out.println(initialMoney + "+++++++++++++++");
        }
    }

    //    从redis中获取初始本金，然后减去单日提现部分，再去计算利息
     @RequestMapping("Lilvjisuan")
//    @Scheduled(cron = "0 0 24 * * *")
    public void UserRate() {
        Bill bill = new Bill();
        //获取用户总个数
         //int countOfUser=Integer.parseInt((utils.get("usercount")).toString());
         //int countOfUser = us.countqueryassetsById();
         //System.out.println(countOfUser+"heiheiheihehehehehehehehe"+countOfUser+"hahahahahahahahahahahaha");

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        //获取当前日期
        String riqi = df.format(date);
        //开始日期
        String startdate = riqi + " 00:00:00";
        //结束日期
        String enddate = riqi + " 23:59:59";


       // for (int i = 1; i <= countOfUser; i++) {
            List<User> userlist=us.queryAllUsers();
            for(User u:userlist){
               Integer uid=u.getUid().intValue();
                System.out.println(uid+"这是我的uid");
            //设置用户的支出总额  并设置为0
            BigDecimal paySum = BigDecimal.ZERO;
            //获取用户当前支出金额的集合
            List<Bill> payList = bs.querynowmoneybill(uid,startdate,4,enddate);
            if (payList != null) {
                for (int n = 0; n < payList.size(); n++) {
                    System.out.println(payList.get(n).getDetailed()+"这是支出集合里面的金额");
                    paySum = (payList.get(n).getDetailed()).add(paySum);
                    //System.out.println("这是我用户"+i+"支出总和"+paySum+"--------++++++++");
                }
                String key = Integer.toString(uid);
                //设置一个BigDecimal类型的初始值  用来接受用户从redis中获取的金额
                BigDecimal initialMoney = null;
                //获取用户金额  从redis中获取
                if (utils.get(key) != null) {
                    try {
                        initialMoney = (BigDecimal) utils.get(key);
                        System.out.println(initialMoney+"这是我用户"+uid+"的初始金额");
                    }catch (ClassCastException e){
                        initialMoney=us.queryAssetsById(uid).getAssets();
                    }
                }

                if (initialMoney.subtract(paySum).compareTo(BigDecimal.valueOf(0)) == 1) {
                    //通过日期查当日  利率   没有获取前一天的利率
                    BigDecimal nowRate = frs.queryFinRatesRate(riqi);
                    //将利率进行四舍五入  保留5位小数  得到新利率
                    BigDecimal newrate = nowRate.divide(BigDecimal.valueOf(1), 5, BigDecimal.ROUND_HALF_UP);
                    System.out.println(newrate+"这是我用户"+uid+"的新利率");
                    System.out.println(utils.get(key)+"getgetgetgetgetgetget");
                    //计算产生的利息
                    BigDecimal nowmoney=initialMoney.subtract(paySum);
                   // System.out.println(nowmoney+"这是我用户"+i+"计算利息的钱数");
                    BigDecimal lixi = (nowmoney).multiply(newrate).divide(BigDecimal.valueOf(366),5,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(0.97));
                    //System.out.println(lixi+"这是我用户"+i+"计算之后的利息");
                    BigDecimal newlixi;
                    //通过uid 查询当前用户的金额
                    User user = us.queryAssetsById(uid);
                    //将查到的金额设置给 oldjine
                    BigDecimal oldjine = user.getAssets();


                    //判断每日产生的利息，
                    if (lixi.compareTo(BigDecimal.valueOf(0))>-1){
                        if (lixi.compareTo(BigDecimal.valueOf(0.01)) < 1){
                            newlixi = BigDecimal.valueOf(0);
                            //新的金额数newjine 为  oldjine加利息
                            BigDecimal newjine = oldjine.add(newlixi);
                            bill.setUid(uid);
                            bill.setNowmoney(newjine);
                            bill.setDetailed(newlixi);
                            bill.setBilltype(3);
                            bill.setBilltime(sdf.format(date));
                            bs.addBill1(bill);
                            us.updateUserAssets(uid,newjine);
                        }else {
                            //新的金额数newjine 为  oldjine加利息
                            BigDecimal newjine = oldjine.add(lixi);
                            bill.setUid(uid);
                            bill.setNowmoney(newjine);
                            bill.setDetailed(lixi);
                            bill.setBilltype(3);
                            bill.setBilltime(sdf.format(date));
                            bs.addBill1(bill);
                            us.updateUserAssets(uid,newjine);
                        }
                    }else{
                        System.out.println("aaaaaaaa");
                    }
                }
            } else {
                String key = Integer.toString(uid);
                BigDecimal initialMoney = BigDecimal.valueOf((int) (utils.get(key)));
                BigDecimal nowRate = frs.queryFinRatesRate(riqi);
                BigDecimal newRate = nowRate.divide(BigDecimal.valueOf(1), 5, BigDecimal.ROUND_HALF_UP);

                BigDecimal nowMoney=initialMoney.subtract(paySum);

                BigDecimal lixi = initialMoney.subtract(nowMoney).multiply(newRate).divide(BigDecimal.valueOf(366),5,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(0.97));
                //通过uid 查询当前用户的金额
                User user = us.queryAssetsById(uid);
                //将查到的金额设置给 oldjine
                BigDecimal oldjine = user.getAssets();
                BigDecimal newjine = oldjine.add(lixi);
                bill.setUid(uid);
                bill.setNowmoney(newjine);
                bill.setDetailed(lixi);
                bill.setBilltype(3);
                bill.setBilltime(sdf.format(date));
                bs.addBill1(bill);
                us.updateUserAssets(uid,newjine);
            }
        }
    }
}
