package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.FinanceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.FinanceHistory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/12 11:10
 */
@RestController
public class FinanceHistoryController {
    @Autowired
    FinanceHistoryService fhs;

    /**
     * 根据用户id查询理财记录
     * @param uid
     * @return
     */
    @RequestMapping("queryFinanceHistoryById")
    public List<FinanceHistory> queryFinanceHistoryById(@RequestParam("uid") Integer uid) {
        return fhs.queryFinanceHistoryById(uid);
    }

    /**
     * 订单支付
     * @param map
     * @return
     */
    @RequestMapping("addFinanceHistory")
    public int addFinanceHistory(@RequestBody Map<String,String> map){
        return fhs.addFinanceHistory(map);
    }

    /**
     * 添加临时订单，时长为15分钟
     * @param map
     * @return
     */
    @RequestMapping("RedisFinanceHistory")
    public Set<Object> RedisFinanceHistory(@RequestBody Map<String,String> map){
        return fhs.RedisFinanceHistory(map);
    }
}
