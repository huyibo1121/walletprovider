package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.CreditHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.CreditHistory;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 19:32
 */
@RestController
public class CreditHistoryController {
    @Autowired
    CreditHistoryService cs;

    @RequestMapping("queryLoanHistoryByid")
    public List<CreditHistory> queryLoanHistoryByid(@RequestParam Integer uid){
        return cs.queryLoanHistoryByid(uid);
    }

    /**
     * 贷款
     * @param map
     * @return
     */
    @RequestMapping("addcrdhis")
    public Integer addcrdhis(@RequestBody Map<String,String> map) {
        return cs.addcrdhis(map);
    }

}
