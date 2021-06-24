package com.projectsekai.domain;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskCardParameter
 * @description 卡牌等级数据
 * @date 2021/6/22 0022 下午 5:08
 **/
public class PjskCardParameter {
    private String id;
    private int jsonId;
    private int cardId;
    private String cardPrimaryKey;
    private int cardLevel;
    private String cardParameterType;
    private int power;

    public String getCardPrimaryKey() {
        return cardPrimaryKey;
    }

    public void setCardPrimaryKey(String cardPrimaryKey) {
        this.cardPrimaryKey = cardPrimaryKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getJsonId() {
        return jsonId;
    }

    public void setJsonId(int jsonId) {
        this.jsonId = jsonId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public String getCardParameterType() {
        return cardParameterType;
    }

    public void setCardParameterType(String cardParameterType) {
        this.cardParameterType = cardParameterType;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
