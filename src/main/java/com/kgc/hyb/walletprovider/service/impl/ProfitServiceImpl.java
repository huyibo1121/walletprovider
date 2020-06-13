package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.ProfitDao;
import com.kgc.hyb.walletprovider.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.Profit;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 11:01
 */
@Service
public class ProfitServiceImpl implements ProfitService {
    @Autowired
    ProfitDao pd;

    @Override
    public int addProfit(Profit profit) {



        return pd.addProfit(profit);
    }
}
