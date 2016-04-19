package com.common.androidcore.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.common.androidcore.BaseApplication;

/**
 * Created by lifan on 14-3-21. 网络状态工具类
 *
 * @author lifan
 * @version 1.0
 */
public class ConnectionUtil {
    private Context context;
    private static ConnectionUtil connectionUtil;

    private ConnectionUtil() {
        this.context = BaseApplication.getInstance();
    }

    private ConnectivityManager connectivityManager;

    public ConnectivityManager getConnectivityManager() {
        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return connectivityManager;
    }

    /**
     * 获得ConnectionUtil实例
     *
     * @return ConnectionUtil实例
     */
    public static ConnectionUtil getInstance() {
        if (connectionUtil == null) {
            connectionUtil = new ConnectionUtil();
        }
        return connectionUtil;
    }

    /**
     * 判断手机网络是否可用
     *
     * @return 是否可用
     */
    public boolean checkConn() {
        NetworkInfo[] networkInfoArray = getConnectivityManager().getAllNetworkInfo();
        if (networkInfoArray != null) {
            for (NetworkInfo networkInfo : networkInfoArray) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * WIFI是否打开
     *
     * @return 是否打开
     */
    public boolean isWifiEnabled() {
        ConnectivityManager mgrConn = getConnectivityManager();
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断当前网络是否WIFI网络
     *
     * @return 是否是WIFI网络
     */
    public boolean isWifi() {
        NetworkInfo activeNetInfo = getConnectivityManager().getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断当前网络是否3G网络
     *
     * @return 是否是3G网络
     */
    public boolean is3G() {
        NetworkInfo activeNetInfo = getConnectivityManager().getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
