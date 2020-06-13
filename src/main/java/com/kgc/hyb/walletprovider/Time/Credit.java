package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.dao.CreditDao;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/4 19:26
 */
@Component
public class Credit {
    @Autowired
    CreditDao cd;
    @Autowired
    RedisUtils redisUtils;

    @Scheduled(cron = "0 0 6 * * *")
    public void queryCreditRate(){
        redisUtils.set("credit",cd.queryCredit());
    }
}
