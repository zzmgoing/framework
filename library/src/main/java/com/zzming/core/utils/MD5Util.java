package com.zzming.core.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author ZhongZiMing
 * @time 2022/6/20
 * @description
 **/
public class MD5Util {

    //十六进制数组值
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 返回大写MD5
     *
     * @param origin 要加密的原字符串
     * @return
     */
    public static String MD5Encode(String origin) {
        String resultString = origin;
        try {
            //初始化md5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            //md.digest(resultString.getBytes())获取数据的信息摘要，返回字节数组
            //byteArrayToHexString()将字节数组转为十六进制数
            resultString = byteArrayToHexString(md.digest(resultString.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultString.toUpperCase();
    }

    /**
     * 将字节数组转为十六进制数
     *
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) {
            //每个字节转为十六进制数后进行拼接
            resultSb.append(byteToHexString(value));
        }
        return resultSb.toString();
    }

    /**
     * 将某一个字节转为十六进制数
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

}
