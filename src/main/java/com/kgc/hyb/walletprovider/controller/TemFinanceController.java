package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.FinanceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/11 10:22
 */
@RestController
public class TemFinanceController {
    @Autowired
    FinanceService fs;

    /**
     * 添加临时订单
     * @param uid
     * @param money
     * @param finid
     * @return
     */
    @RequestMapping("GoumaiFinance")
    public int GoumaiFinance(@RequestParam("uid") Integer uid,@RequestParam("money") BigDecimal money,@RequestParam("finid") Integer finid){
        return fs.GoumaiFinance(uid,money,finid);
    }

    /**
     * 进行购买
     * @param uid
     * @param tid
     * @return
     */
    @RequestMapping("ZhifuTemfinance")
    public int ZhifuTemfinance(@RequestParam("uid") Integer uid,@RequestParam("tid") Integer tid){
        return fs.ZhifuTemfinance(uid,tid);
    }
}
