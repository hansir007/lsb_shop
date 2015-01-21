/**
 * 
 */
package org.han.lsb_shop.util;

import java.util.Map;

/**
 * @author 作者 :wangyiou
 * @version 创建时间：2014-9-1 下午5:25:48
 */

public class Http_Value {
	
	public static final String HTTP_URL = "http://vp.btzt.cn/taihua/";
	private static String TAG = "TAG_Http_Value";

	/***
	 * 登录
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String login(Map<String, String> map) {
		String result = HTTP.getString(HTTP_URL + "m_login", map, "post");
		Loger.i(TAG, "登陆-服务器返回:" + result);
		return result;
	}

	/**
	 * 注册
	 * 
	 * @param map
	 * @return
	 */
	public static String register(Map<String, String> map) {
		String result = HTTP.getString(HTTP_URL + "m_register", map, "post");
		// String result = "{\"result\":\"true\"}";
		Loger.i(TAG, "注册-服务器返回:" + result);
		return result;
	}
}
