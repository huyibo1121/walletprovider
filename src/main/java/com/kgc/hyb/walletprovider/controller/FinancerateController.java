package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.FinancerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.FinanceRates;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 9:46
 */
@RestController
public class FinancerateController {

    @Autowired
    FinancerateService fs;

    /**
     * 获取活期利率走势
     * @param limit
     * @return
     */
    @RequestMapping("queryAllFinancereta")
    public List<FinanceRates> queryAllFinancereta(@RequestParam String limit){
        System.out.println(limit+"+++++++++++++++++++++");
        Integer limit1=Integer.parseInt(limit);
        return fs.queryAllFinance(limit1);
    }

    @RequestMapping("queryAllOfFinanceRates")
    public List<FinanceRates> queryAllOfFinanceRates(){
        return fs.queryAllOfFinanceRates();
    }
}
