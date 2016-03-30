package com.common.androidcore.codec;

import java.security.MessageDigest;

/**
 * @author lifan 创建于 2013年10月25日 下午5:14:19
 * @version Ver 1.0 2013年10月25日 改订
 *          MD5加密
 */
public class MD5Util {
    /**
     * md5加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String getMD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
