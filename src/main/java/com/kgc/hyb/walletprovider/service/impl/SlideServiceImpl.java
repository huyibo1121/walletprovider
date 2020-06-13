package com.kgc.hyb.walletprovider.service.impl;

import com.kgc.hyb.walletprovider.dao.SlideDao;
import com.kgc.hyb.walletprovider.service.SlideService;
import com.kgc.hyb.walletprovider.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.bean.Slide;

import java.util.List;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/11/13 11:24
 */
@Service
public class SlideServiceImpl implements SlideService {
    @Autowired
    SlideDao sd;
    @Autowired
    RedisUtils ru;
    @Override
    public List<Slide> queryAllSlide() {
        Object o = ru.get("slide");
        if (o == null) {
            List<Slide> list = sd.queryAllSlide();
            ru.set("slide", list);
            return list;
        } else {
            return (List<Slide>) o;
        }
    }

    @Override
    public Slide querySlideByid(Integer slideid) {
        return sd.querySlideByid(slideid);
    }

    @Override
    public int updateSlide(Slide slide) {
        return sd.updateSlide(slide);
    }
}
