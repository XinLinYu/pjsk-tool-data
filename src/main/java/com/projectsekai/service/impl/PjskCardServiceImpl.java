package com.projectsekai.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.projectsekai.domain.PjskCard;
import com.projectsekai.domain.PjskCardParameter;
import com.projectsekai.mapper.PjskCardMapper;
import com.projectsekai.service.PjskCardService;
import com.projectsekai.util.HttpClientUtil;
import com.projectsekai.util.PropertiesTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskCardServiceImpl
 * @description
 * @date 2021/6/22 0022 下午 4:52
 **/
@Service
public class PjskCardServiceImpl implements PjskCardService {
    @Resource
    private PjskCardMapper pjskCardMapper;

    /**
     * @param
     * @return void
     * @Author XinlindeYu
     * @description 获取卡牌数据添加进数据库（已添加事务，遇到异常则回滚）
     * @Date 下午 3:56 2021/6/24 0024
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getProjectSekaiCardData() {
        String cardJson = HttpClientUtil.sendGetRequest(PropertiesTool.getValue("card_json"));
        //String cardJson = jsonTest();
        List<PjskCard> pjskCardList = JSONArray.parseArray(cardJson, PjskCard.class);
        for (PjskCard card : pjskCardList) {
            // 时间戳转换为时间格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatDate = simpleDateFormat.format(card.getReleaseAt());
            // 将JSON匹配到实体类的ID移动到JSONID
            card.setJsonId(Integer.parseInt(card.getId()));
            card.setReleaseAtDate(formatDate);
            // 实体类的ID为主键
            card.setId(UUID.randomUUID().toString());
            // 根据规则获得到人物名称
            String characterName = getCharacterName(card.getAssetbundleName());
            card.setCharacterName(characterName);
            pjskCardMapper.insertPjskCardData(card);

            List<PjskCardParameter> pjskCardParameterList = card.getCardParameters();
            for (PjskCardParameter pjskCardParameter : pjskCardParameterList) {
                // 将JSON匹配到实体类的ID移动到JSONID
                pjskCardParameter.setJsonId(Integer.parseInt(pjskCardParameter.getId()));
                pjskCardParameter.setId(UUID.randomUUID().toString());
                // 匹配卡牌主键
                pjskCardParameter.setCardPrimaryKey(card.getId());
                pjskCardMapper.insertPjskCardParameter(pjskCardParameter);
            }
        }
    }

    private String getCharacterName(String bundleName) {
        String resName = bundleName.substring(0, 6);
        switch (resName) {
            case "res001":
                return "星乃一歌";
            case "res002":
                return "天马咲希";
            case "res003":
                return "望月穗波";
            case "res004":
                return "日野森志步";
            case "res005":
                return "花里みのり/花里实乃里";
            case "res006":
                return "桐谷遥";
            case "res007":
                return "桃井愛莉/桃井爱莉";
            case "res008":
                return "日野森雫";
            case "res009":
                return "小豆沢こはね/小豆泽心羽";
            case "res010":
                return "白石杏";
            case "res011":
                return "東雲彰人/东云彰人";
            case "res012":
                return "青柳冬弥/青柳冬弥";
            case "res013":
                return "天馬司/天马司";
            case "res014":
                return "鳳えむ/凤绘梦";
            case "res015":
                return "草薙寧々/草薙宁宁";
            case "res016":
                return "神代類/神代类";
            case "res017":
                return "宵崎奏";
            case "res018":
                return "朝比奈まふゆ/朝比奈真冬";
            case "res019":
                return "東雲絵名/东云绘名";
            case "res020":
                return "暁山瑞希/晓山瑞希";
            case "res021":
                return "初音ミク/初音未来/Miku/miku/MIKU/葱";
            case "res022":
                return "鏡音リン/镜音铃/Lin/lin/LIN/镜音双子";
            case "res023":
                return "鏡音レン/镜音连/Len/len/LEN/镜音双子";
            case "res024":
                return "巡音ルカ/巡音流歌/LUKA/luka/Luka";
            case "res025":
                return "メイコ/MEIKO/大姐";
            case "res026":
                return "カイト/KAITO";
            default:
                return null;
        }
    }

    private String jsonTest() {
        String jsonTestString = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"seq\": 10,\n" +
                "    \"characterId\": 1,\n" +
                "    \"rarity\": 1,\n" +
                "    \"specialTrainingPower1BonusFixed\": 0,\n" +
                "    \"specialTrainingPower2BonusFixed\": 0,\n" +
                "    \"specialTrainingPower3BonusFixed\": 0,\n" +
                "    \"attr\": \"cool\",\n" +
                "    \"supportUnit\": \"none\",\n" +
                "    \"skillId\": 1,\n" +
                "    \"cardSkillName\": \"足元の小さな花\",\n" +
                "    \"prefix\": \"クールだけど友達想い\",\n" +
                "    \"assetbundleName\": \"res001_no001\",\n" +
                "    \"gachaPhrase\": \"-\",\n" +
                "    \"flavorText\": \"フレーバーテキスト\",\n" +
                "    \"releaseAt\": 1546322400000,\n" +
                "    \"cardParameters\": [\n" +
                "      {\n" +
                "        \"id\": 10001,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1065\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10002,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1149\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10003,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1233\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10004,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1317\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10005,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1401\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10006,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1485\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10007,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1569\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10008,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1653\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10009,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1737\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10010,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1821\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10011,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1906\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10012,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 1990\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10013,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2074\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10014,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2158\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10015,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2242\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10016,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2326\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10017,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2410\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10018,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2494\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10019,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2578\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 10020,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2663\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11001,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 930\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11002,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1003\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11003,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1076\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11004,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1150\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11005,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1223\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11006,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1297\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11007,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1370\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11008,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1443\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11009,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1517\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11010,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1590\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11011,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1664\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11012,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1737\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11013,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1811\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11014,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1884\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11015,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 1957\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11016,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2031\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11017,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2104\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11018,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2178\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11019,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2251\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 11020,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2325\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12001,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1035\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12002,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1116\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12003,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1198\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12004,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1280\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12005,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1361\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12006,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1443\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12007,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1525\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12008,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1606\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12009,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1688\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12010,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1770\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12011,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1851\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12012,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 1933\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12013,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2015\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12014,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2096\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12015,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2178\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12016,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2260\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12017,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2341\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12018,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2423\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12019,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2505\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 12020,\n" +
                "        \"cardId\": 1,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2587\n" +
                "      }\n" +
                "    ],\n" +
                "    \"specialTrainingCosts\": [],\n" +
                "    \"masterLessonAchieveResources\": [\n" +
                "      {\n" +
                "        \"releaseConditionId\": 8,\n" +
                "        \"cardId\": 1,\n" +
                "        \"masterRank\": 1,\n" +
                "        \"resources\": []\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"seq\": 20,\n" +
                "    \"characterId\": 1,\n" +
                "    \"rarity\": 2,\n" +
                "    \"specialTrainingPower1BonusFixed\": 0,\n" +
                "    \"specialTrainingPower2BonusFixed\": 0,\n" +
                "    \"specialTrainingPower3BonusFixed\": 0,\n" +
                "    \"attr\": \"happy\",\n" +
                "    \"supportUnit\": \"none\",\n" +
                "    \"skillId\": 2,\n" +
                "    \"cardSkillName\": \"精一杯の韻を踏んで\",\n" +
                "    \"prefix\": \"Leo/need\",\n" +
                "    \"assetbundleName\": \"res001_no002\",\n" +
                "    \"gachaPhrase\": \"-\",\n" +
                "    \"flavorText\": \"フレーバーテキスト\",\n" +
                "    \"releaseAt\": 1546322400000,\n" +
                "    \"cardParameters\": [\n" +
                "      {\n" +
                "        \"id\": 20001,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2238\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20002,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2354\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20003,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2470\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20004,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2586\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20005,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2702\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20006,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2818\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20007,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2934\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20008,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3050\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20009,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3166\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20010,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3282\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20011,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3398\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20012,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3514\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20013,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3630\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20014,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3746\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20015,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3862\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20016,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3978\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20017,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4094\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20018,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4210\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20019,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4326\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20020,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4442\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20021,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 21,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4558\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20022,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 22,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4674\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20023,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 23,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4790\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20024,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 24,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4906\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20025,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 25,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5022\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20026,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 26,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5138\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20027,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 27,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5254\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20028,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 28,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5370\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20029,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 29,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5486\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 20030,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 30,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5602\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21001,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2547\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21002,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2678\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21003,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2810\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21004,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 2942\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21005,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3073\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21006,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3205\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21007,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3337\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21008,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3469\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21009,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3600\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21010,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3732\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21011,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3864\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21012,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3995\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21013,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4127\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21014,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4259\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21015,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4391\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21016,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4522\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21017,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4654\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21018,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4786\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21019,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4918\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21020,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5049\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21021,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 21,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5181\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21022,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 22,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5313\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21023,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 23,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5444\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21024,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 24,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5576\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21025,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 25,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5708\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21026,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 26,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5840\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21027,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 27,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5971\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21028,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 28,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 6103\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21029,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 29,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 6235\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21030,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 30,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 6367\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22001,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2003\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22002,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2106\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22003,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2210\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22004,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2313\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22005,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2417\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22006,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2520\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22007,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2624\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22008,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2727\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22009,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2831\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22010,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 2934\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22011,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3038\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22012,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3142\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22013,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3245\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22014,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3349\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22015,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3452\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22016,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3556\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22017,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3659\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22018,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3763\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22019,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3866\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22020,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3970\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22021,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 21,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4074\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22022,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 22,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4177\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22023,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 23,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4281\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22024,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 24,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4384\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22025,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 25,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4488\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22026,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 26,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4591\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22027,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 27,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4695\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22028,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 28,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4798\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22029,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 29,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4902\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22030,\n" +
                "        \"cardId\": 2,\n" +
                "        \"cardLevel\": 30,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5006\n" +
                "      }\n" +
                "    ],\n" +
                "    \"specialTrainingCosts\": [],\n" +
                "    \"masterLessonAchieveResources\": []\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"seq\": 30,\n" +
                "    \"characterId\": 1,\n" +
                "    \"rarity\": 3,\n" +
                "    \"specialTrainingPower1BonusFixed\": 300,\n" +
                "    \"specialTrainingPower2BonusFixed\": 300,\n" +
                "    \"specialTrainingPower3BonusFixed\": 300,\n" +
                "    \"attr\": \"mysterious\",\n" +
                "    \"supportUnit\": \"none\",\n" +
                "    \"skillId\": 6,\n" +
                "    \"cardSkillName\": \"踏み出した一歩\",\n" +
                "    \"prefix\": \"見上げる先に\",\n" +
                "    \"assetbundleName\": \"res001_no003\",\n" +
                "    \"gachaPhrase\": \"……すごいな、ミクは\",\n" +
                "    \"flavorText\": \"フレーバーテキスト\",\n" +
                "    \"releaseAt\": 1546322400000,\n" +
                "    \"cardParameters\": [\n" +
                "      {\n" +
                "        \"id\": 30001,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2844\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30002,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2916\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30003,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 2989\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30004,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3062\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30005,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3135\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30006,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3208\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30007,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3281\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30008,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3354\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30009,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3427\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30010,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3500\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30011,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3572\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30012,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3645\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30013,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3718\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30014,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3791\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30015,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3864\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30016,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 3937\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30017,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4010\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30018,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4083\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30019,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4156\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30020,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4229\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30021,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 21,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4301\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30022,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 22,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4374\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30023,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 23,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4447\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30024,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 24,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4520\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30025,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 25,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4593\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30026,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 26,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4666\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30027,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 27,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4739\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30028,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 28,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4812\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30029,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 29,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4885\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30030,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 30,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 4958\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30031,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 31,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5030\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30032,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 32,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5103\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30033,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 33,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5176\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30034,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 34,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5249\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30035,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 35,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5322\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30036,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 36,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5395\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30037,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 37,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5468\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30038,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 38,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5541\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30039,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 39,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5614\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30040,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 40,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5687\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30041,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 41,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 5971\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30042,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 42,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 6255\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30043,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 43,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 6540\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30044,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 44,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 6824\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30045,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 45,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 7109\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30046,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 46,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 7393\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30047,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 47,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 7677\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30048,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 48,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 7962\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30049,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 49,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 8246\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30050,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 50,\n" +
                "        \"cardParameterType\": \"param1\",\n" +
                "        \"power\": 8531\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31001,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3033\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31002,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3111\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31003,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3189\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31004,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3267\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31005,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3345\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31006,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3423\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31007,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3501\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31008,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3579\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31009,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3657\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31010,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3735\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31011,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3813\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31012,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3891\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31013,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 3969\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31014,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4047\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31015,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4125\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31016,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4203\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31017,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4281\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31018,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4359\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31019,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4437\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31020,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4515\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31021,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 21,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4593\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31022,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 22,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4671\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31023,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 23,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4749\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31024,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 24,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4827\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31025,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 25,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4905\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31026,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 26,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 4983\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31027,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 27,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5061\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31028,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 28,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5139\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31029,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 29,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5217\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31030,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 30,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5295\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31031,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 31,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5373\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31032,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 32,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5451\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31033,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 33,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5529\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31034,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 34,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5607\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31035,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 35,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5685\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31036,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 36,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5763\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31037,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 37,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5841\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31038,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 38,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5919\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31039,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 39,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 5997\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31040,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 40,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 6075\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31041,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 41,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 6378\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31042,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 42,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 6681\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31043,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 43,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 6984\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31044,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 44,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 7287\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31045,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 45,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 7591\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31046,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 46,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 7894\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31047,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 47,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 8197\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31048,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 48,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 8500\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31049,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 49,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 8803\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31050,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 50,\n" +
                "        \"cardParameterType\": \"param2\",\n" +
                "        \"power\": 9107\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32001,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 1,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3213\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32002,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 2,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3295\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32003,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 3,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3377\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32004,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 4,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3459\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32005,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 5,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3542\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32006,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 6,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3624\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32007,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 7,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3706\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32008,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 8,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3789\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32009,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 9,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3871\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32010,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 10,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 3953\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32011,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 11,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4036\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32012,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 12,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4118\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32013,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 13,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4200\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32014,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 14,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4283\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32015,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 15,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4365\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32016,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 16,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4447\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32017,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 17,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4529\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32018,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 18,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4612\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32019,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 19,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4694\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32020,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 20,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4776\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32021,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 21,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4859\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32022,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 22,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 4941\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32023,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 23,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5023\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32024,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 24,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5106\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32025,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 25,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5188\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32026,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 26,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5270\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32027,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 27,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5353\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32028,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 28,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5435\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32029,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 29,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5517\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32030,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 30,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5599\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32031,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 31,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5682\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32032,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 32,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5764\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32033,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 33,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5846\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32034,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 34,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 5929\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32035,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 35,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 6011\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32036,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 36,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 6093\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32037,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 37,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 6176\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32038,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 38,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 6258\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32039,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 39,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 6340\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32040,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 40,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 6423\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32041,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 41,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 6744\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32042,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 42,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 7065\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32043,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 43,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 7387\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32044,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 44,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 7708\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32045,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 45,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 8030\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32046,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 46,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 8351\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32047,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 47,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 8672\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32048,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 48,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 8994\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32049,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 49,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 9315\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 32050,\n" +
                "        \"cardId\": 3,\n" +
                "        \"cardLevel\": 50,\n" +
                "        \"cardParameterType\": \"param3\",\n" +
                "        \"power\": 9637\n" +
                "      }\n" +
                "    ],\n" +
                "    \"specialTrainingCosts\": [\n" +
                "      {\n" +
                "        \"cardId\": 3,\n" +
                "        \"seq\": 1,\n" +
                "        \"cost\": {\n" +
                "          \"resourceId\": 10,\n" +
                "          \"resourceType\": \"material\",\n" +
                "          \"resourceLevel\": 0,\n" +
                "          \"quantity\": 100\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"cardId\": 3,\n" +
                "        \"seq\": 2,\n" +
                "        \"cost\": {\n" +
                "          \"resourceId\": 14,\n" +
                "          \"resourceType\": \"material\",\n" +
                "          \"resourceLevel\": 0,\n" +
                "          \"quantity\": 50\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"masterLessonAchieveResources\": []\n" +
                "  }\n" +
                "]";
        return jsonTestString;
    }
}
