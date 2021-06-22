/*
 * Copyright (C), 2015-9999, 南京紫金数据信息技术有限公司
 * Author: 孙夕银
 * Date: 2017年11月20日
 * Description:
 */
package com.projectsekai.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取properties配置文件工具类
 *
 * @ClassName: PropertiesTool
 * @Description: TODO
 * @author: wumeng
 * @date: 2017年8月17日 上午11:01:45
 */
public class PropertiesTool {

    private static Log log = LogFactory.getLog(PropertiesTool.class);

    private static Properties props;

    /**
     * 加载配置文件
     */
    static {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(
                    PropertiesTool.class.getClassLoader().getResourceAsStream(
                            "varible.properties"), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            log.error("工程读取配置文件 = varible.properties，文件读取失败。", e);
        }
    }

    /**
     * 根据key读取对应的value
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return props.getProperty(key);
    }

}
