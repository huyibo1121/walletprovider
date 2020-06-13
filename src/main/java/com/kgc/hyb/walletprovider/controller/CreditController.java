package com.kgc.hyb.walletprovider.controller;


import com.kgc.hyb.walletprovider.service.CreditService;
import com.kgc.hyb.walletprovider.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Credit;
import wallet.bean.Finance;
import wallet.bean.Page;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/5 16:39
 */
@RestController
public class CreditController {
    @Autowired
    CreditService cs ;
    @Autowired
    FinanceService fs;
    /**
     * @author 胡怡博
     * @version 0.0.1
     * @Description 对credit表进行添加操作
     * @date 2019/11/5 16:39
     */
    @RequestMapping("addCredit")
    public int addCredit(@RequestBody Credit credit){
        int i=cs.addCredit(credit);
        System.out.println(i);
        return credit.getCreid();
    }

    @RequestMapping("updateCredit")
    public int updateCredit(@RequestBody Credit credit){
        return cs.updateCredit(credit);
    }

    @RequestMapping("queryAllCredit")
    public List<Credit> queryAllCredit(){
       return cs.queryAllCredit();
    }

    @RequestMapping("queryLoanRate")
    public List<Credit> queryLoanRate(){
        return cs.queryLoanRate();
    }

    @RequestMapping("selectLoanByname")
    public List<Credit> selectLoanByname(String crename){
        return cs.selectLoanByname(crename);
    }

    /**
     * 按利率查询贷款产品
     * @return
     */
    @RequestMapping("creditrate")
    public List<Credit> querycredit(){
        return cs.queryCredit();
    }


    /**
     * 根据名字查询贷款和理财产品
     * @param crename
     * @return
     */
    @RequestMapping("query")
    public Map query(Integer pageon,String crename) {
        Page<Credit> page=cs.queryByCrename(pageon,crename);
        Page<Finance> page1=fs.queryFinanceByName(pageon,crename);
        Map map = new HashMap();
        map.put("licai",page1);
        map.put("daikuan",page);
        return map;
    }

    /**
     * 根据新品获取贷款产品
     * @return
     */
    @RequestMapping("queryCreditByReleasetime")
    public List<Credit> queryCreditByReleasetime(){
        return cs.queryCreditByReleasetime();
    }

    /**
     * 根据周期获取贷款产品
     * @param cycle
     * @return
     */
    @RequestMapping("queryCreditByMaxCycle")
    public List<Credit> queryCreditByMaxCycle(@RequestParam Integer cycle){
        return cs.queryCreditByMaxCycle(cycle);
    }

    /**
     * 根据贷款额度获得贷款产品
     * @param quotaMin,quotaMax
     * @return
     */
    @RequestMapping("queryCreditByQuoTa")
    public List<Credit> queryCreditByQuoTa(@RequestParam Integer quotaMin,@RequestParam Integer quotaMax){
        return cs.queryCreditByQuoTa(quotaMin,quotaMax);
    }

    @RequestMapping("applyCredit")
    public int applyCredit(@RequestParam("uid") Long uid, @RequestParam("money") BigDecimal money,@RequestParam("creid")  Integer creid,@RequestParam("crid")  Integer crid,@RequestParam("paypwd") Integer paypwd){

        return cs.applyCredit(uid,money,creid,crid,paypwd);
    }

    @RequestMapping("queryAllOfCredit")
    public List<Credit> queryAllOfCredit(){
        return cs.queryAllOfCredit();
    }

//    /**
//     * 添加贷款临时订单
//     * @param uid
//     * @param money
//     * @param creid
//     * @return
//     */
//    @RequestMapping("GoumaiCredit")
//    public int GoumaiCredit(@RequestParam("uid") Integer uid,@RequestParam("money") BigDecimal money,@RequestParam("creid") Integer creid,@RequestParam("crid") Integer crid) {
//        return cs.GoumaiCredit(uid,money,creid,crid);
//    }
//
//
//    @RequestMapping("ZhifuTemcredit")
//    public int ZhifuTemcredit(@RequestParam("uid")Integer uid,@RequestParam("tid") Integer tid) {
//        return cs.ZhifuTemcredit(uid,tid);
//    }


}
