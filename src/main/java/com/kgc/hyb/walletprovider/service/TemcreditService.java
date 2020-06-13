package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.Temcredit;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/11 16:38
 */
public interface TemcreditService {
    /**
     * 贷款临时订单表插入数据
     * @param temcredit
     * @return
     */
    public int addTemcredit(Temcredit temcredit);

    /**
     * 根据id删除临时订单
     * @param tid
     * @return
     */
    public int deleteTemcredit(@Param("tid") Integer tid);

    /**
     * 根据订单时间删除贷款临时订单
     * @param ctime
     * @return
     */
    public int deleteTemcreditByFtime(@Param("ctime") String ctime);

    /**
     * 根据id查询贷款临时订单
     * @param tid
     * @return
     */
    public Temcredit queryTemcreditById(@Param("tid") Integer tid);
}
