package com.kgc.hyb.walletprovider.Time;

import com.kgc.hyb.walletprovider.service.TemfinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.SchemaOutputResolver;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/11 9:49
 */
@RestController
@Component
public class Temfinance {
    @Autowired
    TemfinanceService temfinanceService;

    //@RequestMapping("asd")
    @Scheduled(cron = "10 * * * * *")
    public void temfinance(){
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -15);// 15分钟之前的时间
        Date beforeD = beforeTime.getTime();
        String ftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeD);


        temfinanceService.deleteTmfinanceByFtime(ftime);
    }
}
