package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.User;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 14:14
 */
public interface UserService {

    /**
     * 全查用户表
     * @return
     */
    public List<User> queryAllUser();

    /**
     * 根据真实姓名模糊查询
     * @return
     */
    public List<User> queryUserByIdName(String idname);

    /**
     * 根据id查询用户详情页
     * @param uid
     * @return
             */
    public User queryUserDetailByid(Integer uid);

    /**
     * 根据id修改用户状态
     * @param user
     * @return
     */
    public int updateUstate(User user);

    /**
     * 根据id查用户现有金额
     * @param uid
     * @return
     */
    public User queryAssetsById(Integer uid);

    /**
     * 查询用户总个数
     * @return
     */
    public int countqueryassetsById();

    /**
     * 修改用户表的资产
     * @param
     * @return
     */
    public int updateUserAssets(@Param("uid") Integer uid, @Param("assets") BigDecimal assets);


    public List<User> queryAllUsers();

   //添加用户
    public int addUser(User user);

    /**
     * 注册
     * @param map
     * @return
     */
    public int zhuceUser(Map<String,String> map);

    /**
     * 短信验证码登录
     * @param map
     * @return
     */
    public int SmsUserLog(Map<String,String> map);

    /**
     * 密码登录
     * @param map
     * @return
     */
    public int UserLog(Map<String,String> map);


    public User queryUserByPhone(String phone);


    /**
     * 根据id修改手机号
     * @param map
     * @return
     */
    public int updatePhoneById(Map<String,String> map);

    /**
     * 通过原密码修改登录密码

     * @return
     */
    public int updateLogpwdBLogpwd(Map<String,String> map);

    /**
     * 通过手机号修改登录密码
     * @return
     */
    public int updateLogpwdByPhone(Map<String,String> map);

    /**
     * 通过手机号修改支付密码
     * @return
     */
    public int updatePaypwdByphone(Map<String,String> map);

    /**
     * 通过支付密码修改支付密码
     * @param map
     * @return
     */
    public int updatePaypwdBypaypwd(Map<String, String> map);

    /**
     * 修改用户头像
     * @param uid
     * @param head
     * @return
     */
    public int updateUserHead(Long uid,String head);


    /**
     * 用户信息展示，根据uidc查（头像、昵称、性别、身份证号码）
     * @param uid
     * @return
     */
    public User queryUserByid(Long uid);

}
