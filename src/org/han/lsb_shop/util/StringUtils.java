package org.han.lsb_shop.util;

/**
 * String Tools
 * @author Han Guohong
 */
public class StringUtils {
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str){
		if(str == null){
			return true;
		}else if(str.equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不为空
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str){
		if(str != null && !"".equals(str)){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 去掉空格
	 * @param str
	 * @return String
	 */
	public static String trimToEmpty(String str){
		if(str == null || "".equals(str)){
			return "";
		}else{
			return str.trim();
		}
	}
}
