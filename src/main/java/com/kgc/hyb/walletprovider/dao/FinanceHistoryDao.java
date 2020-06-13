package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.FinanceHistory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/12 10:29
 */
@Mapper
public interface FinanceHistoryDao {
    /**
     * 根据用户id查询理财记录
     * @return
     */
    public List<FinanceHistory> queryFinanceHistoryById(@Param("uid") Integer uid);

    /**
     * 添加定期理财记录
     * @param redisHistory
     * @return
     */
    public int addFinanceHistory(FinanceHistory redisHistory);


    /**
     * 添加临时订单
     * @return
     */
    //public int RedisFinanceHistory(FinanceHistory financeHistory);
    public Set<Object> RedisFinanceHistory(Map<String,String> map);
}

