package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.dao.SlideDao;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/4 19:23
 */
@Component
public class Slide {
    @Autowired
    SlideDao sd;
    @Autowired
    RedisUtils redisUtils;
    /**
     * 全查轮播图
     */
    @Scheduled(cron = "0 0 6 * * *")
    public void queryAllSlide(){
        redisUtils.set("slide",sd.queryAllSlide());
    }
}
