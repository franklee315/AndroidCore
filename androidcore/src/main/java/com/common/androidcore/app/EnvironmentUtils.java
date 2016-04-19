package com.common.androidcore.app;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.common.androidcore.BaseApplication;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * 硬件相关
 */
public class EnvironmentUtils {
    public static EnvironmentUtils environmentUtils;
    private Context context;
    private TelephonyManager tm;

    private EnvironmentUtils() {
        this.context = BaseApplication.getInstance();
    }

    public static EnvironmentUtils getInstance() {
        if (environmentUtils == null) {
            environmentUtils = new EnvironmentUtils();
        }
        return environmentUtils;
    }

    private TelephonyManager getTm() {
        if (tm == null) {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return tm;
    }


    /**
     * 检测SDCard是否插入
     *
     * @return 是否插入
     */
    public boolean isSDCardLoad() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得设备特征码
     *
     * @return 设备特征码
     */
    public String getUUID() {
        final TelephonyManager tm = getTm();
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
            return null;
        }
    }

    /**
     * 检测手机是否已Root
     *
     * @return 手机是否root
     */
    public boolean isRoot() {
        try {
            File file;
            final String[] suSearchPaths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
            for (String path : suSearchPaths) {
                file = new File(path + "su");
                if (file.exists()) {
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
     * @return mac地址
     */
    public String getMacAddress() {
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
     * @return 运营商名称
     */
    public String getNetworkName() {
        TelephonyManager telManager = getTm();

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
     * @return IMEI
     */
    public String getIMEI() {
        try {
            TelephonyManager tm = getTm();
            String deviceId = tm.getDeviceId();
            if (TextUtils.isEmpty(deviceId) || deviceId.contains("unknown")) {
                deviceId = "aid" + Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            }
            return java.net.URLEncoder.encode(deviceId, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
