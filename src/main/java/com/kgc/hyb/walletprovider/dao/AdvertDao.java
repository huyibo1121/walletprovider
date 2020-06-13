package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;

import wallet.bean.Advert;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 对广告表实体类进行操作
 * @date 2019/11/13 8:45
 */
@Mapper
public interface AdvertDao {

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
