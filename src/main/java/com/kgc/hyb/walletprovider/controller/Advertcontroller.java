package com.kgc.hyb.walletprovider.controller;


import com.kgc.hyb.walletprovider.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Advert;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 8:50
 */
@RestController
public class Advertcontroller{

    @Autowired
    AdvertService as;

    @RequestMapping("queryAllAds")
    public List<Advert> queryAllAdvet(){
        return as.queryAllAdvert();
    }

    @RequestMapping("addAdvs")
    public Integer addAdvs(@RequestBody Advert advert){
        return as.addAdvs(advert);
    }
}
