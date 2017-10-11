package com.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.entity.User;
import com.service.UserService;
import com.service.imp.UserServiceImp;
import com.util.KeyTypeUtil;
import com.util.StringUtil;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * ��½����
 * 
 * @author Goddard
 *
 */
public class LogInFrame extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private UserService userService = new UserServiceImp();

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

	public  void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//LogInFrame frame = new LogInFrame();
					setVisible(true);
					setLocation(
							(int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2,
							(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public LogInFrame() {
		setTitle("\u767B\u9646");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 478);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);

		JPanel rail = new JPanel();
		rail.setBounds(0, 0, 627, 185);

		JLabel username = new JLabel("\u7528\u6237\u540D");
		username.setBounds(111, 206, 45, 18);
		username.setForeground(new Color(0, 0, 0));
		username.setBackground(new Color(0, 0, 0));

		JLabel password = new JLabel("\u5BC6  \u7801");
		password.setBounds(110, 260, 46, 18);

		usernameField = new JTextField();
		usernameField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.letterNumType(e);
			}
		});
		usernameField.setBounds(234, 203, 169, 24);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(234, 257, 169, 24);

		JButton logInButton = new JButton("\u767B   \u5F55");
		logInButton.setBounds(204, 313, 176, 54);
		logInButton.setFont(new Font("����", Font.PLAIN, 23));
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginActionPerformed(e);
			}
		});
		getRootPane().setDefaultButton(logInButton);
		contentPane.setLayout(null);
		rail.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JLabel bcakground = new JLabel("");
		bcakground.setIcon(new ImageIcon(LogInFrame.class.getResource("/images/LoginBackground.png")));
		bcakground.setSize(640, 162);
		rail.add(bcakground);
		contentPane.add(rail);
		contentPane.add(password);
		contentPane.add(username);
		contentPane.add(usernameField);
		contentPane.add(passwordField);
		contentPane.add(logInButton);
	}

	/**
	 * ��½�¼�����
	 * 
	 * @param evt
	 */
	private void loginActionPerformed(ActionEvent evt) {
		String username = this.usernameField.getText().toLowerCase();
		String password = new String(this.passwordField.getPassword());

		if (StringUtil.isEmpty(username)) {
			JOptionPane.showMessageDialog(null, "�û�������Ϊ��!");
			return;
		}
		if (StringUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��!");
			return;
		}

		User user = new User(username, password);
		User currentUser = userService.login(user);
		if (currentUser != null) {
			dispose();
			String userType = currentUser.getUsertype();
			if("admin".equals(userType)) {
				new MainFrame(currentUser).Start();
			}else if("employee".equals(userType)){
				new EmployeeFrame(currentUser).Start();
			}
		} else {
			JOptionPane.showMessageDialog(null, "�û��������������");
		}

	}
}
