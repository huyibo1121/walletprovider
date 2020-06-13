package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Back;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/16 15:36
 */
@Mapper
public interface BackDao {
    /**
     * 添加还款记录
     * @return
     */
    public int addBack(Back back);

    /**
     * 修改还款记录状态
     * @return
     */
    public int updateBackState(@Param("backid") Long backid,@Param("backstate") Integer backstate);

    /**
     * 根据贷款记录id查询还款记录
     * @return
     */
    public List<Back> queryBackByChid(@Param("chid") Long chid);

    /**
     * 根据贷款id查询还款记录
     * @param backid
     * @return
     */
    public Back queryBackByID(@Param("chid") Long backid);

    /**
     *
     * @param backstate
     * @return
     */
    public List<Back> queryBackByState(@Param("backstate") Integer backstate);

    /**
     *
     * @param backtime
     * @return
     */
    public List<Back> queryBackByTime(@Param("backtime") String backtime);

}
