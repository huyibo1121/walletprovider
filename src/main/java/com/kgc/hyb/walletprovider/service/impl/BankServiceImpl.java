package com.kgc.hyb.walletprovider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.kgc.hyb.walletprovider.dao.BankDao;
import com.kgc.hyb.walletprovider.service.BankService;
import com.kgc.hyb.walletprovider.util.BankcardUtil;
import com.kgc.hyb.walletprovider.util.sendSmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.Bank;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 9:27
 */
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    BankDao bd;

    @Override
    public int addbank(Map<String,String> map) {
        String accountNo=map.get("bankid");
        Bank bank=bd.queryBankByBankid(accountNo);
        if (bank==null){
            Long uid=Long.parseLong(map.get("uid"));
            String idCard=map.get("idCard");
            String mobile=map.get("phone");
            String name=map.get("name");
            String str= BankcardUtil.bankVerification(accountNo,idCard,mobile,name);
            JSONObject json= JSONObject.parseObject(str);
            String status=(String) json.get("status");
            if (status.equals("01")){
                System.out.println("认证成功！");
                //认证成功，向用户银行卡绑定的手机号发送验证码
                int verficattionCode=(int) ((Math.random()*9+1)*1000);
                try {
                    sendSmsUtils.sendSms(mobile,verficattionCode);
                    Integer yzm=Integer.parseInt(map.get("yzm"));
                    if (yzm.equals(verficattionCode)){
                        bd.addbank(accountNo,uid);
                        System.out.println("银行卡验证码成功");
                        return 1;
                    }else {
                        System.out.println("银行卡验证失败！！！");
                        return -1;
                    }
                }catch (ClientException e){
                    e.printStackTrace();
                    System.out.println("发送验证码失败！");
                    return -2;
                }

            }else {
                System.out.println("认证失败！！！");
                return 2;
            }
        }else {
            System.out.println("该银行卡已经绑定");
            return 0;
        }
    }

    @Override
    public int updatebank(String bankid, Long uid) {
        return bd.updatebank(bankid, uid);
    }

    @Override
    public int deletebank(String bankid) {
        return bd.deletebank(bankid);
    }

    @Override
    public List<Bank> queryBankByUid(Long uid) {
        return bd.queryBankByUid(uid);
    }

    @Override
    public Bank queryBankByBankid(String bankid) {
        return bd.queryBankByBankid(bankid);
    }
}
