package com.kgc.hyb.walletprovider.service;

import wallet.bean.FinanceHistory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/12 10:35
 */
public interface FinanceHistoryService {
    /**
     * 根据用户id查询理财记录
     * @return
     */
    public List<FinanceHistory> queryFinanceHistoryById(Integer uid);

    /**
     * 购买定期基金
     * @return
     */
    public int addFinanceHistory(Map<String,String> map);

    /**
     * 添加临时订单
     * @return
     */
    //public int RedisFinanceHistory(FinanceHistory financeHistory);
    public Set<Object> RedisFinanceHistory(Map<String,String> map) ;

    public int addFinanceHistory(FinanceHistory redisHistory);

    /**
     * 获取用户所有临时订单
     * @param uid
     * @return
     */
    public Set<Object> queryRedisFinanceHistory(Long uid);
}
