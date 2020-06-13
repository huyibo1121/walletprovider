package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.dao.FinanceDao;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wallet.bean.Finance;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/4 19:28
 */
@Component
public class Finances {
    @Autowired
    FinanceDao fd;
    @Autowired
    RedisUtils redisUtils;
    @Scheduled(cron = "* * 6 * * *")
    public void queryFinanceCycle(){
        List<Finance> list = fd.queryFinanceCycle();
        redisUtils.set("finanaces",list);

    }

    @Scheduled(cron = "0 0 6 * * *")
    public void queryFinancereta(){

        redisUtils.set("finanaces",fd.queryFinancereta());
    }


}
