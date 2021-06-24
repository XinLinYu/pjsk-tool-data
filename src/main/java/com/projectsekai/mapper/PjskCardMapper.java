package com.projectsekai.mapper;

import com.projectsekai.domain.PjskCard;
import com.projectsekai.domain.PjskCardParameter;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskCardMapper
 * @Description
 * @date 2021/6/23 0023 上午 10:26
 **/
public interface PjskCardMapper {
    /**
     * @param pjskCard
     * @return void
     * @Author XinlindeYu
     * @description 插入卡牌数据
     * @Date 下午 2:59 2021/6/24 0024
     **/
    void insertPjskCardData(PjskCard pjskCard);

    /**
     * @param pjskCardParameter
     * @return void
     * @Author XinlindeYu
     * @description 插入卡牌属性数据
     * @Date 下午 3:14 2021/6/24 0024
     **/
    void insertPjskCardParameter(PjskCardParameter pjskCardParameter);
}
