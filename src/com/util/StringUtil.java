package com.util;

/**
 * �ַ���������
 * 
 * @author Goddard
 *
 */
public class StringUtil {

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param str
	 *            ��Ҫ�жϵ��ַ���
	 * @return Ϊ�շ���true
	 */
	public static boolean isEmpty(String str) {
		boolean flag = false;
		if (null == str || "".equals(str.trim())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * �ж������Ƿ��Ǽ۸�����
	 * 
	 * @param str
	 * @return Ϊtrue���Ǽ۸�����
	 */
	public static boolean isPrice(String str) {
		boolean flag = false;
		if ((!StringUtil.isEmpty(str)) && str.matches("\\d{0,10}(\\.\\d{1,2})?")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * �ж��Ƿ�����ϵ��ʽ
	 * 
	 * @param str
	 *            Ҫ�жϵ��ַ���
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
	 * �ж��Ƿ�������
	 * 
	 * @param str
	 *            Ҫ�жϵ��ַ���
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
	 * �ж��ַ����Ƿ�������
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
