package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.AdvertDao;
import com.kgc.hyb.walletprovider.service.AdvertService;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.Advert;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 8:49
 */
@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    AdvertDao ad;
    @Autowired
    RedisUtils ru;
    @Override
    public List<Advert> queryAllAdvert() {
        Object o = ru.get("advert");
        if (o == null) {
            List<Advert> list = ad.queryAllAdvert();
            ru.set("advert", list);
            return list;
        } else {
            return (List<Advert>) o;
        }
    }

    @Override
    public int addAdvs(Advert advid) {
        return ad.addAdvs(advid);
    }
}
