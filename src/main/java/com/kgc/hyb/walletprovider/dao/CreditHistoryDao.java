package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.CreditHistory;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 19:21
 */
@Mapper
public interface CreditHistoryDao {
    /**
     * 根据用户id查用户贷款详情
     * @param uid
     * @return
     */
    public List<CreditHistory> queryLoanHistoryByid(@Param("uid") Integer uid);

    /**
     * 生成贷款记录
     * @param creditHistory
     * @return
     */
    public int addCreditHistory(CreditHistory creditHistory);

    /**
     * 查询贷款记录数量
     * @return
     */
    public int queryConutOfCreditHistory();

    /**
     * 通过chid 查询贷款记录
     * @param chid
     * @return
     */
    public CreditHistory queryCreditHistoryById(@Param("chid") Long chid);
}
