package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.dao.AdvertDao;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/4 19:20
 */
@Component
public class Advert {
    @Autowired
    AdvertDao ad;
    @Autowired
    RedisUtils redisUtils;
    /**
     * 全查广告
     */
    @Scheduled(cron = "1 * * * * *")
    public void queryAllAdvert(){
        redisUtils.set("adverts",ad.queryAllAdvert());
    }
}
