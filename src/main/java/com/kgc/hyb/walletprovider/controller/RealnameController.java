package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.RealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 15:22
 */
@RestController
public class RealnameController {
    @Autowired
    RealnameService rs;

    @RequestMapping("addRealname")
    public int addRealname(@RequestBody Map<String,String> map) {
        return rs.addRealname(map);
    }
}
