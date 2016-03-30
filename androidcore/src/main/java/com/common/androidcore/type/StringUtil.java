package com.common.androidcore.type;

import android.text.TextUtils;
import android.util.Base64;

import com.common.androidcore.bean.KeyValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lifan 创建于 2013年10月25日 下午4:05:37
 * @version Ver 1.0 2013年10月25日 改订 字符串工具类
 */
public class StringUtil {
    /**
     * 是否为Int
     *
     * @param str 需要判断的字符串
     * @return 是否为Int
     */
    public static int isInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 是否为Long
     *
     * @param str 需要判断的字符串
     * @return 是否为Long
     */
    public static long isLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 解析字符串为boolean
     * @param str 字符串
     * @return boolean值
     */
    public static boolean parseBoolean(String str) {
        return str.equals("true") || str.equals("1");
    }

    /**
     * 是否为身份证号
     *
     * @param str 需要判断的字符串
     * @return 是否为身份证号
     */
    public static boolean isIDCard(String str) {
        int length = str.length();
        return isLong(str) != -1 && (length == 15 || length == 18);
    }

    /**
     * 是否为银行卡号
     *
     * @param str 需要判断的字符串
     * @return 是否为银行卡号
     */
    public static boolean isBankCard(String str) {
        int length = str.length();
        return isLong(str) != -1 && length > 14 && length < 20;
    }

    /**
     * 判断字符串是否为url
     *
     * @param address url字符串
     * @return 是否为url
     */
    public static boolean isUrl(String address) {
        try {
            new URL(address);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否为邮编
     *
     * @param str 需要判断的字符串
     * @return 是否为邮编
     */
    public static boolean isPostalCode(String str) {
        return str.matches("[1-9]\\d{5}(?!\\d)");
    }

    /**
     * 检查字符串是否为电话号码
     *
     * @param phoneNumber 电话号码字符串
     * @return 是否为符合
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        String regExp = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 检查字符串是否为固定电话
     *
     * @param fixPhoneNumber 固定电话号码字符串
     * @return 是否为符合
     */
    public static boolean isFixPhoneNumber(String fixPhoneNumber) {
        String regExp = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(fixPhoneNumber);
        return m.matches();
    }

    /**
     * 检查字符串是否为邮箱
     *
     * @param email 邮箱字符串
     * @return 是否符合
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 删除html代码
     *
     * @param str 初始字符串
     * @return 处理后字符串
     */
    public static String removeHtml(String str) {
        return str.replaceAll("</?[^>]+>", "").replace("&nbsp;", "").replace("&nbsp;", "").replace("\"", "‘").replace("'", "‘");
    }

    /**
     * 通过URL和参数KEY获取参数值
     *
     * @param url URL
     * @param key 参数key
     * @return 参数值
     */
    public static String getUrlParamByKey(String url, String key) {
        String[] temp = url.split("\\?");
        String params = temp.length > 1 ? temp[1] : "";

        if (!TextUtils.isEmpty(params)) {
            temp = params.split("&");

            for (String keyValue : temp) {
                String[] keyValueArray = keyValue.split("=");
                if (keyValueArray.length > 1 && keyValueArray[0].equals(key)) {
                    return keyValueArray[1];
                }
            }
        }
        return null;
    }

    /**
     * 通过URL获取所有参数
     *
     * @param url URL
     * @return 所有参数
     */
    public static List<KeyValue> getUrlParams(String url) {
        List<KeyValue> allKeyValues = new ArrayList<>();
        String[] temp = url.split("\\?");
        String params = temp.length > 1 ? temp[1] : "";

        if (!TextUtils.isEmpty(params)) {
            temp = params.split("&");

            for (String keyValue : temp) {
                String[] keyValueArray = keyValue.split("=");
                if (keyValueArray.length > 1) {
                    try {
                        String value = URLDecoder.decode(keyValueArray[1], "UTF-8");
                        allKeyValues.add(new KeyValue(keyValueArray[0], value));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return allKeyValues;
    }

    /**
     * 集合转换成String
     * @param SceneList
     * @return
     * @throws IOException
     */
    public static String SceneList2String(List SceneList) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    /**
     * String转换成集合
     * @param SceneListString
     * @return
     * @throws StreamCorruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static List String2SceneList(String SceneListString) throws StreamCorruptedException, IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List SceneList = (List) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }

    /**
     * 字符串排版半角转全角
     *
     * @param input
     * @return
     */
    public String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
