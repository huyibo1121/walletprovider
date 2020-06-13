package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.TemcreditDao;
import com.kgc.hyb.walletprovider.service.TemcreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.Temcredit;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/11 16:39
 */
@Service
public class TemcreditServiceImpl implements TemcreditService {

    @Autowired
    TemcreditDao td;

    @Override
    public int addTemcredit(Temcredit temcredit) {
        return td.addTemcredit(temcredit);
    }

    @Override
    public int deleteTemcredit(Integer tid) {
        return td.deleteTemcredit(tid);
    }

    @Override
    public int deleteTemcreditByFtime(String ctime) {
        return td.deleteTemcreditByFtime(ctime);
    }

    @Override
    public Temcredit queryTemcreditById(Integer tid) {
        return td.queryTemcreditById(tid);
    }
}
