package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.config.RSAconfig;
import com.kgc.hyb.walletprovider.dao.UserDao;
import com.kgc.hyb.walletprovider.service.UserService;
import com.kgc.hyb.walletprovider.util.Base64Util;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import com.kgc.hyb.walletprovider.util.StringUtil;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.User;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.kgc.hyb.walletprovider.config.RSAconfig.*;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 14:14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao ud;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public List<User> queryAllUser() {
        return ud.queryAllUser();
    }

    @Override
    public List<User> queryUserByIdName(String idname) {
        return ud.queryUserByIdName(idname);
    }

    @Override
    public User queryUserDetailByid(Integer uid) {
        return ud.queryUserDetailByid(uid);
    }

    @Override
    public int updateUstate(User user) {
        return ud.updateUstate(user);
    }

    @Override
    public User queryAssetsById(Integer uid) {
        return ud.queryAssetsById(uid);
    }

    @Override
    public int countqueryassetsById() {
        return ud.countqueryassetsById();
    }

    @Override
    public int updateUserAssets(Integer uid, BigDecimal assets) {
        return ud.updateUserAssets(uid,assets);
    }

    @Override
    public List<User> queryAllUsers() {
        return ud.queryAllUsers();
    }

    @Override
    public int addUser(User user) {
        return ud.addUser(user);
    }

    /**
     * 注册
     * @param map
     * @return
     */
    @Override
    public int zhuceUser(Map<String, String> map){
        //判断手机号 验证码 登陆密码是否为空
        if(map.get("phone")==""||map.get("yzm")==""||map.get("logpwd")==""){
            return 0;
        }

        try {
            Integer yzm = Integer.parseInt(map.get("yzm"));
            String uname = map.get("uname");
            String logpwd = map.get("logpwd");
            String phone = map.get("phone");
            String sex = map.get("sex");
            KeyPair keyPair = RSAconfig.getKeyPair();
            String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
            //System.out.println("这是我加密前的密码："+logpwd);
            //加密
            String rsapassword = encrypt(logpwd, getPublicKey(publicKey));
            //System.out.println("这是我加密之后的密码：" + rsapassword);
            Integer oldyzm = (Integer) redisUtils.getSms("yzm"+phone);
            //System.out.println("这是我redis中的验证码" + oldyzm);
            User user = ud.queryUserByPhone(phone);
            if (user == null) {
                if (yzm.equals(oldyzm)) {
                    User user1 = new User();
                    user1.setPhone(phone);
                    user1.setUname(uname);
                    user1.setLogpwd(rsapassword);
                    user1.setPrivateKey(privateKey);
                    user1.setPublicKey(publicKey);
                    user1.setSex(sex);
                    ud.addUser(user1);
                    redisUtils.deleteSms("yzm"+phone);
                } else {
                    System.out.println("验证码错误");
                    return 0;
                }
            } else {
                System.out.println("该手机号已被注册");
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 验证码登录
     * @param map
     * @return
     */
    @Override
    public int SmsUserLog(Map<String, String> map) {
        Integer yzm=Integer.parseInt(map.get("yzm"));
        String phone=map.get("phone");
        Integer oldyzm=(Integer) redisUtils.getSms("yzm"+phone);
        //System.out.println("这是我redis之中的验证码"+oldyzm);
       // HttpSession session=request.getSession(true);
        User user=ud.queryUserByPhone(phone);
        if (user!=null){
            if (yzm.equals(oldyzm)){
                user.getUid();
                user.getPhone();
                user.getAssets();
               // session.setAttribute("user",user);
                System.out.println("登录成功！");
                redisUtils.deleteSms("yzm"+phone);
                return 1;
            }else {
                System.out.println("验证码错误！");
                return 0;
            }
        }else {
            System.out.println("该手机号还没注册！");
            return 0;
        }
    }

    /**
     * 账号密码登录
     * @param map
     * @param
     * @return
     */
    @Override
    public int UserLog(Map<String, String> map) {
        String phone=map.get("phone");
        String srlogpwd=map.get("logpwd");
        User user=ud.queryUserByPhone(phone);
        if (user!=null) {
            try {
                String rsapassword = user.getLogpwd();
                String privatekey = user.getPrivateKey();
                //解密
                String jmpassword = RSAconfig.decrypt(rsapassword, getPrivateKey(privatekey));
                //System.out.println(jmpassword+"这是我解密后的密码");
                if (jmpassword.equals(srlogpwd)) {
                    user.getUid();
                    user.getPhone();
                    user.getAssets();
                    // session.setAttribute("user",user);
                    System.out.println("登录成功！");
                    return 1;
                } else {
                    System.out.println("密码错误！");
                    return 0;
                }
            }catch (Exception e){
                e.printStackTrace();
                //解密失败
                System.out.println("解密失败");
                return 0;
            }
        }else {
            System.out.println("该手机号还没注册！");
            return 0;
        }
    }

    @Override
    public User queryUserByPhone(String phone) {
        return ud.queryUserByPhone(phone);
    }


    @Override
    public int updatePhoneById(Map<String, String> map) {
        //从session中获取
        Long uid=Long.parseLong(map.get("uid"));

        String newphone=map.get("newphone");
        String oldphone=map.get("oldphone");
        Integer yzm=Integer.parseInt(map.get("yzm"));
        Integer oldyzm=(Integer) redisUtils.getSms("yzm"+oldphone);
        User user=ud.queryUserByPhone(newphone);
        if (user==null){
            if(yzm==oldyzm){
                ud.updatePhoneById(uid.intValue(),newphone);
                redisUtils.deleteSms("yzm"+oldphone);
                System.out.println("修改成功！");
                return 1;
            }else {
                System.out.println("验证码错误！");
                return 2;
            }
        }else {
            System.out.println("改手机号已存在！");
            return 0;
        }

    }

    /**
     * 修改密码
     * @param map
     * @return
     */
    @Override
    public int updateLogpwdBLogpwd(Map<String, String> map) {
        //从session中获取
        Long uid=Long.parseLong(map.get("uid"));
        //从数据库获取
        User user=ud.queryAllByUid(uid);
        System.out.println(user+"++++++++++");
        System.out.println(user.getLogpwd()+"++++++++");
        //旧密码
        String logpwd=user.getLogpwd();
        //私钥
        String privatekey = user.getPrivateKey();
        //解密
        try {
            //解密后的旧密码
            String jmpassword = RSAconfig.decrypt(logpwd, getPrivateKey(privatekey));
            System.out.println("这是解密后的密码："+jmpassword);
            //前台输入的旧密码
            String inlogpwd=map.get("inlogpwd");
            //前台输入的修改后的密码
            String updatelogpwd=map.get(("updatelogpwd"));
            if(jmpassword.equals(inlogpwd)){
               if(!inlogpwd.equals(updatelogpwd)){
                   try {
                       String publicKey = user.getPublicKey();
                       String rsapassword = encrypt(updatelogpwd, getPublicKey(publicKey));
                       ud.updateLogpwdBLogpwd(uid.intValue(), rsapassword);
                       return 1;
                   }catch (Exception e){
                       e.printStackTrace();
                       System.out.println("加密失败！");
                       return 123;
                   }
               }else {
                   System.out.println("修改密码不得与原密码相同！");
                   return 2;
               }
            }else {
                System.out.println("请输入正确的原密码！");
                return 3;
            }
        }catch (Exception e){
            e.printStackTrace();
            //解密失败
            System.out.println("解密失败");
            return 0;
        }
    }

    //忘记密码
    @Override
    public int updateLogpwdByPhone(Map<String, String> map) {
        //从session中获取
        Long uid=Long.parseLong(map.get("uid"));

        String phone=map.get("phone");
        Integer yzm=Integer.parseInt(map.get("yzm"));
        Integer oldyzm=(Integer) redisUtils.getSms("yzm"+phone);
        String updatelogpwd=map.get("updatelogpwd");
        User user=ud.queryAllByUid(uid);
        String logpwd=user.getLogpwd();
        String privatekey = user.getPrivateKey();
        try {
            //解密后的旧密码
            String jmpassword = RSAconfig.decrypt(logpwd, getPrivateKey(privatekey));
            if ((yzm==oldyzm)){
                if (!updatelogpwd.equals(jmpassword)){
                    String publicKey =user.getPublicKey();
                    String rsapassword = encrypt(updatelogpwd, getPublicKey(publicKey));
                    ud.updateLogpwdByPhone(uid.intValue(),rsapassword);
                    return 1;
                }else {
                    System.out.println("新密码和原密码一致！！！！");
                    return 2;
                }
            }else {
                System.out.println("验证码不正确");
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("解密失败");
            return 3;
        }
    }

    //忘记支付密码
    @Override
    public int updatePaypwdByphone(Map<String, String> map) {
        //从session中获取
        Long uid=Long.parseLong(map.get("uid"));

        String phone=map.get("phone");
        Integer yzm=Integer.parseInt(map.get("yzm"));
        Integer oldyzm=(Integer) redisUtils.getSms("yzm"+phone);
        User user=ud.queryAllByUid(uid);
        Integer oldpaypwd=user.getPaypwd();
        //用户修改的新密码
        Integer newpaypwd=Integer.parseInt(map.get("newpwd"));
        if (yzm==oldyzm){
            if (oldpaypwd!=newpaypwd){
                ud.updatePaypwdByphone(uid.intValue(),newpaypwd);
                System.out.println("修改成功！");
                return 1;
            }else {
                System.out.println("新密码与旧密码一致！");
                return 3;
            }
        }else {
            System.out.println("请输入正确的验证码");
            return 2;
        }
    }

    /**
     * 修改支付密码
     * @param map
     * @return
     */
    public int updatePaypwdBypaypwd(Map<String, String> map) {
        //从session中获取
        Long uid=Long.parseLong(map.get("uid"));

        User user=ud.queryAllByUid(uid);
        //原来的支付密码
        Integer oldpaypwd=user.getPaypwd();
        //用户输入的支付密码
        Integer srpaypwd=Integer.parseInt(map.get("srpaypwd"));
        //用户修改后的支付密码
        Integer newpaypwd=Integer.parseInt(map.get("newpaypwd"));
        if (oldpaypwd.equals(srpaypwd)){
           if (!newpaypwd.equals(oldpaypwd)){
               ud.updatePaypwdByphone(uid.intValue(),newpaypwd);
               return 1;
           }else {
               System.out.println("新密码不得与旧密码一致！");
               return 2;
           }
        }else {
            System.out.println("您输入的支付密码不正确，请重新输入！");
            return 0;
        }
    }



    /**
     * 修改用户头像
     * @param uid
     * @param head
     * @return
     */
    @Override
    public int updateUserHead(Long uid, String head) {
        String path="D:\\cloud\\walletprovider\\src\\main\\webapp\\file\\";
        String suffix= StringUtil.subString(head,"/",",");
        System.out.println("这是我截取的字符串："+suffix);
        String imgName= UUID.randomUUID().toString()+"."+suffix;
        Base64Util.decodeBase64ToImage(head,path,imgName);
        String headpath=path+imgName;
        return ud.updateUserHead(uid,headpath);
    }

    @Override
    public User queryUserByid(Long uid) {
       User user=ud.queryUserByid(uid);
       String head=user.getHead();
        File userhead=new File(head);
        String hz=head.substring(head.lastIndexOf(".")+1);
        System.out.println(hz+"这是我图片的后缀");
        try {
            String newhead="data:image/"+hz+";base64,"+Base64Util.encodeImgageToBase64(userhead);
            System.out.println("解码成功");
            user.setHead(newhead);
            return user;
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("解码失败！");
            return user;
        }

    }

}
