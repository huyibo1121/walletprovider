package com.kgc.hyb.walletprovider.service.impl;


import com.kgc.hyb.walletprovider.dao.TemfinanceDao;
import com.kgc.hyb.walletprovider.service.TemfinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.Temfinance;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/11 8:59
 */
@Service
public class TemfinanceServiceImpl implements TemfinanceService {

    @Autowired
    TemfinanceDao td;

    @Override
    public int addTemfinance(Temfinance temfinance) {
        return td.addTemfinance(temfinance);
    }

    @Override
    public int deleteTemfinanceById(Integer tid) {
        return td.deleteTemfinanceById(tid);
    }

    @Override
    public int deleteTmfinanceByFtime(String ftime) {
        return td.deleteTmfinanceByFtime(ftime);
    }

    @Override
    public Temfinance queryTemfinanceById(Integer tid) {
        return td.queryTemfinanceById(tid);
    }
}
