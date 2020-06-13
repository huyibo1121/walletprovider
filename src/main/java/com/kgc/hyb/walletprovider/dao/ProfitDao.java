package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import wallet.bean.Profit;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 10:35
 */
@Mapper
public interface ProfitDao {

    /**
     *活期理财明细记录 添加
     * @param profit
     * @return
     */
    public int addProfit(Profit profit);
}
