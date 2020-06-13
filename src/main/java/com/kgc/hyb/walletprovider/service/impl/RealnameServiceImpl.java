package com.kgc.hyb.walletprovider.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kgc.hyb.walletprovider.dao.RealnameDao;
import com.kgc.hyb.walletprovider.service.RealnameService;
import com.kgc.hyb.walletprovider.util.Base64Util;
import com.kgc.hyb.walletprovider.util.IdcardUtil;
import com.kgc.hyb.walletprovider.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.Realname;

import java.util.Map;
import java.util.UUID;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 15:11
 */
@Service
public class RealnameServiceImpl implements RealnameService {

    @Autowired
    RealnameDao rd;

    @Override
    public Realname queryRealnameByUid(Long uid) {
        return rd.queryRealnameByUid(uid);
    }

    @Override
    public int addRealname(Map<String,String> map) {
        Long uid=Long.parseLong(map.get("uid"));
        String idcard=map.get("idcard");
        String idname=map.get("idname");

        String obverse=map.get("obverse");
        String path1="D:\\cloud\\walletprovider\\src\\main\\webapp\\file\\";
        String suffix1= StringUtil.subString(obverse,"/",",");
        System.out.println("这是我截取的字符串："+suffix1);
        String imgName1= UUID.randomUUID().toString()+"."+suffix1;
        Base64Util.decodeBase64ToImage(obverse,path1,imgName1);
        String newobverse=path1+imgName1;

        String reverse=map.get("reverse");
        String path2="D:\\cloud\\walletprovider\\src\\main\\webapp\\file\\";
        String suffix2= StringUtil.subString(reverse,"/",",");
        System.out.println("这是我截取的字符串："+suffix2);
        String imgName2= UUID.randomUUID().toString()+"."+suffix2;
        Base64Util.decodeBase64ToImage(obverse,path1,imgName2);
        String newreverse=path1+imgName2;

        String str= IdcardUtil.authentication(idcard,idname);
        JSONObject json= JSONObject.parseObject(str);
        String status=(String) json.get("status");
        Realname realname=rd.queryRealnameByUid(uid);
        if (realname==null){
            if(status.equals("01")){
                Realname realname1=new Realname();
                realname1.setUid(uid);
                realname1.setIdcard(idcard);
                realname1.setIdname(idname);
                realname1.setObverse(newobverse);
                realname1.setReverse(newreverse);
                rd.addRealname(realname1);
                return 1;
            }else {
                System.out.println("实名认证失败");
                return 2;
            }
        }else {
            System.out.println("该身份已经进行身份认证");
            return  0;
        }
    }
}
