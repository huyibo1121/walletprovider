package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.CreditRates;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 对贷款利率表进行操作
 * @date 2019/11/13 9:10
 */
@Mapper
public interface CreditrateDao {

    /**
     * 为贷款产品表添加利率
     * @param creditRates
     * @return
     */
    public int addRate(CreditRates creditRates);

    /**
     * 根据id查询贷款利率表
     * @param crid
     * @return
     */
    public CreditRates queryCreditRatesByCrid(@Param("crid") Integer crid);
}
