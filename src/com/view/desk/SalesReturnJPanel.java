package com.view.desk;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.entity.Employee;
import com.entity.Member;
import com.entity.Sales;
import com.entity.SalesInfo;
import com.entity.User;
import com.service.EmployeeService;
import com.service.GoodsService;
import com.service.MemberService;
import com.service.SalesInfoService;
import com.service.SalesService;
import com.service.UserService;
import com.service.imp.EmployeeServiceImp;
import com.service.imp.GoodsServiceImp;
import com.service.imp.MemberServiceImp;
import com.service.imp.SalesInfoServiceImpl;
import com.service.imp.SalesServiceImp;
import com.service.imp.UserServiceImp;
import com.util.KeyTypeUtil;
import com.util.OutExcel;
import com.util.StringUtil;
import com.view.DateField;

/**
 * 销售退货表格 设定只能先有销售出货单才能退后，同时，一张退货单的内容必须都是一张销售出货单中的salesInfo销售信息
 * 
 * @author wuhong
 *
 */
public class SalesReturnJPanel extends JPanel {
	private JTextField srMoneyTextField;
	private JTextField srSalesTextField;
	private JTextField srStockTextField;
	private JTextField srSumTextField;
	private JTextField memberNametextField;
	private JLabel memberGradeLabel;

	private JTextArea srRemarkTextArea;

	private GoodsReturnJtable srTable;
	private User currentUser;

	private JButton srFindSidButton;
	private JButton firstPageButton;
	private JButton prePageButton;
	private JButton nextPageButton;
	private JButton lastPageButton;
	private JButton deleteButton;
	private JButton confirmButton;
	private JButton reImburseButton;

	private JComboBox srUserComboBox;
	private DateField srDateComboBox;
	private DefaultComboBoxModel srUserdcbm;

	private JScrollPane srUserScrollPane;
	private JScrollPane srScrollPaneTable;

	private SalesService salesService = new SalesServiceImp();
	private SalesInfoService salesInfoService = new SalesInfoServiceImpl();
	private EmployeeService employeeService = new EmployeeServiceImp();
	private MemberService memberService = new MemberServiceImp();
	private UserService userService = new UserServiceImp();
	private GoodsService goodsService = new GoodsServiceImp();

	private long salesId;
	private int maxPage = 1;
	private int curPage = 1;
	private JLabel notePageLabel;

	private List<SalesInfo> salesInfos = new ArrayList<SalesInfo>();
	private JButton pressButton;

	/**
	 * Create the panel.
	 */
	public SalesReturnJPanel(User currentUser) {
		this.currentUser = currentUser;
		salesReturn();
		addListen();
		maxPage = srTable.getMaxPage();
		setPage();
	}

