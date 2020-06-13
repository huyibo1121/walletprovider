package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Finance;

import java.math.BigDecimal;
import java.util.List;

/**理财产品
 * @author 胡怡博
 * @version 0.0.1
 * @Description 对finance表进行操作
 * @date 2019/11/12 14:15
 */
@Mapper
public interface FinanceDao {
    /**
     *对finance表进行添加操作
     * @param finance
     * @return
     */
    public int addFinance(Finance finance);

    /**
     * 对finance表进行全查操作
     * @return
     */
    public List<Finance> queryAllFinance();

    /**
     *
     * 修改理财产品状态
     * @return
     */
    public int updateFinance(Finance finance);

    /**
     * 根据id查询用户理财详情
     * @param uid
     * @return
     */
    public Finance queryFinanceHistoryByid(@Param("uid") Integer uid);

    /**
     * 通过理财产品名称模糊查询
     * @param finname
     * @return
     */
    public List<Finance> selectFinByname(
            @Param("finname") String finname
    );

    /**
     * 按热度查询理财产品
     * @return
     */
    public List<Finance> queryFinanceCycle();

    /**
     * 按利率查询理财产品
     * @return
     */
    public List<Finance> queryFinancereta();

    /**
     * 首页搜索
     * @param finname
     * @return
     */
    public List<Finance> queryFinanceByName(
            @Param("pageon") Integer pageon,
            @Param("finname") String finname
    );

    /**
     * 查询记录数
     * @param finname
     * @return
     */
    public int count(@Param("finname")String finname);


    /**
     * 根据新品查
     * @return
     */
    public List<Finance> queryFinaceByReleasetime();

    /**
     * 根据周期查询利率高的基金
     * @return
     */
    public List<Finance> queryFinanceRateByCycle(@Param("cycle") Integer cycle);

    /**
     * 根据id获取理财产品详情
     * @param finid
     * @return
     */
    public Finance queryFinanceById(@Param("finid") Integer finid);


    /**
     * 修改定期理财的总发布金额
     * @param finid
     * @param total
     * @return
     */
    public int UpdateFinanceTotal(@Param("finid") Integer finid,@Param("total") BigDecimal total);

    /**
     * 银行卡购买
     * @param uid
     * @param money
     * @param finid
     * @param bankid
     * @return
     */
    public int buyFinance(@Param("uid") Integer uid,
                          @Param("money") BigDecimal money,
                          @Param("finid") Integer finid,
                          @Param("bankid") String bankid);

    /**
     * 用户余额购买
     * @param uid
     * @param money
     * @param finid
     * @return
     */
    public int buyFinanceByassets(@Param("uid") Integer uid,
                          @Param("money") BigDecimal money,
                          @Param("finid") Integer finid);

    /**
     * 新增临时订单
     * @param uid
     * @param money
     * @param finid
     * @return
     */
    public int GoumaiFinance(@Param("uid") Integer uid,
                             @Param("money") BigDecimal money,
                             @Param("finid") Integer finid);

    /**
     * 支付临时订单
     * @param uid
     * @param tid
     * @return
     */
    public int ZhifuTemfinance(@Param("uid") Integer uid,
                               @Param("tid") Integer tid
                               );


    public List<Finance> queryAllOfFinance();
}
