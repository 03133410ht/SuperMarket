package com.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.entity.User;
import com.view.desk.SalesGoodsJPanel;
import com.view.desk.SalesReturnJPanel;
import com.view.employee.AddEmployeeJPanel;
import com.view.employee.EmployeeManageJPanel;
import com.view.goods.AddGoodsJPanel;
import com.view.goods.GoodsManageJPanel;
import com.view.member.AddMemberJPanel;
import com.view.member.MemberManageJPanel;
import com.view.purchase.PurchaseInJpanel;
import com.view.purchase.PurchaseOutJPanel;
import com.view.soldNote.SoldNoteJPanel;
import com.view.soldNote.StatementJpanel;
/**
 * 主程序
 * @author Goddard
 *
 */
public class EmployeeFrame extends JFrame {
	private CardLayout cl_mainPanel;
	private JPanel mainPanel;
	private User currentUser;
	private JPanel contentPane;

	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			
		}
		UIManager.put("RootPane.setupButtonVisible", false);// 清除设置按钮
		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));// 设置BeantuEye外观下JTabbedPane的左缩进
		new EmployeeFrame(null).Start();
	}
	public void Start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//EmployeeFrame frame = new EmployeeFrame(currentUser);
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

	public EmployeeFrame(User user) {
		this.currentUser = user;
		setResizable(false);
		setTitle("\u8D85\u5E02\u7BA1\u7406\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		cl_mainPanel = new CardLayout(0, 0);
		mainPanel.setLayout(cl_mainPanel);

		loadMainJPanel();

		contentPane.add(bottomPanel(), BorderLayout.SOUTH);// 底部栏
		contentPane.add(menusBar(), BorderLayout.NORTH);//顶部栏
	}
	
	/**
	 * 加载主Jpanel
	 */
	public void loadMainJPanel() {
		// 前台管理页面
		JTabbedPane deskCard = new JTabbedPane(JTabbedPane.TOP);
		mainPanel.add(deskCard, "deskCard");

		JPanel salesGoods = new SalesGoodsJPanel(currentUser);
		deskCard.addTab("\u9500\u552E\u51FA\u5E93", null, salesGoods, null);

		JPanel salesReturn = new SalesReturnJPanel(currentUser);
		deskCard.addTab("\u9500\u552E\u9000\u8D27", null, salesReturn, null);

		
	}

	/**
	 * 切换页面按钮
	 * 
	 * @return
	 */
	private JPanel toolbarToggleButoon() {
		JPanel toolPanel = new JPanel();
		contentPane.add(toolPanel, BorderLayout.NORTH);
		toolPanel.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolPanel.add(toolBar, BorderLayout.CENTER);

		JToggleButton deskManageJToggleButton = new JToggleButton("前台管理");
		deskManageJToggleButton.doClick();
		deskManageJToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl_mainPanel.show(mainPanel, "deskCard");
			}
		});
		toolBar.add(deskManageJToggleButton);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(deskManageJToggleButton);
		return toolPanel;
	}

	/**
	 * 底部栏
	 * 
	 * @return
	 */
	public JPanel bottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout(0, 0));

		JTabbedPane bottomTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		bottomPanel.add(bottomTabbedPane, BorderLayout.NORTH);

		JLabel bottomLabel = new JLabel("底部栏");
		setTimer(bottomLabel);
		bottomPanel.add(bottomLabel, BorderLayout.SOUTH);

		return bottomPanel;
	}

	/**
	 * 顶部工具栏
	 * 
	 * @return
	 */
	public JPanel menusBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu systemSet = new JMenu("\u7CFB\u7EDF\u7EF4\u62A4");
		menuBar.add(systemSet);

		JMenuItem initialize = new JMenuItem("\u521D\u59CB\u5316");//初始化按钮
		initialize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPanel.removeAll();
				mainPanel.repaint();
				loadMainJPanel();
			}
		});
		systemSet.add(initialize);

		JMenuItem alterPassword = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");//修改密码按钮
		alterPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangePassword(currentUser).start();
			}
		});
		systemSet.add(alterPassword);

		JPanel menus = toolbarToggleButoon();
		menus.add(menuBar, BorderLayout.NORTH);

		JMenu help = new JMenu("\u5E2E\u52A9");
		menuBar.add(help);

		JMenuItem about = new JMenuItem("\u5173\u4E8E");//关于按钮
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About().start();
			}
		});
		help.add(about);

		JMenuItem exit = new JMenuItem("\u9000\u51FA");//退出按钮
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		help.add(exit);
		return menus;
	}

	/**
	 * 底部栏当前时间
	 * 
	 * @param time
	 */
	private void setTimer(JLabel time) {
		final JLabel varTime = time;
		Timer timeAction = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// 转换日期显示格式
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				varTime.setText("当前时间：" + df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
	}
}
