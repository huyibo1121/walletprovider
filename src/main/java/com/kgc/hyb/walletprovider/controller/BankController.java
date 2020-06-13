package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.dao.BankDao;
import com.kgc.hyb.walletprovider.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Bank;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 9:29
 */
@RestController
public class BankController {
    @Autowired
    BankService bs;

    @RequestMapping("addbank")
    public int addbank(@RequestBody Map<String,String> map) {
        return bs.addbank(map);
    }

    @RequestMapping("updatebank")
    public int updatebank(@RequestParam String bankid,@RequestParam Long uid) {
        return bs.updatebank(bankid, uid);
    }

    @RequestMapping("deletebank")
    public int deletebank(@RequestParam String bankid) {
        return bs.deletebank(bankid);
    }

    @RequestMapping("queryBankByUid")
    public List<Bank> queryBankByUid(@RequestParam Long uid) {
        return bs.queryBankByUid(uid);
    }

    @RequestMapping("queryBankByBankid")
    public Bank queryBankByBankid(@RequestParam String bankid) {
        return bs.queryBankByBankid(bankid);
    }
}
