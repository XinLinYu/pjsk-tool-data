package com.projectsekai.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @ClassName TokenProccessor
 * @Description
 * @Author XinlindeYu
 * @Date 2020/4/27 0027 下午 1:56
 * @Version 1.0
 **/
public class TokenProccessor {
    private TokenProccessor() {
    }

    ;
    private static final TokenProccessor instance = new TokenProccessor();

    public static TokenProccessor getInstance() {
        return instance;
    }

    /**
     * 生成Token
     *
     * @return
     */
    public String makeToken(String param) {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + param;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
