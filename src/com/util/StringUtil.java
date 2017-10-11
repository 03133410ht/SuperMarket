package com.util;

/**
 * 字符串工具类
 * 
 * @author Goddard
 *
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return 为空返回true
	 */
	public static boolean isEmpty(String str) {
		boolean flag = false;
		if (null == str || "".equals(str.trim())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断数字是否是价格类型
	 * 
	 * @param str
	 * @return 为true则是价格类型
	 */
	public static boolean isPrice(String str) {
		boolean flag = false;
		if ((!StringUtil.isEmpty(str)) && str.matches("\\d{0,10}(\\.\\d{1,2})?")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否是联系方式
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return true/false
	 */
	public static boolean isTel(String str) {
		boolean flag = false;
		if ((!StringUtil.isEmpty(str)) && str.matches("(0\\d{2,3}-){0,1}\\d{7,8}|1[34578]\\d{9}")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否是邮箱
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return true/false
	 */
	public static boolean isEmail(String str) {
		boolean flag = false;
		if ((!StringUtil.isEmpty(str)) && str.matches(
				"[_a-z\\d\\-\\./]+@[_a-z\\d\\-]+(\\.[_a-z\\d\\-]+)*(\\.(info|biz|com|edu|gov|net|am|bz|cn|cx|hk|jp|tw|vc|vn))$")) {
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 判断字符串是否是数字
	 * @param num
	 * @return
	 */
	public static boolean isNumber(String num) {
		boolean flag = false;
		if((!StringUtil.isEmpty(num)) && num.matches("\\d{1,10}")) {
			flag = true;
		}
		return flag;
	}

}
