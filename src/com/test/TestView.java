package com.test;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.view.LogInFrame;

/**
 * test运行类
 * @author Goddard
 *
 */
public class TestView {
	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {

		}
		UIManager.put("RootPane.setupButtonVisible", false);// 清除设置按钮
		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));// 设置BeantuEye外观下JTabbedPane的左缩进
		BeautyEyeLNFHelper.translucencyAtFrameInactive = false;// 设置此开关量为false即表示关闭之，BeautyEye LNF中默认是true
																// 关闭窗口在不活动时的半透明效果
		new LogInFrame().start();
	}
}
