package com.kgc.hyb.walletprovider.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wallet.bean.Overdue;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/17 9:53
 */
@Mapper
public interface OverdueDao {
    /**
     * 添加逾期记录
     * @param overdue
     * @return
     */
    public int addOverdue(Overdue overdue);

    /**
     * 修改逾期记录
     * @return
     */
    public int updateOverdue(Overdue overdue);

    /**
     * 删除逾期记录
     * @return
     */
    public int deleteOverdue(Long overdueid);

    /**
     * 根据id查询逾期记录
     * @param overdueid
     * @return
     */
    public Overdue queryOverdueByID(Long overdueid);

    /**
     * 根据还款记录id查询逾期记录
     * @param backid
     * @return
     */
    public List<Overdue> queryByBack(Long backid);

}
