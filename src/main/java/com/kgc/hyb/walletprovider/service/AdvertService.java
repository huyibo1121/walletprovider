package com.kgc.hyb.walletprovider.service;


import wallet.bean.Advert;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 8:49
 */
public interface AdvertService {
    /**
     * 全查广告表
     * @return
     */
    public List<Advert> queryAllAdvert();

    /**
     * 修改广告信息
     * @param advert
     * @return
     */
    public int addAdvs(Advert advert);
}
