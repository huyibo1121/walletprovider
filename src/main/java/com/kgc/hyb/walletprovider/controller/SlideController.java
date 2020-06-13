package com.kgc.hyb.walletprovider.controller;

import com.kgc.hyb.walletprovider.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet.bean.Slide;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 11:25
 */
@RestController
public class SlideController {
    @Autowired
    SlideService ss;

    @RequestMapping("queryAllSlide")
    public List<Slide> queryAllSlide(){
        return ss.queryAllSlide();
    }

    @RequestMapping("querySlideById")
    public Slide querySlideById(@RequestParam Integer slideid){
        return ss.querySlideByid(slideid);
    }

    @RequestMapping("updateSlide")
    public int updateSlide(@RequestBody Slide slide){
        return ss.updateSlide(slide);
    }


}
