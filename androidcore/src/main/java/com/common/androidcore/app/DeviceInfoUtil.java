package com.common.androidcore.app;

/**
 * @author lifan 创建于 2013年10月25日 下午3:30:35
 * @version Ver 1.0 2013年10月25日 改订
 *          获得设备相关的信息
 */
public class DeviceInfoUtil {
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


}
