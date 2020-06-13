package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Slide;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 对轮播图表进行的操作
 * @date 2019/11/13 11:20
 */
@Mapper
public interface SlideDao {
    /**
     *全查轮播图
     * @return
     */
    public List<Slide> queryAllSlide();

    /**
     * 根据id单查轮播图表
     * @param slideid
     * @return
     */
    public Slide querySlideByid(@Param("slideid") Integer slideid);

    /**
     * 修改轮播图表
     * @param slide
     * @return
     */
    public int updateSlide(Slide slide);
}
