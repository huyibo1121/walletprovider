package com.kgc.hyb.walletprovider.service;

import wallet.bean.Back;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/16 15:41
 */
public interface BackService {
    /**
     * 手动还款
     * @return
     */
    public int shoudongBack(Map<String,String> map);
}
