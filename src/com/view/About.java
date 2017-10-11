package com.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;

public class About extends JFrame {

	private JPanel contentPane;

	
	public static void main(String[] args) {
		new About().start();
	}

	public  void start() {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {

		}
		UIManager.put("RootPane.setupButtonVisible", false);// 清除设置按钮
		UIManager.put("TabbedPane.tabAreaInsets"
			    , new javax.swing.plaf.InsetsUIResource(0,0,0,0));//设置BeantuEye外观下JTabbedPane的左缩进
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭子窗口不关闭父窗口
					frame.setAlwaysOnTop(true);	//总是置于顶层
					frame.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth()) / 2,
	                        (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight()) / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(19, 18, 360, 184);
		panel.setLayout(null);
		
		JTextPane txtpnjavaeclipseoracle = new JTextPane();
		txtpnjavaeclipseoracle.setBounds(14, 13, 325, 153);
		txtpnjavaeclipseoracle.setEditable(false);
		txtpnjavaeclipseoracle.setText("\u672C\u8F6F\u4EF6\u91C7\u7528java1.8\uFF0Ceclipse 4.7\uFF0Coracle 11g\u5F00\u53D1\uFF0C\u4F7F\u7528\u4E86beautyeye\uFF0CJFreeChart ,log4j,jxl,,BeanUtils\u7B49\u5F00\u6E90\u6846\u67B6\u3002");
		panel.add(txtpnjavaeclipseoracle);
		contentPane.add(panel);
	}
}
