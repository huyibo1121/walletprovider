package com.kgc.hyb.walletprovider.controller;


import com.aliyuncs.exceptions.ClientException;
import com.kgc.hyb.walletprovider.service.UserService;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import com.kgc.hyb.walletprovider.util.sendSmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.User;


import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 14:15
 */
@RestController
public class UserController {
    @Autowired
    UserService us;
    @Autowired
    RedisUtils redisUtils;


    @RequestMapping("queryAllUser")
    public List<User> queryAllUser(){
        List<User> list=us.queryAllUser();
        return us.queryAllUser();
    }

    @RequestMapping("queryUserByIdName")
    public List<User> queryUserByIdName(String idname){
        return us.queryUserByIdName(idname);
    }

    @RequestMapping("queryUserDetailByid")
    public User queryUserDetailByid(Integer uid){
        return us.queryUserDetailByid(uid);
    }

    @RequestMapping("UpdateUstate")
    public int UpdateUstate(@RequestBody User user){
        return us.updateUstate(user);
    }


    @RequestMapping("zhuceUser")
    public int zhuceUser(@RequestBody Map<String, String> map){
        return us.zhuceUser(map);
    }

    @RequestMapping("SendSmsUtils")
    public Map<String, String> SendSmsUtils(@RequestBody Map<String,String> map) throws ClientException {
        String phone=map.get("phone");
        int verificationCode = (int) ((Math.random()*9+1)*1000);
        System.out.println(verificationCode+"这是我的验证码");
        redisUtils.setSms("yzm"+phone,verificationCode);
        sendSmsUtils.sendSms(phone,verificationCode);
        return map;
    }

    @RequestMapping("SmsUserLog")
    public int SmsUserLog(@RequestBody Map<String, String> map) {
        return us.SmsUserLog(map);
    }

    @RequestMapping("UserLog")
    public int UserLog(@RequestBody Map<String, String> map) {
        return us.UserLog(map);
    }

    @RequestMapping("queryUserByPhone")
    public User queryUserByPhone(@RequestParam String phone) {
        System.out.println(phone+"++++++++++++++++++++++++++++++++++++++");
        System.out.println(us.queryUserByPhone(phone)+"++++++++++++++++++++++++");
        return us.queryUserByPhone(phone);
    }

    @RequestMapping("updateLogpwdBLogpwd")
    public int updateLogpwdBLogpwd(@RequestBody Map<String, String> map) {
        return us.updateLogpwdBLogpwd(map);
    }

    @RequestMapping("updateLogpwdByPhone")
    public int updateLogpwdByPhone(@RequestBody Map<String, String> map) {
        return us.updateLogpwdByPhone(map);
    }

    /**
     * 通过手机号修改支付密码
     * @param map
     * @return
     */
    @RequestMapping("updatePaypwdByphone")
    public int updatePaypwdByphone(@RequestBody Map<String, String> map) {
        return us.updatePaypwdByphone(map);
    }

    /**
     * 通过旧支付密码修改支付密码
     * @param map
     * @return
     */
    @RequestMapping("updatePaypwdBypaypwd")
    public int updatePaypwdBypaypwd(@RequestBody Map<String, String> map) {
        return us.updatePaypwdBypaypwd(map);
    }


    @RequestMapping("updatePhoneById")
    public int updatePhoneById(@RequestBody Map<String, String> map) {
        return us.updatePhoneById(map);
    }

    //修改用户头像
    @RequestMapping("updateUserHead")
    public int updateUserHead(@RequestParam Long uid, @RequestParam String head) {
        return us.updateUserHead(uid,head);
    }

    /**
     * 用户信息展示，根据uidc查（头像、昵称、性别、身份证号码）
     * @param uid
     * @return
     */
    @RequestMapping("queryUserByid")
    public User queryUserByid(@RequestParam Long uid){
        return us.queryUserByid(uid);
    }

}
