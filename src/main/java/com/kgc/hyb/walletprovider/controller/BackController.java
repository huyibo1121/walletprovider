package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.BackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.util.Map;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/18 8:59
 */
@RestController
public class BackController {
    @Autowired
    BackService bs;

    @RequestMapping("shoudongBack")
    public int shoudongBack(@RequestBody Map<String,String> map){
        return bs.shoudongBack(map);
    }
}
