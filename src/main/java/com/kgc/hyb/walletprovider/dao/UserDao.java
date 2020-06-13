package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 13:47
 */
@Mapper
public interface UserDao {
    /**
     * 全查用户表 用户个人信息展示
     * @return
     */
    public List<User> queryAllUser();

    /**
     * 根据真实姓名模糊查询
     * @return
     */
    public List<User> queryUserByIdName(@Param("idname") String idname);

    /**
     * 根据id查询用户详情页
     * @param uid
     * @return
     */
    public User queryUserDetailByid(@Param("uid") Integer uid);

    /**
     * 根据id修改用户状态
     * @param user
     * @return
     */
    public int updateUstate(User user);

    /**
     * 修改用户表的资产
     * @param
     * @return
     */
    public int updateUserAssets(@Param("uid") Integer uid, @Param("assets") BigDecimal assets);

    /**
     * 根据id查询用户现有金额
     * @param uid
     * @return
     */
    public User queryAssetsById(@Param("uid") Integer uid);

    /**
     * 查询用户总个数
     * @return
     */
    public int countqueryassetsById();


    public List<User> queryAllUsers();

    /**
     * 注册
     * @param user
     * @return
     */
    public int addUser(User user);

    public User queryUserByPhone(@Param("phone") String phone);

    /**
     * 根据id修改手机号
    * @param uid
     * @param phone
     * @return
     */
    public int updatePhoneById(@Param("uid") Integer uid,@Param("phone") String phone);

    /**
     * 通过原密码修改登录密码
     * @param uid
     * @param logpwd
     * @return
     */
    public int updateLogpwdBLogpwd(@Param("uid") Integer uid,
                                   @Param("logpwd") String logpwd
                                    );

    /**
     * 通过手机号修改登录密码
     * @param uid
     * @param logpwd
     * @return
     */
    public int updateLogpwdByPhone(@Param("uid") Integer uid,
                                   @Param("logpwd") String logpwd);

    /**
     * 通过手机号修改支付密码
     * @param uid
     * @param paypwd
     * @return
     */
    public int updatePaypwdByphone(@Param("uid") Integer uid,
                                   @Param("paypwd") Integer paypwd);

    /**
     * 修改用户头像
     * @param uid
     * @param head
     * @return
     */
    public int updateUserHead(@Param("uid") Long uid,@Param("head") String head);

    public User queryAllByUid(@Param("uid") Long uid);


    /**
     * 用户信息展示，根据uidc查（头像、昵称、性别、身份证号码）
     * @param uid
     * @return
     */
    public User queryUserByid(@Param("uid") Long uid);
}
