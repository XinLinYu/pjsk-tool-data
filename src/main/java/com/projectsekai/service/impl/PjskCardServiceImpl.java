package com.projectsekai.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.projectsekai.domain.PjskCard;
import com.projectsekai.service.PjskCardService;
import com.projectsekai.util.HttpClientUtil;
import com.projectsekai.util.PropertiesTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskCardServiceImpl
 * @description
 * @date 2021/6/22 0022 下午 4:52
 **/
@Service
public class PjskCardServiceImpl implements PjskCardService {
    @Override
    public void getProjectSekaiCardData() {
        String cardJson = HttpClientUtil.sendGetRequest(PropertiesTool.getValue("card_json"));
        List<PjskCard> pjskCardList = JSONArray.parseArray(cardJson, PjskCard.class);
        for (PjskCard card : pjskCardList) {

        }
    }
}
