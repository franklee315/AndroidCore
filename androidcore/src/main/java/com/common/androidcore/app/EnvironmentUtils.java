package com.common.androidcore.app;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class EnvironmentUtils {
    private static final String TAG = "GTGJ_EnvironmentUtils";

    /**
     * 获得设备特征码
     *
     * @param context context
     * @return 设备特征码
     */
    public static String getDeviceRelatedCode(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        StringBuilder sb = new StringBuilder();
        sb.append(tmDevice);
        sb.append(tmSerial);
        sb.append(androidId);

        UUID uuid;
        try {
            uuid = UUID.nameUUIDFromBytes((sb.toString()).getBytes("utf8"));
            return uuid.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 检测手机是否已Root
     *
     * @return 手机是否root
     */
    public static boolean isRoot() {
        try {
            File f = null;
            final String[] suSearchPaths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
            for (String path : suSearchPaths) {
                f = new File(path + "su");
                if (f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获得Mac地址
     *
     * @param context context
     * @return mac地址
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifi == null) {
                return "";
            } else {
                return wifi.getConnectionInfo().getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取运营商
     *
     * @param context context
     * @return 运营商名称
     */
    public static String getNetworkOperator(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        int phoneType = telManager.getPhoneType();
        if (phoneType != TelephonyManager.PHONE_TYPE_CDMA) {
            try {
                String mcc = telManager.getNetworkOperator().substring(0, 3);
                String mnc = telManager.getNetworkOperator().substring(3, 5);
                if (mcc.equals("460")) {
                    switch (mnc) {
                        case "00":
                        case "02":
                        case "07":
                            return "移动";
                        case "01":
                        case "06":
                            return "联通";
                        case "03":
                        case "05":
                            return "电信";
                        case "20":
                            return "铁通";
                    }
                } else {
                    return "其他";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return "其他";
            }
        }
        return "其他";
    }

    /**
     * 取得IMEI参数
     *
     * @param context context
     * @return IMEI
     */
    public static String getImei(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            if (TextUtils.isEmpty(deviceId) || deviceId.contains("unknown")) {
                // 取不到imei或imei不正确时，用androidId替代
                deviceId = "aid" + Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            }
            imei = java.net.URLEncoder.encode(deviceId + "", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }
}
