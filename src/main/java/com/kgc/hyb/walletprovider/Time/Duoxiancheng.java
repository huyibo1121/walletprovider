package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.service.FinanceService;
import com.kgc.hyb.walletprovider.service.impl.FinanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import wallet.bean.Finance;
import wallet.bean.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/10 15:45
 */
public class Duoxiancheng implements Runnable {



    private FinanceService financeService;

    public FinanceService getFinanceService() {
        return financeService;
    }

    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    @Override
    public void run() {

        Thread thread=new Thread();
        thread.start();
    }
}
