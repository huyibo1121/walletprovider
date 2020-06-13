package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Bill;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 15:41
 */
@Mapper
public interface BillDao {
    /**
     * 添加账单
     * @param bill
     * @return
     */
    public int addBill(Bill bill);

    /**
     * 添加账单
     * @param bill
     * @return
     */
    public int addBill1(Bill bill);

    /**
     * 根据uid查当前用户的信息
     * @param uid
     * @return
     */
    public Bill querybilltime(Integer uid);

    /**
     * 根据id查用户的所有消费记录
     * @param uid
     * @param pageon
     * @return
     */
    public List<Bill> queryByIdBill(@Param("uid")Integer uid, @Param("pageon") Integer pageon);

    public int countqueryByIdBill(@Param("uid")Integer uid);


    /**
     * 根据开始时间和结束时间  和  用户id  查询用户支出金额集合
     * @param uid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Bill> querynowmoneybill(@Param("uid")Integer uid,
                                        @Param("billtype") Integer billtype,
                                        @Param("startdate") String startdate,
                                        @Param("enddate") String enddate);


    /**
     * 查询账单（理财记录，还款记录，贷款记录）
     * @param uid
     * @param starttime
     * @param endtime
     * @param billtype
     * @return
     */
    public List<Bill> queryBillByUidAndStartEnd(@Param("uid") Long uid,
                                                @Param("starttime") String starttime,
                                                @Param("endtime") String endtime,
                                                @Param("billtype") Integer billtype,
                                                @Param("pageon") Integer pageon);
}
