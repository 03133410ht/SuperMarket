package com.view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.entity.User;
import com.service.UserService;
import com.service.imp.UserServiceImp;
import com.util.KeyTypeUtil;
import com.util.StringUtil;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 修改密码窗口
 * 
 * @author Goddard
 *
 */
public class ChangePassword extends JFrame {

	private JPanel contentPane;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField1;
	private JPasswordField newPasswordField2;
	private User currentUser;
	private UserService userService = new UserServiceImp();

	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			
		}
		UIManager.put("RootPane.setupButtonVisible", false);// 清除设置按钮
		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));// 设置BeantuEye外观下JTabbedPane的左缩进
		new ChangePassword(null).start();
	}

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ChangePassword frame = new ChangePassword(currentUser);
					setVisible(true);
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 关闭子窗口不关闭父窗口
					//setAlwaysOnTop(true); // 总是置于顶层
					setLocation(
							(int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2,
							(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChangePassword(User user) {
		this.currentUser = user;
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 413, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel oldPasswordLabel = new JLabel("\u539F\u5BC6\u7801");
		oldPasswordLabel.setBounds(27, 30, 45, 18);

		oldPasswordField = new JPasswordField();
		oldPasswordField.setBounds(189, 28, 150, 21);

		JLabel newPasswordLabel1 = new JLabel("\u65B0\u5BC6\u7801");
		newPasswordLabel1.setBounds(27, 81, 45, 18);

		JLabel newPasswordLabel = new JLabel("\u91CD\u65B0\u8F93\u5165");
		newPasswordLabel.setBounds(27, 138, 60, 18);

		newPasswordField1 = new JPasswordField();
		newPasswordField1.setBounds(189, 79, 150, 21);

		newPasswordField2 = new JPasswordField();
		newPasswordField2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(KeyTypeUtil.pressEnter(e)) {
					changePassword();
				}
			}
		});
		
		newPasswordField2.setBounds(189, 136, 150, 21);
		contentPane.setLayout(null);
		contentPane.add(oldPasswordLabel);
		contentPane.add(newPasswordLabel1);
		contentPane.add(newPasswordLabel);
		contentPane.add(newPasswordField1);
		contentPane.add(newPasswordField2);
		contentPane.add(oldPasswordField);

		JButton ensureButton = new JButton("\u786E\u5B9A");
		ensureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePassword();
			}
		});
		ensureButton.setBounds(41, 197, 113, 27);
		contentPane.add(ensureButton);

		JButton exitButton = new JButton("\u9000\u51FA");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setBounds(201, 197, 113, 27);
		contentPane.add(exitButton);
	}

	/**
	 * 修改密码事件处理
	 */
	private void changePassword() {
		String oldPassword = currentUser.getPassword();
		String oldPasswordInput = new String(oldPasswordField.getPassword());
		String newPasswordInput1 = new String(newPasswordField1.getPassword());
		String newPasswordInput2 = new String(newPasswordField2.getPassword());
		if (StringUtil.isEmpty(oldPasswordInput)) {
			JOptionPane.showMessageDialog(null, "原密码框不能为空");
			return;
		}
		if (StringUtil.isEmpty(newPasswordInput1)) {
			JOptionPane.showMessageDialog(null, "新密码框不能为空");
			return;
		}
		if (StringUtil.isEmpty(newPasswordInput1)) {
			JOptionPane.showMessageDialog(null, "重新输入框不能为空");
			return;
		}
		if (!oldPassword.equals(oldPasswordInput)) {
			JOptionPane.showMessageDialog(null, "原密码不正确");
			return;
		}
		if (newPasswordInput1.length() > 10 || newPasswordInput1.length() < 6 || newPasswordInput2.length() > 10
				|| newPasswordInput2.length() < 6) {
			JOptionPane.showMessageDialog(null, "新密码和重新输入密码的长度只能为6-10之间");
			return;
		}
		if(!newPasswordInput1.equals(newPasswordInput2)) {
			JOptionPane.showMessageDialog(null, "新密码和重新输入密码不相同");
			return;
		}
		if(userService.changePassword(currentUser, newPasswordInput1)) {
			JOptionPane.showMessageDialog(null, "修改成功");
			dispose();
		}else {
			JOptionPane.showMessageDialog(null, "修改失败");
			return;
		}
	}
}
