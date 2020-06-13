package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.FinanceRates;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 9:44
 */
public interface FinancerateService {
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
    public BigDecimal queryFinRatesRate(String ratetime);

    public List<FinanceRates> queryAllOfFinanceRates();
}
