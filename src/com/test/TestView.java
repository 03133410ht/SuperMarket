package com.test;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.view.LogInFrame;

/**
 * test������
 * @author Goddard
 *
 */
public class TestView {
	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {

		}
		UIManager.put("RootPane.setupButtonVisible", false);// ������ð�ť
		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));// ����BeantuEye�����JTabbedPane��������
		BeautyEyeLNFHelper.translucencyAtFrameInactive = false;// ���ô˿�����Ϊfalse����ʾ�ر�֮��BeautyEye LNF��Ĭ����true
																// �رմ����ڲ��ʱ�İ�͸��Ч��
		new LogInFrame().start();
	}
}
