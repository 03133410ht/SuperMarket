package com.view.desk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComboBoxUI;

import com.entity.Employee;
import com.entity.Goods;
import com.entity.Member;
import com.entity.Sales;
import com.entity.User;
import com.service.EmployeeService;
import com.service.GoodsService;
import com.service.MemberService;
import com.service.SalesService;
import com.service.UserService;
import com.service.imp.EmployeeServiceImp;
import com.service.imp.GoodsServiceImp;
import com.service.imp.MemberServiceImp;
import com.service.imp.SalesServiceImp;
import com.service.imp.UserServiceImp;
import com.util.KeyTypeUtil;
import com.util.StringUtil;

/**
 * 销售出库panel
 * 
 * @author Goddard
 *
 */
public class SalesGoodsJPanel extends JPanel {

	private SaleGoodsJtable sgTable;
	private Long gid;
	private JTextField sgMoneyTextField;
	private JTextField sgNumTextField;
	private JTextField sgIntegralTextField;
	private JTextField sgGidTextField;
	private JTextField sgGoodsTextField;
	private SalesService salesService = new SalesServiceImp();
	private EmployeeService employeeService = new EmployeeServiceImp();
	private MemberService memberService = new MemberServiceImp();
	private UserService userService = new UserServiceImp();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private GoodsService goodsService = new GoodsServiceImp();
	private DefaultComboBoxModel employeeModel;
	private DefaultComboBoxModel goodsModel;
	private List<Employee> employeelist;
	private User currentUser;
	private JComboBox sgUserComboBox;
	private JComboBox goodsComboBox;
	private List<Goods> goodsList;
	private JTextArea sgRemarkTextArea;
	private DecimalFormat df;
	private DateField sgdateComboBox;

