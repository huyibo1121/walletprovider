package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.CreditrateDao;
import com.kgc.hyb.walletprovider.service.CreditrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wallet.bean.CreditRates;



/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 9:38
 */
@Service
public class CreditrateServiceImpl implements CreditrateService {
    @Autowired
    CreditrateDao cd;
    @Override
    public int addRate(CreditRates creditRates) {
       return cd.addRate(creditRates);
    }

    @Override
    public CreditRates queryCreditRatesByid(Integer crid) {
        return cd.queryCreditRatesByCrid(crid);
    }


}
