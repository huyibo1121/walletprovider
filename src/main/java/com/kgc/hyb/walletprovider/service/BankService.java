package com.kgc.hyb.walletprovider.service;

import com.kgc.hyb.walletprovider.dao.BankDao;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Bank;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 9:26
 */
public interface BankService {
    /**
     * 添加银行卡信息
     * @param map
     * @return
     */
    public int addbank (Map<String,String> map);

    /**
     * 修改银行卡信息
     * @param bankid
     * @param uid
     * @return
     */
    public int updatebank (String bankid,@Param("uid") Long uid);

    /**
     * 删除银行卡信息
     * @param bankid
     * @return
     */
    public int deletebank(String bankid);

    /**
     * 根据用户id查询银行卡
     * @param uid
     * @return
     */
    public List<Bank> queryBankByUid(Long uid);

    /**
     * 根据银行卡id查询银行卡信息
     * @param bankid
     * @return
     */
    public Bank queryBankByBankid(String bankid);
}
