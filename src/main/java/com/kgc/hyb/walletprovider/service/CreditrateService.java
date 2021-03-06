package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.CreditRates;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 9:37
 */
public interface CreditrateService {
    /**
     * 为贷款产品表添加利率
     * @param creditRates
     * @return
     */
    public int addRate(CreditRates creditRates);

    /**
     * 根据id查询贷款产品表
     * @param crid
     * @return
     */
    public CreditRates queryCreditRatesByid(@Param("crid") Integer crid);

}