	public SalesGoodsJPanel(User currentUser) {

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					checkActionPerformed();
				}
			}
		});
		this.currentUser = currentUser;
		sales();
		setSalesId();
	}

	private void sales() {
		df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		JButton sgCherckButton = new JButton("\u7ED3\u8D26\uFF08F5\uFF09");
		sgCherckButton.setBounds(41, 450, 345, 76);
		sgCherckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkActionPerformed();
			}
		});
		sgCherckButton.setFont(new Font("方正兰亭黑简体", Font.BOLD, 34));

		JScrollPane sgScrollPane = new JScrollPane();

		sgScrollPane.setBounds(14, 126, 1268, 317);

		JLabel sgMoneyLabel = new JLabel("\u5E94\u6536");
		sgMoneyLabel.setBounds(508, 473, 54, 32);
		sgMoneyLabel.setFont(new Font("宋体", Font.PLAIN, 27));

		sgMoneyTextField = new JTextField();
		sgMoneyTextField.setFont(new Font("宋体", Font.PLAIN, 27));
		sgMoneyTextField.setBounds(594, 456, 131, 49);
		sgMoneyTextField.setEditable(false);
		sgMoneyTextField.setColumns(10);

		JLabel sgNumLabel = new JLabel("\u603B\u6570\u91CF");
		sgNumLabel.setBounds(758, 473, 81, 32);
		sgNumLabel.setFont(new Font("宋体", Font.PLAIN, 27));

		sgNumTextField = new JTextField();
		sgNumTextField.setFont(new Font("宋体", Font.PLAIN, 27));
		sgNumTextField.setBounds(854, 456, 118, 49);
		sgNumTextField.setEditable(false);
		sgNumTextField.setColumns(10);

		JLabel sgIntegralLabel = new JLabel("\u672C\u5355\u79EF\u5206");
		sgIntegralLabel.setBounds(993, 473, 108, 32);
		sgIntegralLabel.setFont(new Font("宋体", Font.PLAIN, 27));

		sgIntegralTextField = new JTextField();
		sgIntegralTextField.setFont(new Font("宋体", Font.PLAIN, 27));
		sgIntegralTextField.setBounds(1115, 457, 100, 48);
		sgIntegralTextField.setEditable(false);
		sgIntegralTextField.setColumns(10);

		sgTable = new SaleGoodsJtable();
		sgTable.getColumnModel().addColumnModelListener(new TableColumnModelListener() {

			public void columnAdded(TableColumnModelEvent e) {
			}

			public void columnRemoved(TableColumnModelEvent e) {
			}

			public void columnMoved(TableColumnModelEvent e) {
			}

			public void columnMarginChanged(ChangeEvent e) {
			}

			public void columnSelectionChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					sgTable.refresh();
					countMonryNumPoint();
				}
			}

		});

		sgScrollPane.setViewportView(sgTable);

		JLabel sgGidLabel = new JLabel("\u5355\u53F7");
		sgGidLabel.setBounds(14, 28, 30, 18);

		sgGidTextField = new JTextField();
		sgGidTextField.setBounds(50, 25, 154, 24);
		sgGidTextField.setEditable(false);
		sgGidTextField.setColumns(10);

		JLabel sgGoodsLabel = new JLabel("\u9009\u62E9\u5546\u54C1");
		sgGoodsLabel.setBounds(14, 81, 60, 18);

		sgGoodsTextField = new JTextField();
		sgGoodsTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					loadGoods();
				}
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					checkActionPerformed();
				}
			}
		});
		sgGoodsTextField.setBounds(80, 78, 166, 24);
		sgGoodsTextField.setColumns(10);

		JLabel sgDateLabel = new JLabel("\u65E5\u671F");
		sgDateLabel.setBounds(508, 26, 34, 20);
		sgDateLabel.setFont(new Font("宋体", Font.PLAIN, 17));

		JLabel sgUserLabel = new JLabel("\u5236\u5355\u4EBA");
		sgUserLabel.setBounds(227, 28, 45, 18);

		sgdateComboBox = new DateField();
		sgdateComboBox.setBounds(567, 28, 166, 25);
		sgdateComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		employeeModel = new DefaultComboBoxModel();
		employeelist = employeeService.queryAll();
		for (Employee employee : employeelist) {
			employeeModel.addElement(employee.getEname());
		}
		sgUserComboBox = new JComboBox(employeeModel);
		sgUserComboBox.setBounds(293, 25, 166, 24);
		add(sgUserComboBox);
		if (currentUser != null) {
			sgUserComboBox.setSelectedItem(userService.queryEmployee(currentUser).getEname());
		}

		JLabel sgRemarkLabel = new JLabel("\u5907\u6CE8");
		sgRemarkLabel.setBounds(783, 28, 30, 18);

		sgRemarkTextArea = new JTextArea();
		sgRemarkTextArea.setBounds(841, 26, 316, 84);
		sgRemarkTextArea.setBorder(BorderFactory.createEtchedBorder());

		JButton sgDeleteButton = new JButton("\u5220\u9664");
		sgDeleteButton.setBounds(628, 77, 105, 27);
		sgDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteActionPerformed();
			}
		});
		setLayout(null);
		add(sgGidLabel);
		add(sgGidTextField);
		add(sgGoodsLabel);
		add(sgGoodsTextField);
		add(sgDeleteButton);
		add(sgDateLabel);
		add(sgUserLabel);

		add(sgdateComboBox);
		add(sgRemarkLabel);
		add(sgRemarkTextArea);
		add(sgCherckButton);
		add(sgMoneyLabel);
		add(sgMoneyTextField);
		add(sgNumLabel);
		add(sgNumTextField);
		add(sgIntegralLabel);
		add(sgIntegralTextField);
		add(sgScrollPane);

		goodsModel = new DefaultComboBoxModel();
		goodsComboBox = new JComboBox(goodsModel);

		goodsComboBox.setBounds(293, 78, 166, 24);
		add(goodsComboBox);

		JButton addGoodsButton = new JButton("\u6DFB\u52A0\u5546\u54C1");
		addGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGoods();
			}
		});
		addGoodsButton.setBounds(501, 77, 105, 27);
		add(addGoodsButton);

	}

	/**
	 * 结账事件处理
	 */
	private void checkActionPerformed() {
		if (sgTable.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "请添加商品后再结账");
			return;
		}
		new Clearing().start();
	}

	/**
	 * 删除事件处理
	 */
	private void deleteActionPerformed() {
		if (sgTable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行再删除");
			return;
		}
		if (JOptionPane.showConfirmDialog(null, "是否删除", "删除", JOptionPane.YES_NO_OPTION) == 0) {
			sgTable.deleteRow();
		}
		countMonryNumPoint();

	}

	/**
	 * 添加商品到待结算表格
	 */
	private void addGoods() {
		if (goodsModel.getSize() == 0) {
			JOptionPane.showMessageDialog(null, "请搜索后再添加商品");
			return;
		}
		boolean flag = true;
		String remark = sgRemarkTextArea.getText();
		if (goodsList.size() == 1) {
			sgTable.addRow(goodsList.get(0));
			flag = false;
		}
		if (flag) {
			for (Goods goods : goodsList) {
				if (goodsComboBox.getSelectedItem().toString().equals(goods.getGname())) {
					sgTable.addRow(goods);
				}
			}
		}
		countMonryNumPoint();
		sgGoodsTextField.setText("");
	}

	/**
	 * 计算应付 总数量 和本单积分
	 */
	private void countMonryNumPoint() {
		int row = sgTable.getRowCount();
		double money = 0;
		int num = 0;
		int point = 0;
		for (int i = 0; i < row; i++) {
			money += Double.parseDouble(sgTable.getValueAt(i, 6).toString());
			num += Integer.parseInt(sgTable.getValueAt(i, 4).toString());
			point += Integer.parseInt(sgTable.getValueAt(i, 7).toString());
		}
		sgMoneyTextField.setText(df.format(money) + "");
		sgNumTextField.setText(String.valueOf(num));
		sgIntegralTextField.setText(point + "");
		requestFocus();
		sgGoodsTextField.requestFocusInWindow();
	}

	/**
	 * 模糊搜索加载到选择框
	 */
	private void loadGoods() {
		String fuzzy = sgGoodsTextField.getText();
		if (StringUtil.isEmpty(fuzzy)) {
			return;
		}
		goodsList = goodsService.fuzzyQuery(fuzzy, "", "> -1");
		goodsModel.removeAllElements();
		for (Goods goods : goodsList) {
			goodsModel.addElement(goods.getGname());
		}
		if (goodsList.size() == 1) {
			addGoods();
		}
	}

	/**
	 * 动态加载员工
	 */
	private void loadEmployee() {
		employeeModel.removeAllElements();
		employeelist = employeeService.queryAll();
		for (Employee employee : employeelist) {
			employeeModel.addElement(employee.getEname());
		}
		if (currentUser != null) {
			sgUserComboBox.setSelectedItem(userService.queryEmployee(currentUser).getEname());
		}
	}

	/**
	 * 动态加载单号
	 */
	public void setSalesId() {
		Sales sales = salesService.querySalesID(Integer.parseInt(sdf.format(sgdateComboBox.getDate()).toString()));
		if (sales == null) {
			sgGidTextField.setText(sdf.format(sgdateComboBox.getDate()).toString() + "000001");
		} else {
			sgGidTextField.setText((sales.getSalesid() + 1) + "");
		}
	}

	/**
	 * 前台结算页面
	 * 
	 * @author Goddard
	 *
	 */
	public class Clearing extends JFrame {

		private JPanel contentPane;
		private JTextField dealTextField;
		private JTextField cashTextField;
		private JTextField cardTextField;
		private JTextField memberPriceTextField;
		private JTextField changeTextField;
		private JTextField memberIdTextField;
		private JPasswordField memberPasswordField;
		private JTextField memberAccountTextField;
		private JPanel cahsPanel;
		private JPanel cardPanel;
		private JPanel memberPricePanel;
		private JPanel memberAccountpanel;
		private JPanel changePanel;
		private JRadioButton cashRadioButton;
		private JRadioButton cardRadioButton;
		private JRadioButton memberPriceRadioButton;
		private JRadioButton memberRadioButton;
		private String discount;
		private double memberPrice;
		private Member member;

		private void start() {
			try {
				org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			} catch (Exception e) {

			}
			UIManager.put("RootPane.setupButtonVisible", false);// 清除设置按钮
			UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));// 设置BeantuEye外观下JTabbedPane的左缩进
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						setVisible(true);
						setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2,
								(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		public Clearing() {
			setTitle("\u4ED8\u6B3E");
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 530, 700);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);

			JPanel paymentPanel = new JPanel();
			paymentPanel.setBounds(18, 469, 432, 71);
			paymentPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
					"\u8BF7\u9009\u62E9\u652F\u4ED8\u65B9\u5F0F", TitledBorder.LEADING, TitledBorder.TOP, null,
					Color.BLACK));

			JButton ensureButton = new JButton("\u786E\u5B9A");
			ensureButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearingActionPerformed();
				}
			});
			ensureButton.setFont(new Font("宋体", Font.BOLD, 15));
			ensureButton.setBounds(78, 553, 85, 38);

			JButton exitButton = new JButton("\u9000\u51FA");
			exitButton.setFont(new Font("宋体", Font.BOLD, 15));
			exitButton.setBounds(272, 553, 85, 38);
			exitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			cashRadioButton = new JRadioButton("\u73B0\u91D1");
			cashRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cashActionPerformed();
				}
			});
			cashRadioButton.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}
				}
			});
			cashRadioButton.setBounds(16, 29, 69, 27);
			cashRadioButton.setSelected(true);

			cardRadioButton = new JRadioButton("\u5237\u5361");
			cardRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardActionPerformed();
				}
			});
			cardRadioButton.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}
				}
			});
			cardRadioButton.setBounds(109, 29, 69, 27);

			memberRadioButton = new JRadioButton("\u4F1A\u5458\u8D26\u6237");
			memberRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					memberActionPerformed();
				}
			});
			memberRadioButton.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}
				}
			});
			memberRadioButton.setBounds(203, 29, 96, 27);

			memberPriceRadioButton = new JRadioButton("\u4F1A\u5458\u73B0\u91D1");
			memberPriceRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					memberPriceActionPerformed();
				}
			});
			memberPriceRadioButton.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}
				}
			});
			memberPriceRadioButton.setBounds(326, 29, 96, 27);
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(cashRadioButton);
			buttonGroup.add(cardRadioButton);
			buttonGroup.add(memberRadioButton);
			buttonGroup.add(memberPriceRadioButton);

			JPanel infoPanel = new JPanel();
			infoPanel.setBackground(Color.WHITE);
			infoPanel.setBounds(19, 13, 431, 443);
			infoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY));
			paymentPanel.setLayout(null);
			paymentPanel.add(cashRadioButton);
			paymentPanel.add(cardRadioButton);
			paymentPanel.add(memberRadioButton);
			paymentPanel.add(memberPriceRadioButton);
			infoPanel.setLayout(null);

			contentPane.setLayout(null);
			contentPane.add(ensureButton);
			contentPane.add(exitButton);
			contentPane.add(paymentPanel);
			contentPane.add(infoPanel);

			JPanel dealPanel = new JPanel();
			dealPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			dealPanel.setBounds(0, 0, 431, 67);
			infoPanel.add(dealPanel);
			dealPanel.setLayout(null);

			JLabel dealLabel = new JLabel("\u5E94\u4ED8\u91D1\u989D");
			dealLabel.setFont(new Font("宋体", Font.BOLD, 30));
			dealLabel.setBounds(33, 13, 128, 41);
			dealPanel.add(dealLabel);
			dealTextField = new JTextField();
			dealTextField.setEditable(false);

			dealTextField.setBounds(233, 13, 158, 38);

			dealTextField.setFont(new Font("微软雅黑", Font.BOLD, 22));
			dealPanel.add(dealTextField);
			dealTextField.setColumns(10);
			dealTextField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}
				}
			});
			cahsPanel = new JPanel();
			cahsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			cahsPanel.setBounds(0, 67, 431, 67);
			infoPanel.add(cahsPanel);
			cahsPanel.setLayout(null);

			JLabel cashLabel = new JLabel("\u73B0\u91D1\u91D1\u989D");
			cashLabel.setFont(new Font("宋体", Font.BOLD, 30));
			cashLabel.setBounds(33, 13, 128, 41);
			cahsPanel.add(cashLabel);

			cashTextField = new JTextField();
			cashTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					KeyTypeUtil.priceType(e);
				}

				public void keyPressed(KeyEvent e) {
					if ((!StringUtil.isEmpty(cashTextField.getText().toString())) && KeyTypeUtil.pressEnter(e)) {
						double dealPrice = Double.parseDouble(cashTextField.getText().toString());
						if (memberPriceRadioButton.isSelected()) {
							changeTextField.setText(df.format(dealPrice - memberPrice));
						} else {
							changeTextField.setText(
									df.format((dealPrice - Double.parseDouble(dealTextField.getText().toString())))
											+ "");
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}

				}
			});
			cashTextField.setBounds(233, 13, 158, 38);
			cashTextField.setFont(new Font("微软雅黑", Font.BOLD, 22));
			cahsPanel.add(cashTextField);
			cashTextField.setColumns(10);

			cardPanel = new JPanel();
			cardPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			cardPanel.setBounds(0, 135, 431, 67);
			infoPanel.add(cardPanel);
			cardPanel.setLayout(null);

			JLabel cardLabel = new JLabel("\u5237\u5361\u91D1\u989D");
			cardLabel.setFont(new Font("宋体", Font.BOLD, 30));
			cardLabel.setBounds(33, 13, 128, 41);
			cardPanel.add(cardLabel);

			cardTextField = new JTextField();
			cardTextField.setBounds(233, 13, 158, 38);
			cardPanel.add(cardTextField);
			cardTextField.setFont(new Font("微软雅黑", Font.BOLD, 22));
			cardTextField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}
				}
			});
			cardTextField.setColumns(10);

			memberPricePanel = new JPanel();
			memberPricePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			memberPricePanel.setBounds(0, 309, 431, 67);
			infoPanel.add(memberPricePanel);
			memberPricePanel.setLayout(null);

			JLabel memberPriceLabel = new JLabel("会员折扣价");
			memberPriceLabel.setFont(new Font("宋体", Font.BOLD, 30));
			memberPriceLabel.setBounds(33, 13, 165, 41);
			memberPricePanel.add(memberPriceLabel);

			memberPriceTextField = new JTextField();
			memberPriceTextField.setBounds(233, 13, 158, 38);
			memberPricePanel.add(memberPriceTextField);
			memberPriceTextField.setFont(new Font("微软雅黑", Font.BOLD, 22));
			memberPriceTextField.setColumns(10);

			memberAccountpanel = new JPanel();
			memberAccountpanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			memberAccountpanel.setBounds(0, 202, 431, 107);
			infoPanel.add(memberAccountpanel);
			memberAccountpanel.setLayout(null);

			JLabel memberIdLabel = new JLabel("\u4F1A\u5458id");
			memberIdLabel.setFont(new Font("宋体", Font.BOLD, 17));
			memberIdLabel.setBounds(38, 13, 79, 18);
			memberAccountpanel.add(memberIdLabel);

			memberIdTextField = new JTextField();
			memberIdTextField.setBounds(235, 10, 156, 24);
			memberAccountpanel.add(memberIdTextField);
			memberIdTextField.setFont(new Font("微软雅黑", Font.BOLD, 18));
			memberIdTextField.setColumns(10);

			JLabel memberPasswordLabel = new JLabel("\u4F1A\u5458\u5BC6\u7801");
			memberPasswordLabel.setFont(new Font("宋体", Font.BOLD, 17));
			memberPasswordLabel.setBounds(38, 44, 72, 18);
			memberAccountpanel.add(memberPasswordLabel);

			memberPasswordField = new JPasswordField();
			memberPasswordField.setBounds(235, 41, 156, 24);
			memberPasswordField.setFont(new Font("微软雅黑", Font.BOLD, 18));
			memberPasswordField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (KeyTypeUtil.pressEnter(e)) {
						member = memberService
								.queryMember(new Member(Integer.parseInt(memberIdTextField.getText().toString()),
										new String(memberPasswordField.getPassword())));
						if (member == null) {
							JOptionPane.showMessageDialog(null, "登陆失败，帐号或者密码错误");
							return;
						} else {
							discount = memberService.queryDiscount(member.getMgrade());

							memberAccountTextField.setText(member.getMaccount() + "");
							JOptionPane.showMessageDialog(null,
									"登陆成功,当前会员是" + member.getMgrade() + ",享受" + discount + "的折扣率");
							memberPrice = Double.parseDouble(df.format(Double.parseDouble(discount)
									* Double.parseDouble(dealTextField.getText().toString())));
							memberPriceTextField.setText(memberPrice + "");
							if (memberRadioButton.isSelected()) {
								if (memberPrice > member.getMaccount()) {
									JOptionPane.showMessageDialog(null, "会员余额不足，请选择其他方式支付");
								}
							}
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_F5) {
						clearingActionPerformed();
					}
				}
			});
			memberAccountpanel.add(memberPasswordField);

			JLabel memberAccountLabel = new JLabel("\u4F1A\u5458\u8D26\u6237\u4F59\u989D");
			memberAccountLabel.setFont(new Font("宋体", Font.BOLD, 17));
			memberAccountLabel.setBounds(38, 76, 114, 18);
			memberAccountpanel.add(memberAccountLabel);

			memberAccountTextField = new JTextField();
			memberAccountTextField.setEditable(false);
			memberAccountTextField.setBounds(235, 73, 156, 24);
			memberAccountpanel.add(memberAccountTextField);
			memberAccountTextField.setFont(new Font("微软雅黑", Font.BOLD, 18));
			memberAccountTextField.setColumns(10);

			changePanel = new JPanel();
			changePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			changePanel.setBounds(0, 376, 431, 67);
			infoPanel.add(changePanel);
			changePanel.setLayout(null);

			JLabel changeLabel = new JLabel("\u627E\u96F6");
			changeLabel.setFont(new Font("宋体", Font.BOLD, 30));
			changeLabel.setBounds(33, 13, 128, 41);
			changePanel.add(changeLabel);

			changeTextField = new JTextField();
			changeTextField.setEditable(false);
			changeTextField.setBounds(233, 13, 158, 38);
			changePanel.add(changeTextField);
			changeTextField.setFont(new Font("微软雅黑", Font.BOLD, 22));
			changeTextField.setColumns(10);

			cashRadioButton.doClick();
			loadPrice();
		}

		/**
		 * 确定事件处理
		 */
		private void clearingActionPerformed() {
			Sales sales = null;
			Map<Integer, Integer> mapGoods = new HashMap<Integer, Integer>();
			long salesid = Long.parseLong(sgGidTextField.getText().toString());
			int eid = userService.queryEmployee(currentUser).getEid();
			java.sql.Date sdate = new java.sql.Date(sgdateComboBox.getDate().getTime());
			String remark = sgRemarkTextArea.getText().toString();
			double mgathering = 0;
			if (cashRadioButton.isSelected()) {
				mgathering = Double.parseDouble(cashTextField.getText().toString());
				sales = new Sales(salesid, eid, 10000, sdate, mgathering, remark);
			}
			if (cardRadioButton.isSelected()) {
				mgathering = Double.parseDouble(cardTextField.getText().toString());
				sales = new Sales(salesid, eid, 10000, sdate, mgathering, remark);

			}
			if (memberRadioButton.isSelected()) {
				if (member == null) {
					JOptionPane.showMessageDialog(null, "请登陆会员后再结账");
					return;
				} else {
					if (memberPrice > member.getMaccount()) {
						JOptionPane.showMessageDialog(null, "会员余额不足，请选择其他方式支付");
						return;
					}
				}
				mgathering = Double.parseDouble(memberPriceTextField.getText().toString());
				sales = new Sales(salesid, eid, member.getMid(), sdate, mgathering, remark);

			}
			if (memberPriceRadioButton.isSelected()) {
				if (member == null) {
					JOptionPane.showMessageDialog(null, "请登陆会员后再结账");
					return;
				}
				mgathering = Double.parseDouble(memberPriceTextField.getText().toString());
				sales = new Sales(salesid, eid, member.getMid(), sdate, mgathering, remark);

			}
			for (int i = 0; i < sgTable.getRowCount(); i++) {
				mapGoods.put(Integer.parseInt(sgTable.getModel().getValueAt(i, 0).toString()),
						Integer.parseInt(sgTable.getModel().getValueAt(i, 4).toString()));
			}
			if (salesService.addSales(sales, mapGoods)) {
				JOptionPane.showMessageDialog(null, "结算成功");
				if (memberRadioButton.isSelected()) {
					memberService.consume(member.getMid(), mgathering, true);
					Member newMember = memberService.queryByid(member.getMid() + "").get(0);
					JOptionPane.showMessageDialog(null, "当前会员余额为 " + newMember.getMaccount());
					String str = memberService.isUpgrade(newMember);
					if (str != null) {
						JOptionPane.showMessageDialog(null,
								"当前会员升级为" + str + "，享受" + memberService.queryDiscount(str) + "折扣率");
					}

				}
				if (memberPriceRadioButton.isSelected()) {
					memberService.consume(member.getMid(), mgathering, false);
				}
				for (int i = 0; i < sgTable.getRowCount(); i++) {
					if (goodsService.minusAccount(Integer.parseInt(sgTable.getValueAt(i, 0).toString()),
							Integer.parseInt(sgTable.getValueAt(i, 4).toString()))) {
					}else {
						JOptionPane.showMessageDialog(null, "结算失败");
					}
				}
				dispose();
				sgTable.deleteAll();
				setSalesId();
				sgMoneyTextField.setText("");
				sgNumTextField.setText("");
				sgIntegralTextField.setText("");
				goodsModel.removeAllElements();

			} else {
				JOptionPane.showMessageDialog(null, "结算失败");
			}

		}

		/**
		 * 加载价格
		 */
		private void loadPrice() {
			dealTextField.setText(sgMoneyTextField.getText());
			cashTextField.setText(sgMoneyTextField.getText());

		}

		/**
		 * 会员现金事件处理
		 */
		private void memberPriceActionPerformed() {
			cashTextField.setText(sgMoneyTextField.getText());
			// cahsPanel.setBackground(Color.GRAY);
			cahsPanel.setBackground(new Color(240, 240, 240));
			cardPanel.setBackground(Color.GRAY);
			// cardPanel.setBackground(new Color(240, 240, 240));
			// memberPricePanel.setBackground(Color.GRAY);
			memberPricePanel.setBackground(new Color(240, 240, 240));
			memberAccountpanel.setBackground(new Color(240, 240, 240));
			changePanel.setBackground(new Color(240, 240, 240));

			cashTextField.setEditable(true);
			cashTextField.setBackground(dealTextField.getSelectedTextColor());
			cardTextField.setBackground(Color.LIGHT_GRAY);
			cardTextField.setEditable(false);
			cardTextField.setText("");
			memberPriceTextField.setBackground(dealTextField.getSelectedTextColor());
			memberPriceTextField.setEditable(false);
			memberIdTextField.setBackground(dealTextField.getSelectedTextColor());
			memberIdTextField.setEditable(true);
			memberPasswordField.setBackground(dealTextField.getSelectedTextColor());
			memberPasswordField.setEditable(true);
			memberAccountTextField.setBackground(dealTextField.getSelectedTextColor());
			memberAccountTextField.setEditable(false);
			changeTextField.setBackground(dealTextField.getSelectedTextColor());
			changeTextField.setText("");
		}

		/**
		 * 现金按钮事件处理
		 */
		private void cashActionPerformed() {
			loadPrice();
			// cahsPanel.setBackground(Color.GRAY);
			cahsPanel.setBackground(new Color(240, 240, 240));
			cardPanel.setBackground(Color.GRAY);
			// cardPanel.setBackground(new Color(240, 240, 240));
			memberPricePanel.setBackground(Color.GRAY);
			// memberPricePanel.setBackground(new Color(240, 240, 240));
			memberAccountpanel.setBackground(Color.GRAY);
			// memberAccountpanel.setBackground(new Color(240, 240, 240));
			changePanel.setBackground(new Color(240, 240, 240));

			cashTextField.setBackground(dealTextField.getSelectedTextColor());
			cashTextField.setEditable(true);
			cardTextField.setBackground(Color.LIGHT_GRAY);
			cardTextField.setEditable(false);
			cardTextField.setText("");
			memberPriceTextField.setBackground(Color.LIGHT_GRAY);
			memberPriceTextField.setEditable(false);
			memberPriceTextField.setText("");
			memberIdTextField.setBackground(Color.LIGHT_GRAY);
			memberIdTextField.setEditable(false);
			memberIdTextField.setText("");
			memberPasswordField.setBackground(Color.LIGHT_GRAY);
			memberPasswordField.setEditable(false);
			memberPasswordField.setText("");
			memberAccountTextField.setBackground(Color.LIGHT_GRAY);
			memberAccountTextField.setEditable(false);
			memberAccountTextField.setText("");
			changeTextField.setBackground(dealTextField.getSelectedTextColor());
		}

		/**
		 * 刷卡事件处理
		 */
		private void cardActionPerformed() {
			cahsPanel.setBackground(Color.GRAY);
			// cahsPanel.setBackground(new Color(240, 240, 240));
			// cardPanel.setBackground(Color.GRAY);
			cardPanel.setBackground(new Color(240, 240, 240));
			memberPricePanel.setBackground(Color.GRAY);
			// memberPricePanel.setBackground(new Color(240, 240, 240));
			memberAccountpanel.setBackground(Color.GRAY);
			// memberAccountpanel.setBackground(new Color(240, 240, 240));
			changePanel.setBackground(Color.GRAY);
			// changePanel.setBackground(new Color(240, 240, 240));

			cashTextField.setEditable(false);
			cashTextField.setBackground(Color.LIGHT_GRAY);
			cashTextField.setText("");
			cardTextField.setBackground(dealTextField.getSelectedTextColor());
			cardTextField.setEditable(true);
			cardTextField.setText(sgMoneyTextField.getText());
			memberPriceTextField.setBackground(Color.LIGHT_GRAY);
			memberPriceTextField.setEditable(false);
			memberPriceTextField.setText("");
			memberIdTextField.setBackground(Color.LIGHT_GRAY);
			memberIdTextField.setEditable(false);
			memberIdTextField.setText("");
			memberPasswordField.setBackground(Color.LIGHT_GRAY);
			memberPasswordField.setEditable(false);
			memberPasswordField.setText("");
			memberAccountTextField.setBackground(Color.LIGHT_GRAY);
			memberAccountTextField.setEditable(false);
			memberAccountTextField.setText("");
			changeTextField.setBackground(Color.LIGHT_GRAY);
			changeTextField.setText("");
		}

		/**
		 * 会员账户事件处理
		 */
		private void memberActionPerformed() {
			cahsPanel.setBackground(Color.GRAY);
			cardPanel.setBackground(Color.GRAY);
			memberPricePanel.setBackground(new Color(240, 240, 240));
			memberAccountpanel.setBackground(new Color(240, 240, 240));
			changePanel.setBackground(Color.GRAY);

			cashTextField.setEditable(false);
			cashTextField.setBackground(Color.LIGHT_GRAY);
			cashTextField.setText("");
			cardTextField.setBackground(Color.LIGHT_GRAY);
			cardTextField.setEditable(false);
			cardTextField.setText("");
			memberPriceTextField.setBackground(dealTextField.getSelectedTextColor());
			memberPriceTextField.setEditable(false);
			memberIdTextField.setBackground(dealTextField.getSelectedTextColor());
			memberIdTextField.setEditable(true);
			memberPasswordField.setBackground(dealTextField.getSelectedTextColor());
			memberPasswordField.setEditable(true);
			memberAccountTextField.setBackground(dealTextField.getSelectedTextColor());
			memberAccountTextField.setEditable(false);
			changeTextField.setBackground(Color.LIGHT_GRAY);
			changeTextField.setText("");
			if (member != null) {
				if (memberPrice > member.getMaccount()) {
					JOptionPane.showMessageDialog(null, "会员余额不足，请选择其他方式支付");
				}
			}

		}
	}

	/**
	 * 时间选择Jpanel
	 * 
	 * @author Goddard
	 *
	 */
	public class DateField extends JPanel {
		private JTextField tfDate;
		private JButton button;
		private JDialog dlg;
		private JPanel paCalendar;
		private boolean show;
		private JComboBox cboYear;
		private JComboBox cboMonth;
		private JLabel[] lbls = new JLabel[7];
		private JToggleButton[] toggles = new JToggleButton[42];
		private Border border;
		private Color bgColor;
		private int width = 150, height = 25;
		private int year;
		private int month;
		private int date;
		private int dayOfWeek;
		private String[] week = { "日", "一", "二", "三", "四", "五", "六" };

		public DateField() {
			year = Calendar.getInstance().get(Calendar.YEAR);
			month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			date = Calendar.getInstance().get(Calendar.DATE);
			dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			initUI();
			initDateDialog();
			updateField();
		}

		private void initUI() {
			tfDate = new JTextField();
			tfDate.setEditable(false);
			tfDate.setBackground(Color.WHITE);
			border = tfDate.getBorder();
			tfDate.setBorder(null);

			button = new JButton();
			button.setPreferredSize(new Dimension(height, height));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (show == false) {
						dlg.setLocation(DateField.this.getLocationOnScreen().x,
								DateField.this.getLocationOnScreen().y + DateField.this.height);
						dlg.setAlwaysOnTop(true);
						dlg.setVisible(true);
					} else {
						dlg.dispose();
					}
					updateField();
					show = !show;
				}
			});

			this.bgColor = this.getBackground();

			this.setOpaque(false);
			this.setLayout(new BorderLayout(0, 0));
			this.setBorder(border);
			this.add(tfDate, BorderLayout.CENTER);
			this.add(button, BorderLayout.EAST);
		}

		private void initDateDialog() {
			dlg = new JDialog();
			dlg.setUndecorated(true);
			paCalendar = new JPanel(new BorderLayout());
			paCalendar.setBorder(this.border);
			paCalendar.setBackground(this.bgColor);

			int borderWidth = 3;
			// 初始化两个下拉组件用于年和月的选择
			JPanel paHeader = new JPanel(new GridLayout(1, 2, borderWidth, borderWidth));
			paHeader.setOpaque(false);
			paHeader.setPreferredSize(new Dimension(this.width, this.height + borderWidth));
			paHeader.setBorder(BorderFactory.createEmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));
			paHeader.add(cboYear = new JComboBox());
			paHeader.add(cboMonth = new JComboBox());
			initYearModel();
			initMonthModel();
			paCalendar.add(paHeader, BorderLayout.NORTH);

			// 初始化日期组件
			JPanel paDay = new JPanel();
			paDay.setOpaque(false);
			paDay.setPreferredSize(new Dimension(this.height * 7, this.height * 7));
			initDate(paDay);
			paCalendar.add(paDay, BorderLayout.CENTER);

			dlg.setContentPane(paCalendar);
			dlg.pack();
			dlg.addWindowFocusListener(new WindowAdapter() {
				public void windowLostFocus(WindowEvent e) {
					dlg.dispose();
				}
			});
		}

		private void initYearModel() {
			for (int y = 1970; y < 2033; y++)
				cboYear.addItem(y);
			cboYear.setSelectedItem(year);
			cboYear.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					year = Integer.parseInt(cboYear.getSelectedItem().toString());
					updateComponent();
				}

			});
		}

		private void initMonthModel() {
			for (int m = 1; m <= 12; m++)
				cboMonth.addItem(m);
			cboMonth.setSelectedItem(month);
			cboMonth.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					month = Integer.parseInt(cboMonth.getSelectedItem().toString());
					updateComponent();
				}
			});
		}

		private void initDate(JPanel pa) {
			pa.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			// 显示日历的标签栏
			for (int i = 0; i < week.length; i++) {
				lbls[i] = new JLabel(week[i]);
				lbls[i].setHorizontalAlignment(SwingConstants.CENTER);
				lbls[i].setOpaque(true);
				lbls[i].setBackground(Color.WHITE);
				lbls[i].setPreferredSize(new Dimension(this.height, this.height));
				pa.add(lbls[i]);
			}
			// 加载日历按钮
			ButtonGroup group = new ButtonGroup();
			for (int i = 0; i < 42; i++) {
				toggles[i] = new JToggleButton();
				toggles[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				toggles[i].setPreferredSize(new Dimension(this.height, this.height));
				toggles[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						date = Integer.parseInt(((JToggleButton) e.getSource()).getText().toString());
						dlg.dispose();
						updateField();
						setSalesId();
					}
				});
				group.add(toggles[i]);
				pa.add(toggles[i]);
			}
			updateComponent();
		}

		private void updateComponent() {
			if (cboYear == null || cboMonth == null)
				return;
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, 1);
			// 根据当月的第一天是星期几来判断日历按钮的数字该从第几个按钮开始显示
			int off = cal.get(Calendar.DAY_OF_WEEK) - 1;
			// 计算当月总共有几天
			int end = 30;
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
				end = 31;
			if (month == 2) {
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
					end = 29;
				else
					end = 28;
			}
			for (int i = 0; i < 42; i++) {
				if (i >= off && i <= end + off - 1) {
					int day = i - off + 1;
					toggles[i].setText(day + "");
					toggles[i].setEnabled(true);
				} else {
					toggles[i].setText("");
					toggles[i].setEnabled(false);
				}
			}
			// 使当天的按钮呈现被按下的效果
			int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + off - 1;
			toggles[day].setSelected(true);
		}

		// 更新文本框内的文字
		private void updateField() {
			StringBuilder builder = new StringBuilder();
			builder.append(this.year + "年");
			builder.append(this.month + "月");
			builder.append(this.date + "日");
			Calendar cal = Calendar.getInstance();
			cal.set(this.year, this.month - 1, this.date);
			builder.append(" 星期" + week[cal.get(Calendar.DAY_OF_WEEK) - 1]);
			tfDate.setText(builder.toString());
		}

		public Dimension getPreferredSize() {
			return new Dimension(this.width, this.height);
		}

		public void setPreferredSize(Dimension preferredSize) {
			super.setPreferredSize(preferredSize);
			this.width = (int) preferredSize.getWidth();
			this.height = (int) preferredSize.getHeight();
		}

		public void setBackground(Color bg) {
			super.setBackground(bg);
			this.bgColor = bg;
		}

		public void setBorder(Border border) {
			super.setBorder(border);
			if (paCalendar != null)
				paCalendar.setBorder(border);
		}

		public void setButtonUI(String clzUIName) {
			try {
				button.setUI((ButtonUI) Class.forName(clzUIName).newInstance());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		public void setDateButtonUI(String clzUIName) {
			try {
				for (int i = 0; i < 42; i++)
					toggles[i].setUI((ButtonUI) Class.forName(clzUIName).newInstance());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void setComboBoxUI(String clzUIName) {
			try {
				cboYear.setUI((ComboBoxUI) Class.forName(clzUIName).newInstance());
				cboMonth.setUI((ComboBoxUI) Class.forName(clzUIName).newInstance());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		public void setLableColor(Color fg, Color bg) {
			for (int i = 0; i < lbls.length; i++) {
				lbls[i].setForeground(fg);
				lbls[i].setBackground(bg);
			}
		}

		public void setIcon(Image icon) {
			button.setIcon(new ImageIcon(icon));
		}

		public int getYear() {
			return this.year;
		}

		public int getMonth() {
			return this.month - 1;
		}

		public int getDateOfWeek() {
			return this.date;
		}

		public Date getDate() {
			Calendar cal = Calendar.getInstance();
			cal.set(this.year, this.month - 1, this.date);
			return cal.getTime();
		}
	}

}
