package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.FinanceRates;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 9:25
 */
@Mapper
public interface FinancerateDao {

    public List<FinanceRates> queryAllOfFinanceRates();

    /**
     * 获取活期理财走势
     * @return
     */
    public List<FinanceRates> queryAllFinance(@Param("limit") Integer limit);


    /**
     * 根据时间查活期利率
     * @param ratetime
     * @return
     */
    public BigDecimal queryFinRatesRate(@Param("ratetime") String ratetime);



}
