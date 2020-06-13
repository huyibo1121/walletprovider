package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Bank;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 9:12
 */
@Mapper
public interface BankDao {

    /**
     * 添加银行卡信息
     * @param bankid
     * @param uid
     * @return
     */
    public int addbank (@Param("bankid") String bankid,@Param("uid") Long uid);

    /**
     * 修改银行卡信息
     * @param bankid
     * @param uid
     * @return
     */
    public int updatebank (@Param("bankid") String bankid,@Param("uid") Long uid);

    /**
     * 删除银行卡信息
     * @param bankid
     * @return
     */
    public int deletebank(@Param("bankid") String bankid);

    /**
     * 根据用户id查询银行卡
     * @param uid
     * @return
     */
    public List<Bank> queryBankByUid(@Param("uid") Long uid);

    /**
     * 根据银行卡id查询银行卡信息
     * @param bankid
     * @return
     */
    public Bank queryBankByBankid(@Param("bankid") String bankid);
}
