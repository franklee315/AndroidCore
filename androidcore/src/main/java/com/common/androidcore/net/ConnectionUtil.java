package com.common.androidcore.net;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by lifan on 14-3-21. 网络状态工具类
 * 
 * @author lifan
 * @version 1.0
 */
public class ConnectionUtil {
	private Context context;
	private static ConnectionUtil connectionUtil;

	private ConnectionUtil(Context context) {
		this.context = context;
	}

	/**
	 * 获得ConnectionUtil实例
	 * 
	 * @param context
	 *            上下文对象
	 * @return ConnectionUtil实例
	 */
	public static ConnectionUtil getInstance(Context context) {
		if (connectionUtil == null) {
			connectionUtil = new ConnectionUtil(context);
		}
		return connectionUtil;
	}

	/**
	 * 判断手机网络是否可用
	 * 
	 * @return 是否可用
	 */
	public boolean checkConn() {
		ConnectivityManager mgr = (ConnectivityManager) context.getApplicationContext().getSystemService(
				Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfos = mgr.getAllNetworkInfo();
		if (networkInfos != null) {
			for (NetworkInfo networkInfo : networkInfos) {
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
		ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
	}

	/**
	 * 判断当前网络是否3G网络
	 * 
	 * @return 是否是3G网络
	 */
	public boolean is3G() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
	}

	/**
	 * GPS是否打开
	 * 
	 * @return 是否打开
	 */
	public boolean isGpsEnabled() {
		LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders = locationManager.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}
}
