package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.Bill;
import wallet.bean.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 15:52
 */
public interface BillService {
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
     * 根据id查用户的所有消费记录
     * @param uid
     * @param pageon
     * @return
     */
    public Page<Bill> queryByIdBill(@Param("uid")Integer uid, @Param("pageon") Integer pageon);

    public int countqueryByIdBill(@Param("uid")Integer uid);

    /**
     * 根据开始时间和结束时间  和  用户id  查询用户支出金额集合
     * @param uid
     * @param startdate
     * @param enddate
     * @return
     */
    public List<Bill> querynowmoneybill(Integer uid,
                                        String startdate,
                                        Integer billtype,
                                        String enddate);

    /**
     * 查询账单（理财记录，还款记录，贷款记录）
     * @param map
     * @return
     */
    public List<Bill> queryBillByUidAndStartEnd(Map<String,String> map);
}
