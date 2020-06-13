package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Credit;

import java.math.BigDecimal;
import java.util.List;


/**贷款产品
 * @author 胡怡博
 * @version 0.0.1
 * @Description 对credit表进行操作
 * @date 2019/11/5 16:28
 */
@Mapper
public interface CreditDao {
    /**
     * 对credit表进行添加操作
     *
     * @param credit
     * @return
     */
    public int addCredit(Credit credit);

    /**
     * 对credit表进行全查操作
     *
     * @return
     */
    public List<Credit> queryAllCredit();

    /**
     * 修改贷款产品状态
     *
     * @param credit
     * @return
     */
    public int updateCredit(Credit credit);

    /**
     * 贷款产品表和贷款利率表联查
     *
     * @return
     */
    public List<Credit> queryLoanRate();

    /**
     * 通过贷款产品名模糊查询
     *
     * @return
     */
    public List<Credit> selectLoanByname(@Param("crename") String crename);

    /**
     * 根据利率查询贷款产品
     *
     * @return
     */
    public List<Credit> queryCredit();


    /**
     * 通过贷款产品名模糊查询
     *
     * @return
     */
    public List<Credit> queryByCrename(
            @Param("pageon") Integer pageon,
            @Param("crename") String crename
    );

    /**
     * 查询记录数
     *
     * @param crename
     * @return
     */
    public int count(@Param("crename") String crename);


    /**
     * 根据新品获取贷款产品
     *
     * @return
     */
    public List<Credit> queryCreditByReleasetime();

    /**
     * 根据周期查询贷款产品
     *
     * @return
     */
    public List<Credit> queryCreditByMaxCycle(@Param("cycle") Integer cycle);

    /**
     * 根据额度查询贷款产品
     *
     * @param quotaMin,quotaMax
     * @return
     */
    public List<Credit> queryCreditByQuoTa(@Param("quotaMin") Integer quotaMin,
                                           @Param("quotaMax") Integer quotaMax
    );


    /**
     * 根据id查询贷款产品
     * @param creid
     * @return
     */
    public Credit queryCreditByCrid(@Param("creid") Integer creid);

    /**
     * 用户申请贷款
     *
     * @return
     */
    public int applyCredit(@Param("uid") Long uid,
                           @Param("money") BigDecimal money,
                           @Param("creid") Integer creid,
                           @Param("crid") Integer crid,
                           @Param("paypwd") Integer paypwd
             );


    /**
     * 查询四条credit数据
     * @return
     */
    public List<Credit> queryAllOfCredit();


    /**
     * 新增临时订单
     * @param uid
     * @param money
     * @param creid
     * @return
     */
    public int GoumaiCredit(@Param("uid") Integer uid,
                             @Param("money") BigDecimal money,
                             @Param("creid") Integer creid,
                             @Param("crid") Integer crid);

    /**
     * 支付临时订单
     * @param uid
     * @param tid
     * @return
     */
    public int ZhifuTemCredit(@Param("uid") Integer uid,
                               @Param("tid") Integer tid
    );
}