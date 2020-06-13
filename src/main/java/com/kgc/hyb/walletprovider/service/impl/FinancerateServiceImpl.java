package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.FinancerateDao;
import com.kgc.hyb.walletprovider.service.FinancerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import wallet.bean.FinanceRates;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 9:45
 */
@Service
public class FinancerateServiceImpl implements FinancerateService {
    @Autowired
    FinancerateDao fd;
    @Override
    public List<FinanceRates> queryAllFinance(Integer limit) {
        return fd.queryAllFinance(limit);
    }

    @Override
    public BigDecimal queryFinRatesRate(String ratetime) {
        return fd.queryFinRatesRate(ratetime);
    }

    @Override
    public List<FinanceRates> queryAllOfFinanceRates() {
        return fd.queryAllOfFinanceRates();
    }
}
