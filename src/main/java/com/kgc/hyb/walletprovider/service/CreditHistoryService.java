package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.CreditHistory;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 19:28
 */
public interface CreditHistoryService {

    /**
     * 根据用户id查用户贷款详情
     * @param uid
     * @return
     */
    public List<CreditHistory> queryLoanHistoryByid(Integer uid);

    public Integer addcrdhis(Map<String,String> map);
}