	/**
	 * 加载组件
	 */
	private void salesReturn() {
		JLabel srSalesLabel = new JLabel("单号");
		srSalesLabel.setBounds(10, 27, 30, 18);

		srSalesTextField = new JTextField();
		srSalesTextField.setBounds(94, 24, 140, 24);
		srSalesTextField.setEditable(false);
		srSalesTextField.setColumns(10);

		JLabel srLabel = new JLabel("出库单号");
		srLabel.setBounds(10, 85, 60, 18);

		srStockTextField = new JTextField();
		srStockTextField.setBounds(94, 82, 140, 24);
		srStockTextField.setColumns(10);

		srFindSidButton = new JButton("查找单号");
		srFindSidButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		srFindSidButton.setBounds(303, 81, 108, 27);

		JLabel srDateLabel = new JLabel("日期");
		srDateLabel.setBounds(247, 27, 34, 20);
		srDateLabel.setFont(new Font("宋体", Font.PLAIN, 17));

		JLabel srUserLabel = new JLabel("制单人");
		srUserLabel.setBounds(517, 85, 45, 18);

		srDateComboBox = new DateField();
		srDateComboBox.setBounds(303, 27, 166, 25);
		srDateComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		srUserdcbm = new DefaultComboBoxModel();
		srUserComboBox = new JComboBox(srUserdcbm);
		srUserComboBox.setBounds(575, 82, 166, 24);
		setUser();

		JLabel srRemarkLabel = new JLabel("备注");
		srRemarkLabel.setBounds(911, 27, 30, 18);

		srRemarkTextArea = new JTextArea();
		srRemarkTextArea.setBounds(955, 25, 316, 86);
		srRemarkTextArea.setBorder(BorderFactory.createEtchedBorder());

		srScrollPaneTable = new JScrollPane();
		srScrollPaneTable.setBounds(44, 134, 1253, 231);

		reImburseButton = new JButton("退款");
		reImburseButton.setBounds(100, 455, 155, 59);
		reImburseButton.setFont(new Font("宋体", Font.PLAIN, 27));

		JLabel srMoneyLabel = new JLabel("退款金额");
		srMoneyLabel.setBounds(633, 476, 108, 32);
		srMoneyLabel.setFont(new Font("宋体", Font.PLAIN, 27));

		srMoneyTextField = new JTextField();
		srMoneyTextField.setBounds(772, 473, 131, 33);
		srMoneyTextField.setEditable(false);
		srMoneyTextField.setColumns(10);

		JLabel srSumLabel = new JLabel("总数量");
		srSumLabel.setBounds(941, 476, 81, 32);
		srSumLabel.setFont(new Font("宋体", Font.PLAIN, 27));

		srSumTextField = new JTextField();
		srSumTextField.setBounds(1048, 474, 100, 32);
		srSumTextField.setEditable(false);
		srSumTextField.setColumns(10);

		srTable = new GoodsReturnJtable(new ArrayList<SalesInfo>());
		srScrollPaneTable.setViewportView(srTable);

		JLabel memberlabel = new JLabel("会员：");
		memberlabel.setBounds(517, 30, 45, 18);

		memberNametextField = new JTextField();
		memberNametextField.setBounds(575, 25, 113, 27);
		memberNametextField.setEditable(false);
		memberNametextField.setColumns(10);

		memberGradeLabel = new JLabel("会员等级");
		memberGradeLabel.setBounds(702, 26, 60, 21);

		firstPageButton = new JButton("首页");
		firstPageButton.setBounds(64, 402, 93, 23);

		prePageButton = new JButton("上一页");
		prePageButton.setBounds(185, 402, 93, 23);

		nextPageButton = new JButton("下一页");
		nextPageButton.setBounds(303, 402, 93, 23);

		lastPageButton = new JButton("尾页");
		lastPageButton.setBounds(427, 402, 93, 23);

		deleteButton = new JButton("删除");
		deleteButton.setBounds(1178, 402, 93, 23);

		confirmButton = new JButton("确认");
		confirmButton.setBounds(1055, 402, 93, 23);

		notePageLabel = new JLabel();
		notePageLabel.setBounds(1189, 374, 108, 15);

		pressButton = new JButton("导出");
		pressButton.setFont(new Font("宋体", Font.PLAIN, 27));
		pressButton.setBounds(365, 455, 155, 59);

		setLayout(null);
		add(srSalesLabel);
		add(srSalesTextField);
		add(srLabel);
		add(srStockTextField);
		add(srFindSidButton);
		add(srDateLabel);
		add(srUserLabel);
		add(srUserComboBox);
		add(srDateComboBox);
		add(srRemarkLabel);
		add(srRemarkTextArea);
		add(srScrollPaneTable);
		add(reImburseButton);
		add(srMoneyLabel);
		add(srMoneyTextField);
		add(srSumLabel);
		add(srSumTextField);
		add(memberlabel);
		add(memberNametextField);
		add(memberGradeLabel);
		add(firstPageButton);
		add(prePageButton);
		add(nextPageButton);
		add(lastPageButton);
		add(deleteButton);
		add(confirmButton);
		add(notePageLabel);
		add(pressButton);
	}

