package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Bill;
import wallet.bean.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/6 16:49
 */
@RestController
public class BillController {

    @Autowired
    BillService bs;

    /**
     * 添加记录
     * @param bill
     * @return
     */
    @RequestMapping("addBill")
    public int addBill(@RequestBody Bill bill){
        return bs.addBill(bill);
    }

    @RequestMapping("queryByIdBill")
    public Page<Bill> queryByIdBill(@RequestParam("uid") Integer uid, @RequestParam("pageon") Integer pageon) {
        return bs.queryByIdBill(uid, pageon);
    }

    @RequestMapping("queryBillByUidAndStartEnd")
    public List<Bill> queryBillByUidAndStartEnd(@RequestBody Map<String,String> map) {
        return bs.queryBillByUidAndStartEnd(map);
    }

}
