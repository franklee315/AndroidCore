package com.common.androidcore.info;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @author lifan 创建于 2013年10月25日 下午3:30:35
 * @version Ver 1.0 2013年10月25日 改订
 *          获得设备相关的信息
 */
public class DeviceInfoUtil {
    private static DeviceInfoUtil deviceUtil;
    private Context context;

    private DeviceInfoUtil(Context context) {
        this.context = context;
    }

    /**
     * 获得DeviceUtil实例
     *
     * @param context 上下文对象
     * @return DeviceUtil实例
     */
    public static DeviceInfoUtil getInstance(Context context) {
        if (deviceUtil == null) {
            deviceUtil = new DeviceInfoUtil(context);
        }
        return deviceUtil;
    }

    /**
     * 获得设备型号
     *
     * @return 设备型号
     */
    public String getDeviceName() {
        return android.os.Build.MODEL;
    }

    /**
     * 获得设备平台
     *
     * @return 设备平台
     */
    public String getSystemName() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获得操作系统版本
     *
     * @return 操作系统版本
     */
    public int getSystemVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获得屏幕方向
     *
     * @return 屏幕方向
     */
    public int getViewState() {
        return context.getResources().getConfiguration().orientation;
    }

    /**
     * 获得IMEI
     * @return IMEI
     */
    public String getIMEI() {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
}
