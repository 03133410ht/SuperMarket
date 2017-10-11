package com.util;

import java.awt.event.KeyEvent;

/**
 * ������������ ������
 * 
 * @author Goddard
 *
 */
public class KeyTypeUtil {

	private KeyTypeUtil() {

	}

	/**
	 * ���������������� ֻ����������
	 * 
	 * @param evt
	 */
	public static void numType(KeyEvent evt) {
		int keyChar = evt.getKeyChar();
		if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
			evt.consume(); // �ؼ������ε��Ƿ�����
		} 
	}

	/**
	 * ���������������� ֻ���������ֺ�С����
	 * 
	 * @param evt
	 */
	public static void priceType(KeyEvent evt) {
		int keyChar = evt.getKeyChar();
		if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 || keyChar == KeyEvent.VK_PERIOD)) {
			evt.consume(); // �ؼ������ε��Ƿ�����
		} 
	}

	/**
	 * ���������������� ֻ���������ֺ���ĸ
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
	 * ������������ ���»س�����true
	 * @param evt
	 * @return	 ���»س�����true
	 */
	public static boolean pressEnter(KeyEvent evt) {
		boolean flag = false;
		if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
			flag = true;
		}
		return flag;
	}

}
