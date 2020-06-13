package com.kgc.hyb.walletprovider.controller;

import com.alibaba.fastjson.JSONObject;
import com.kgc.hyb.walletprovider.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Finance;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/12 15:45
 */
@RestController
public class FinanceController {
    @Autowired
    FinanceService fs;

    @RequestMapping("addFinance")
    public int addFinance(@RequestBody Finance finance){
        return fs.addFinance(finance);
    }

    @RequestMapping("queryAllFinance")
    public List<Finance> queryAllFinance(){
        return fs.queryAllFinance();
    }

    @RequestMapping("updateFinance")
    public int updateFinance(@RequestBody Finance finance){
        return fs.updateFinance(finance);
    }

    @RequestMapping("queryFinanceHistoryByid")
    public Finance queryFinanceHistoryByid(@RequestParam Integer uid){
        return fs.queryFinanceHistoryByid(uid);
    }

    @RequestMapping("selectFinByname")
    public List<Finance> selectFinByname(@RequestParam String finname){
        return fs.selectFinByname(finname);
    }

    /**
     * 根据热度查询理财产品
     * @return
     */
    @RequestMapping("finanace")
    public List<Finance> queryFinanceCycle(){
        return fs.queryFinanceCycle();
    }

    /**
     * 根据利率查询理财产品
     * @return
     */
    @RequestMapping("finanacerate")
    public List<Finance> queryFinancereta(){
        return fs.queryFinancereta();
    }

    /**
     * 根据新品查询理财产品
     * @return
     */
    @RequestMapping("queryFinaceByReleasetime")
    public List<Finance> queryFinaceByReleasetime(){
        return fs.queryFinaceByReleasetime();
    }

    /**
     * 根据周期查询利率高的基金
     * @param cycle
     * @return
     */
    @RequestMapping("queryFinanceRateByCycle")
    public List<Finance> queryFinanceRateByCycle(@RequestParam("cycle") Integer cycle){
        return fs.queryFinanceRateByCycle(cycle);
    }

    /**
     * 根据id查询理财产品详情
     * @param finid
     * @return
     */
    @RequestMapping("queryFinanceById")
    public Finance queryFinanceById(@RequestParam("finid") Integer finid){
        return fs.queryFinanceById(finid);
    }


    @RequestMapping("buyFinance")
    public int buyFinance(@RequestParam Integer uid, @RequestParam BigDecimal money, @RequestParam Integer finid, @RequestParam String bankid){
        return fs.buyFinance(uid,money,finid,bankid);
    }

    @RequestMapping("buyFinanceByassets")
    public int buyFinanceByassets(@RequestParam Integer uid, @RequestParam BigDecimal money, @RequestParam Integer finid){
        return fs.buyFinanceByassets(uid,money,finid);
    }

    @RequestMapping("queryAllOfFinance")
    public List<Finance> queryAllOfFinance() {
        return fs.queryAllOfFinance();
    }

    /**
     * 根据名字查询理财产品
     * @param finname
     * @return
     */
//    @RequestMapping("queryAllByName")
//    public List<Finance> queryAllByName(@RequestParam("crename") String finname) {
//        return fs.queryFinanceByName(finname);
//    }
}