	/**
	 * 加载监听器
	 */
	public void addListen() {
		srStockTextField.addKeyListener(new KeyAdapter() {
			/**
			 * 限定销售出货单号文本框只能输入数字
			 */
			public void keyTyped(KeyEvent arg0) {
				KeyTypeUtil.numType(arg0);
			}

			/**
			 * 根据文本框输入字段，按下Enter直接查询
			 */
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					showSales();
				}
			}
		});

		srFindSidButton.addMouseListener(new MouseAdapter() {
			/**
			 * 按下查询按钮，刷新表格
			 */
			public void mouseClicked(MouseEvent arg0) {
				showSales();
			}
		});

		srTable.addMouseListener(new MouseAdapter() {
			/**
			 * 单击鼠标选择行后，动态加载退货单号，会员信息，并更新销售单号输入框 双击鼠标选择行后，自动刷新查询内容
			 */
			public void mouseClicked(MouseEvent e) {
				setsalesid();
				setMember();
				if (e.getClickCount() == 2) {
					showSales();

					setPage();
				}
			}
		});

		firstPageButton.addMouseListener(new MouseAdapter() {
			/**
			 * 跳转表格首页
			 */
			public void mouseClicked(MouseEvent arg0) {
				long fuzzyid = 0;
				if (!StringUtil.isEmpty(srStockTextField.getText())) {
					fuzzyid = Long.parseLong(srStockTextField.getText());
				}
				srTable.showagain(1, fuzzyid);
				curPage = srTable.getCurpage();
				setPage();
			}
		});

		prePageButton.addMouseListener(new MouseAdapter() {
			/**
			 * 表格的上一页
			 */
			public void mouseClicked(MouseEvent arg0) {
				prePage();
				setPage();
			}
		});

		nextPageButton.addMouseListener(new MouseAdapter() {
			/**
			 * 表格的下一页
			 */
			public void mouseClicked(MouseEvent arg0) {
				nextPage();
				setPage();
			}
		});

		lastPageButton.addMouseListener(new MouseAdapter() {
			/**
			 * 跳转尾页
			 */
			public void mouseClicked(MouseEvent arg0) {
				long fuzzyid = 0;
				if (!StringUtil.isEmpty(srStockTextField.getText())) {
					fuzzyid = Long.parseLong(srStockTextField.getText());
				}
				srTable.showagain(maxPage, fuzzyid);
				curPage = srTable.getCurpage();
				setPage();
			}
		});

		confirmButton.addMouseListener(new MouseAdapter() {
			/**
			 * 将数据储存起来，留备最后一次性加入数据库
			 */
			public void mouseClicked(MouseEvent e) {
				addSalesInfo();
			}
		});

		deleteButton.addMouseListener(new MouseAdapter() {
			/**
			 * 点击删除按钮，删除选中的之前添加的退货信息
			 */
			public void mouseClicked(MouseEvent e) {
				deleteSalesInfo();
			}
		});

		reImburseButton.addMouseListener(new MouseAdapter() {
			/**
			 * 点击结款按钮，将数据添加到数据库
			 * 
			 * @param arg0
			 */
			public void mouseClicked(MouseEvent arg0) {
				addSales();
			}
		});

		pressButton.addActionListener(new ActionListener() {
			/**
			 * 打印退货单
			 */
			public void actionPerformed(ActionEvent arg0) {
				pressSalesReturn();
			}
		});
	}

	/**
	 * 导出退货信息
	 */
	protected void pressSalesReturn() {
		if (salesInfos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "退货单号内无退货信息！请重新操作！");
			return;
		}
		String[] PURCHASE_COLUMN_NAMES = { "退货单号", "商品名称", "数量", "单价", "总价" };
		Object[][] obj = new Object[salesInfos.size() + 3][5];
		obj[0][0] = "出库单号";
		obj[0][1] = "商品名称";
		obj[0][2] = "数量";
		obj[0][3] = "单价";
		obj[0][4] = "总价";
		int i = 1;
		for (SalesInfo si : salesInfos) {
			obj[i][0] = si.getSalesid();
			obj[i][1] = goodsService.queryGoodsById(si.getGid()).getGname();
			obj[i][2] = si.getSnum();
			obj[i][3] = goodsService.queryGoodsById(si.getGid()).getGprice();
			obj[i][4] = goodsService.queryGoodsById(si.getGid()).getGprice() * si.getSnum();
			i++;
		}
		obj[i][0] = null;
		obj[i][1] = null;
		obj[i][2] = null;
		obj[i][3] = "    合   计：";
		obj[i][4] = srMoneyTextField.getText();
		obj[i + 1][0] = srDateComboBox.getDate().toLocaleString();
		obj[i + 1][1] = null;
		obj[i + 1][2] = null;
		obj[i + 1][3] = "   制单人：";
		String maker = userService.queryEmployee(currentUser).getEname();
		obj[i + 1][4] = maker;
		JTable jtable = new JTable();
		jtable.setTableHeader(new JTableHeader());
		jtable.setModel(new DefaultTableModel(obj, new String[] { "", "", "退货单", "", "", "" }));
		new OutExcel(jtable);
	}

	/**
	 * 删除退货信息
	 */
	protected void deleteSalesInfo() {
		if (srTable.getSelectedColumn() < 0 && srTable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "请先选择要删除的退货销售信息！");
			return;
		}
		long salesid = (long) srTable.getModel().getValueAt(srTable.getSelectedRow(), 0);
		int gid = (int) srTable.getModel().getValueAt(srTable.getSelectedRow(), 1);
		boolean flag = false;
		for (SalesInfo salesInfo : salesInfos) {
			if ((0-salesid) == salesInfo.getSalesid() && gid == salesInfo.getGid()) {
				salesInfos.remove(salesInfo);
				flag = true;
				break;
			}
		}
		if (flag) {
			JOptionPane.showMessageDialog(null, "删除成功");
			return;
		} else {
			JOptionPane.showMessageDialog(null, "删除失败");
			return;
		}
	}

	/**
	 * 加入退货单条目到集合salesInfos中去
	 */
	protected void addSalesInfo() {
		if (srTable.getSelectedColumn() < 0 && srTable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "请先选择要退货的销售信息！");
			return;
		}
		int snum = Integer.parseInt(srTable.getModel().getValueAt(srTable.getSelectedRow(), 9).toString());
		if (snum == 0) {
			JOptionPane.showMessageDialog(null, "请先设定退货的数量！");
			return;
		}
		long salesid = (long) srTable.getModel().getValueAt(srTable.getSelectedRow(), 0);
		int gid = (int) srTable.getModel().getValueAt(srTable.getSelectedRow(), 1);
		SalesInfo salesInfo = salesInfoService.queryBySalesidAndGid(salesid, gid);// 查询销售信息
		int salesOutNum = salesInfo.getSnum();// 售出的数量
		salesInfo.setSalesid(0 - salesid);
		salesInfo.setSnum(0 - snum);
		boolean flag = false;
		SalesInfo salesRe = salesInfoService.queryBySalesidAndGid(0 - salesid, gid);
		StringBuffer sb = new StringBuffer("");
		if (salesRe != null) {
			sb.append(salesid + "单号总共售出" + salesOutNum + ",之前退货" + (0 - salesRe.getSnum()) + ",本次退货" + snum);
			salesOutNum = (0 - salesRe.getSnum()) + salesOutNum;
		}
		if (salesInfo.getSnum() > salesOutNum) {
			JOptionPane.showMessageDialog(null, sb + "退货数量不能比销售的数量多！");
			return;
		}
		for (SalesInfo si : salesInfos) {
			if (si.getSalesid() != (0 - salesid)) {
				JOptionPane.showMessageDialog(null, "所选单号与之前单号不同，不能操作，请先处理上一张销售单！");
				return;
			}
			if (si.getSalesid() == salesInfo.getSalesid() && si.getGid() == salesInfo.getGid()) {
				salesInfos.remove(si);
				salesInfos.add(salesInfo);
				flag = true;
				break;
			}
		}
		if (flag) {
			JOptionPane.showMessageDialog(null, "修改成功");
		} else {
			salesInfos.add(salesInfo);
			JOptionPane.showMessageDialog(null, "添加成功");
		}
		double totalMoney = 0;
		int count = 0;
		for (SalesInfo si : salesInfos) {
			totalMoney = totalMoney + si.getSnum() * goodsService.queryGoodsById(si.getGid()).getGprice()
					* goodsService.queryGoodsById(si.getGid()).getGdiscount();
			count = count + si.getSnum();
		}
		srMoneyTextField.setText(Math.copySign(totalMoney, 0.00) + "");
		srSumTextField.setText(count + "");
	}

	/**
	 * 添加出货单到数据库
	 */
	public void addSales() {
		String salesReturnId = srSalesTextField.getText();// 退货信息单号
		if (salesReturnId.isEmpty() || "".equals(salesReturnId)) {
			JOptionPane.showMessageDialog(null, "请先选择要退货的销售单号！");
			return;
		}
		long salesId = 0 - Long.parseLong(salesReturnId);// 销售信息单号
		Sales sa = salesService.querySalesID(salesId);
		Date sdate = new Date(srDateComboBox.getDate().getTime());
		if (sa.getSdate().after(sdate)) {
			JOptionPane.showMessageDialog(null, "退货的日期必须在售出日期之后！");
			return;
		}
		String ename = (String) srUserdcbm.getSelectedItem();
		if (!ename.equals(userService.queryEmployee(currentUser).getEname())) {
			List<Employee> employeeList = employeeService.queryAll();
			for (Employee employee : employeeList) {
				if (ename.equals(employee.getEname())) {
					currentUser = userService.queryByEid(employee.getEid());// 重新确定当前制表人
					String password = JOptionPane.showInputDialog(null, "密码：", "请输入" + ename + "用户密码",
							JOptionPane.QUESTION_MESSAGE);
					if (!currentUser.getPassword().equals(password)) {
						JOptionPane.showMessageDialog(null, "密码错误，请重新操作");
						return;
					}
				}
			}
		}
		int mid = sa.getMid();
		int eid = currentUser.getEid();
		double discount = Double.parseDouble(memberService.queryDiscount(memberGradeLabel.getText()));// 读取会员折扣
		double mgathering = Math.copySign(Double.parseDouble(srMoneyTextField.getText()) * discount, 0.00);
		String remark = srRemarkTextArea.getText();
		Sales salesReturn = new Sales(Long.parseLong(salesReturnId), eid, mid, sdate, mgathering, remark);
		Sales salesIn = salesService.querySalesID(Long.parseLong(salesReturnId));
		if (salesService.addSales(salesReturn, salesInfos)) {
			JOptionPane.showMessageDialog(null, "退款成功！");
			showagain();
		} else {
			JOptionPane.showMessageDialog(null, "退款失败！");
		}
	}

	/**
	 * 刷新页面
	 */
	private void showagain() {
		removeAll();
		salesReturn();
		addListen();
		curPage = 1;
		maxPage = srTable.getMaxPage();
		setPage();
	}

	/**
	 * 随着操作动态加载页码
	 */
	public void setPage() {
		notePageLabel.setText("当前页：" + curPage + "/" + maxPage);
	}

	/**
	 * 跳转表格上一页
	 */
	protected void prePage() {
		curPage = srTable.getCurpage() - 1;
		if (curPage <= 1) {
			curPage = 1;
		}
		long fuzzyid = 0;
		if (!StringUtil.isEmpty(srStockTextField.getText())) {
			fuzzyid = Long.parseLong(srStockTextField.getText());
		}
		srTable.showagain(curPage, fuzzyid);
		maxPage = srTable.getMaxPage();
		setPage();
	}

	/**
	 * 跳转表格下一页
	 */
	protected void nextPage() {
		curPage = srTable.getCurpage() + 1;
		if (curPage >= maxPage) {
			curPage = maxPage;
		}
		long fuzzyid = 0;
		if (!StringUtil.isEmpty(srStockTextField.getText())) {
			fuzzyid = Long.parseLong(srStockTextField.getText());
		}
		srTable.showagain(curPage, fuzzyid);
		maxPage = srTable.getMaxPage();
		setPage();
	}

	/**
	 * 设置制单人控件内容
	 */
	public void setUser() {
		List<Employee> employeelist = employeeService.queryAll();
		for (Employee employee : employeelist) {
			srUserdcbm.addElement(employee.getEname());
		}
		if (currentUser != null) {
			srUserComboBox.setSelectedItem(userService.queryEmployee(currentUser).getEname());
		}
	}

	/**
	 * 根据选中出货单号的不同，动态加载会员信息
	 */
	public void setMember() {
		if (srTable.getSelectedColumn() >= 0 || srTable.getSelectedRow() >= 0) {
			long salesid = (Long) srTable.getModel().getValueAt(srTable.getSelectedRow(), 0);
			Member currentMember = memberService
					.queryByid(String.valueOf(salesService.queryByFuzzyId(salesid).get(0).getMid())).get(0);
			memberNametextField.setText(currentMember.getMname());
			memberGradeLabel.setText(currentMember.getMgrade());
		}
	}

	/**
	 * 设置salesid退货单，由销售单号决定,并将销售单号更新至销售单号输入框，方便查询
	 */
	public void setsalesid() {
		if (srTable.getSelectedColumn() >= 0 || srTable.getSelectedRow() >= 0) {
			salesId = 0 - (Long) srTable.getModel().getValueAt(srTable.getSelectedRow(), 0);
			srSalesTextField.setText(salesId + "");
			srStockTextField.setText((0 - salesId) + "");
		}
	}

	/**
	 * 根据搜索框内的文本内容刷新页面
	 */
	public void showSales() {
		try {
			String sidStr = srStockTextField.getText();
			if (sidStr.isEmpty() || "".equals(sidStr)) {
				srTable.showagain(1, 0);// 默认展示第一页
				curPage = 1;
				maxPage = srTable.getMaxPage();
				setPage();
			} else {
				long sid = Long.parseLong(sidStr);
				srTable.showagain(sid);
				curPage = srTable.getCurpage();
				maxPage = srTable.getMaxPage();
				setPage();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "系统繁忙，请稍后再试！");
		}
	}
}
