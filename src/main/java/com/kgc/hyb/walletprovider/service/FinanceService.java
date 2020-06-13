package com.kgc.hyb.walletprovider.service;

import org.apache.ibatis.annotations.Param;
import wallet.bean.Credit;
import wallet.bean.Finance;
import wallet.bean.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/12 15:12
 */
public interface FinanceService {
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
    public List<Finance> selectFinByname(String finname);

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
     * `模糊查询
     * @param finname
     * @return
     */
    public Page<Finance> queryFinanceByName(Integer pageon,String finname);



    /**
     * 根据新品查
     * @return
     */
    public List<Finance> queryFinaceByReleasetime();

    /**
     * 根据周期查询利率高的基金
     * @return
     */
    public List<Finance> queryFinanceRateByCycle(Integer cycle);


    /**
     * 根据id获取理财产品详情
     * @param finid
     * @return
     */
    public Finance queryFinanceById(Integer finid);

    /**
     * 修改定期理财的总发布金额
     * @param finid
     * @param total
     * @return
     */
    public int UpdateFinanceTotal(@Param("finid") Integer finid,@Param("total") BigDecimal total);

    public int buyFinance(Integer uid, BigDecimal money,Integer finid,String bankid);

    public int buyFinanceByassets(Integer uid, BigDecimal money, Integer finid);

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
