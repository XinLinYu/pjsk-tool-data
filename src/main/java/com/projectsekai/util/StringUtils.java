/**
 * SSI框架
 * Copyright © 2010 HopeRun Inc. All Rights Reserved.
 */
package com.projectsekai.util;

import org.springframework.util.Base64Utils;

/**
 * 字符串工具类
 *
 * @author wang_zhongpei
 * @function 系统定义
 */
public class StringUtils {

    /**
     * 默认构造方法
     */
    private StringUtils() {

    }

    /**
     * 判断字符串是否为空
     *
     * @param str 需要判断是否为空的字符串
     * @return 判断结果
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * Description: 字符串非空判断<br>
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param str
     * @return
     * @see
     */
    public static String getNotEmpty(String str) {
        return (str == null) || ("".equals(str)) || ("null".equals(str)) ? ""
                : str;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Base64Utils.encode("<DBSET><C N=\"orgNo\">10150001</C><C N=\"consNo\">1006720652</C><C N=\"isVeri\">0</C><C N=\"password\"></C><C N=\"bgnYm\">2019-08</C><C N=\"endYm\">2019-10</C></DBSET>".getBytes("utf-8")));
    }

}
