package org.han.lsb_shop.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

public class MyApplication extends Application {
	private SharedPreferences preferences;
	private static ActivityManager am;
	private TelephonyManager tm;
	private ConnectivityManager cm;
	private LocationManager lm;
	private static Context mContext;
	private static MyApplication mInstance = null;
	public static MyApplication application;
	private boolean isDownload;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();// 获取程序上下文
		mInstance = this;
		preferences = getSharedPreferences("thRealestate", 0);// 创建本地缓存

		isDownload = false;
		am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);// 获取Activity管理
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);// 获得手机信息服务
		cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);// 获取网络管理服务
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);// 获取位置管理器
		application = this;
	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	/**
	 * 返回程序上下文
	 * 
	 * @return
	 */
	public static Context getContext() {
		return mContext;
	}

	public LocationManager getLocationManager() {
		return lm;
	}

	public TelephonyManager getTelephonyManager() {
		return tm;
	}

	public static ActivityManager getActivityManager() {
		return am;
	}

	public ConnectivityManager getConnectivityManager() {
		return cm;
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	public boolean putInfo(String key, Object value) {
		if (preferences != null) {
			Editor editor = preferences.edit();
			if (value instanceof Integer) {
				Integer v = (Integer) value;
				editor.putInt(key, v);
			} else if (value instanceof String) {
				String v = (String) value;
				editor.putString(key, v);
			} else if (value instanceof Boolean) {
				Boolean v = (Boolean) value;
				editor.putBoolean(key, v);
			} else if (value instanceof Float) {
				Float v = (Float) value;
				editor.putFloat(key, v);
			} else if (value instanceof Long) {
				Long v = (Long) value;
				editor.putLong(key, v);
			}
			editor.commit();
			return true;
		}
		return false;
	}

	public SharedPreferences getPreferences() {
		if (preferences != null) {
			return preferences;
		}
		return null;
	}

}
