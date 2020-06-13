package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.Slide;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 11:23
 */
public interface SlideService {
    /**
     * 全查轮播图表
     * @return
     */
    public List<Slide> queryAllSlide();

    /**
     * 根据id单查轮播图表
     * @param slideid
     * @return
     */
    public Slide querySlideByid(Integer slideid);

    /**
     * 修改轮播图表
     * @param slide
     * @return
     */
    public int updateSlide(Slide slide);
}
