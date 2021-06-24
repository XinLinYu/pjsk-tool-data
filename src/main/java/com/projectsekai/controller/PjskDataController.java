package com.projectsekai.controller;

import com.projectsekai.service.PjskCardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskDataController
 * @description
 * @date 2021/6/22 0022 下午 5:12
 **/
@Controller
@RequestMapping("/api")
public class PjskDataController {
    @Resource
    public PjskCardService pjskCardService;

    /**
     * @param
     * @return void
     * @Author XinlindeYu
     * @description 卡牌数据
     * @Date 下午 2:50 2021/6/24 0024
     **/
    @RequestMapping(value = "/card.do", method = RequestMethod.GET)
    @ResponseBody
    public void cardData() {
        pjskCardService.getProjectSekaiCardData();
    }
}
