package com.kgc.hyb.walletprovider.service;


import wallet.bean.Realname;

import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 15:10
 */
public interface RealnameService {
    /**
     * 根据uid查询实名认证表
     * @param uid
     * @return
     */
    public Realname queryRealnameByUid(Long uid);

    /**
     * 添加实名认证信息
     * @param map
     * @return
     */
    public int addRealname(Map<String,String> map);
}
