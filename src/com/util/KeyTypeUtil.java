package com.util;

import java.awt.event.KeyEvent;

/**
 * 监听键盘类型 工具类
 * 
 * @author Goddard
 *
 */
public class KeyTypeUtil {

	private KeyTypeUtil() {

	}

	/**
	 * 监听键盘输入类型 只能输入数字
	 * 
	 * @param evt
	 */
	public static void numType(KeyEvent evt) {
		int keyChar = evt.getKeyChar();
		if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
			evt.consume(); // 关键，屏蔽掉非法输入
		} 
	}

	/**
	 * 监听键盘输入类型 只能输入数字和小数点
	 * 
	 * @param evt
	 */
	public static void priceType(KeyEvent evt) {
		int keyChar = evt.getKeyChar();
		if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 || keyChar == KeyEvent.VK_PERIOD)) {
			evt.consume(); // 关键，屏蔽掉非法输入
		} 
	}

	/**
	 * 监听键盘输入类型 只能输入数字和字母
	 * 
	 * @param evt
	 */
	public static void letterNumType(KeyEvent evt) {
		int keyChar = evt.getKeyChar();
		if (!((keyChar >= KeyEvent.VK_A && keyChar <= KeyEvent.VK_Z) || (keyChar >= 97 && keyChar <= 122)
				|| (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9))) {
			evt.consume();
		} 
	}
	
	/**
	 * 监听键盘输入 按下回车返回true
	 * @param evt
	 * @return	 按下回车返回true
	 */
	public static boolean pressEnter(KeyEvent evt) {
		boolean flag = false;
		if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
			flag = true;
		}
		return flag;
	}

}
