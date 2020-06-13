package com.kgc.hyb.walletprovider.controller;

import com.alibaba.fastjson.JSONObject;
import com.kgc.hyb.walletprovider.service.CreditrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.CreditRates;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 9:41
 */
@RestController
public class CreditrateController {
    @Autowired
    CreditrateService cs;

    @RequestMapping("addRates")
    public String addRates(@RequestBody CreditRates creditRates){
        return cs.addRate(creditRates)+"";
    }


}
