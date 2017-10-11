package com.view.purchase;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.entity.Distributor;
import com.entity.Employee;
import com.entity.Goods;
import com.entity.Purchase;
import com.entity.PurchaseInfo;
import com.entity.User;
import com.service.DistributorService;
import com.service.EmployeeService;
import com.service.GoodsService;
import com.service.PurchaseInfoService;
import com.service.PurchaseService;
import com.service.UserService;
import com.service.imp.DistributorServiceImpl;
import com.service.imp.EmployeeServiceImp;
import com.service.imp.GoodsServiceImp;
import com.service.imp.PurchaseInfoServiceImpl;
import com.service.imp.PurchaseServiceImpl;
import com.service.imp.UserServiceImp;
import com.util.OutExcel;
import com.util.StringUtil;
import com.view.DateField;
import com.view.LogInFrame;

/**
 * 采购入库Jpanel
 * 
 * @author wuhong
 *
 */
@SuppressWarnings("all")
public class PurchaseInJpanel extends JPanel {
	private JTextField piPidTextField;
	private JTextField agChooseGoodsTextField;
	private JTextField piSupplierTextField;
	private JTextField piDealTextField;
	private JTextArea piRemarkTextArea;
	private DateField piDateComboBox;
	
	private JButton outPressbutton;
	private JButton updateDateButton;
	private JButton clearSelectionButton;
	private JButton finshbutton;
	private JButton piaddPurchaseButton;
	private JButton piCheckButton;
	private JButton piDeleteButton;
	private JButton piFindGoodsButton;

	private PusrchaseInJtable pusrchaseInJtable;
	private DefaultComboBoxModel supplierdcbm;
	private DefaultComboBoxModel piUserdcbm;
	private JComboBox piSupplierComboBox;

	private List<Goods> goodsList = new ArrayList<Goods>();
	private List<Employee> employeeList = new ArrayList<Employee>();
	private List<PurchaseInfo> purchaseInfos = new ArrayList<PurchaseInfo>();

	private PurchaseInfoService purchaseInfoService = new PurchaseInfoServiceImpl();
	private DistributorService distributorService = new DistributorServiceImpl();
	private GoodsService goodsService = new GoodsServiceImp();
	private EmployeeService employeeService = new EmployeeServiceImp();
	private UserService userService = new UserServiceImp();
	private PurchaseService purchaseService = new PurchaseServiceImpl();

	private java.util.Date pdate = new java.util.Date();
	private int count = 1;// 当天第几张入库单
	private int pinfonum = 0;// 每张单几个PuchaseInfo
	private int pid;
	private JTable piPurchasetable;
	private User currentUser;

	/**
	 * 创建Jpanel
	 */
	public PurchaseInJpanel(User currentUser) {
		this.currentUser = currentUser;
		setCount();
		pid = (pdate.getYear() + 1900) * 1000000 + (pdate.getMonth() + 1) * 10000 + pdate.getDate() * 100 + count;
		purchaseIn();
		setmaker();
		addListen();
	}

	/**
	 * 加载入库页面
	 */
	private void purchaseIn() {
		JLabel piPidLabel = new JLabel("\u5355\u53F7");
		piPidLabel.setBounds(14, 17, 40, 18);

		piPidTextField = new JTextField();
		piPidTextField.setEditable(false);
		piPidTextField.setBounds(77, 14, 117, 30);
		piPidTextField.setColumns(10);
		piPidTextField.setText("" + pid);

		JLabel agChooseGoodsLabel = new JLabel("选择商品");
		agChooseGoodsLabel.setBounds(14, 114, 60, 18);

		agChooseGoodsTextField = new JTextField();
		agChooseGoodsTextField.setBounds(77, 108, 117, 30);
		agChooseGoodsTextField.setColumns(10);

		JLabel piSupplierLabel = new JLabel("供应商");
		piSupplierLabel.setBounds(208, 16, 52, 18);

		piSupplierTextField = new JTextField();
		piSupplierTextField.setBounds(274, 13, 130, 30);
		piSupplierTextField.setColumns(10);

		piFindGoodsButton = new JButton("查找商品");
		piFindGoodsButton.setBounds(271, 108, 129, 30);

		supplierdcbm = new DefaultComboBoxModel();
		setSupplier();

		piSupplierComboBox = new JComboBox(supplierdcbm);
		piSupplierComboBox.setBounds(414, 12, 129,30);

		piDeleteButton = new JButton("删除");
		piDeleteButton.setBounds(414, 57, 129, 30);

		JLabel piDateLabel = new JLabel("日期");
		piDateLabel.setBounds(557, 13, 32, 24);
		piDateLabel.setFont(new Font("宋体", Font.PLAIN, 16));

		piDateComboBox = new DateField();
		piDateComboBox.setBounds(599, 10, 167, 25);
		piDateComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		JLabel piUserLabel = new JLabel("制单人");
		piUserLabel.setBounds(14, 64, 53, 18);

		piUserdcbm = new DefaultComboBoxModel();

		JComboBox piUserComboBox = new JComboBox(piUserdcbm);
		piUserComboBox.setEditable(false);
		piUserComboBox.setBounds(77, 57, 117, 27);
		/*
		 * 第一次进入系统，默认制表人为当前登录用户
		 */
		if (currentUser != null) {
			piUserComboBox.setSelectedItem(userService.queryEmployee(currentUser).getEname());
		}

		JLabel piRemarkLabel = new JLabel("备注");
		piRemarkLabel.setBounds(557, 63, 30, 18);

		piRemarkTextArea = new JTextArea();
		piRemarkTextArea.setBounds(599, 64, 281, 76);
		piRemarkTextArea.setBorder(BorderFactory.createEtchedBorder());

		JScrollPane piScrollPane = new JScrollPane();
		piScrollPane.setBounds(30, 147, 1253, 285);

		piCheckButton = new JButton("添加采购信息");
		piCheckButton.setBounds(193, 450, 155, 64);
		piCheckButton.setFont(new Font("宋体", Font.PLAIN, 18));

		JLabel piDealLabel = new JLabel("应付");
		piDealLabel.setBounds(991, 477, 64, 37);
		piDealLabel.setFont(new Font("宋体", Font.PLAIN, 32));

		piDealTextField = new JTextField();
		piDealTextField.setBounds(1105, 467, 155, 47);
		piDealTextField.setEditable(false);
		piDealTextField.setColumns(10);

		pusrchaseInJtable = new PusrchaseInJtable(new ArrayList<Goods>());

		piaddPurchaseButton = new JButton("新增采购单号");
		piaddPurchaseButton.setBounds(414, 108, 129, 30);

		piPurchasetable = new JTable();
		piPurchasetable.setColumnSelectionAllowed(false);
		piPurchasetable.setCellSelectionEnabled(false);
		piPurchasetable.setRowSelectionAllowed(true);
		piPurchasetable.setBounds(623, 61, 305, 72);
		showPurchase(new ArrayList<PurchaseInfo>());

		JScrollPane purchaseScrollPane = new JScrollPane();
		purchaseScrollPane.setBounds(921, 0, 354, 132);
		purchaseScrollPane.setViewportView(piPurchasetable);

		JLabel piDayPuchaseLabel = new JLabel("当次采购单");
		piDayPuchaseLabel.setBounds(827, 46, 80, 17);

		finshbutton = new JButton("审核通过");
		finshbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		finshbutton.setBounds(623, 450, 155, 64);
	
		clearSelectionButton = new JButton("清除行选中");
		clearSelectionButton.setBounds(271, 57, 133, 30);

		updateDateButton = new JButton("重新选择时间");
		updateDateButton.setBounds(780, 10, 129, 23);

		outPressbutton = new JButton("导出");
		outPressbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		outPressbutton.setBounds(399, 450, 155, 64);

		piScrollPane.setViewportView(pusrchaseInJtable);
		setLayout(null);
		add(piPidLabel);
		add(piPidTextField);
		add(agChooseGoodsLabel);
		add(agChooseGoodsTextField);
		add(piSupplierLabel);
		add(piSupplierTextField);
		add(piFindGoodsButton);
		add(piDeleteButton);
		add(piSupplierComboBox);
		add(piUserLabel);
		add(piDateLabel);
		add(piDateComboBox);
		add(piUserComboBox);
		add(piRemarkLabel);
		add(piRemarkTextArea);
		add(piaddPurchaseButton);
		add(piScrollPane);
		add(piCheckButton);
		add(purchaseScrollPane);
		add(piDealLabel);
		add(piDealTextField);
		add(piDayPuchaseLabel);
		add(finshbutton);
		add(clearSelectionButton);
		add(updateDateButton);
		add(outPressbutton);
	}

	public void addListen(){
		agChooseGoodsTextField.addFocusListener(new FocusAdapter() {
			/**
			 * 当文本框失去焦点时自动根据文本框内容查询
			 */
			public void focusLost(FocusEvent arg0) {
				if (StringUtil.isEmpty(agChooseGoodsTextField.getText().trim())) {
					return;
				}
				showGoodsByFuzzy();
			}
		});
		
		piSupplierTextField.addFocusListener(new FocusAdapter() {
			/**
			 * 展示供应商在文本框
			 */
			public void focusLost(FocusEvent e) {
				showSupplier();
			}
		});
		
		piFindGoodsButton.addMouseListener(new MouseAdapter() {
			/**
			 * 根据文本框模糊查询
			 */
			public void mouseClicked(MouseEvent e) {
				showGoodsByFuzzy();
			}
		});
		
		piSupplierComboBox.addItemListener(new ItemListener() {
			/**
			 * 监听piSupplierComboBox，当选择条目改变时，在piSupplierTextField展示选中的Supplier
			 */
			public void itemStateChanged(ItemEvent arg0) {
				try {
					if (!"选择供应商".equals((String) supplierdcbm.getSelectedItem())) {
						piSupplierTextField.setText((String) supplierdcbm.getSelectedItem());
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "系统繁忙，请稍后再试！");
				}
			}
		});
		
		piDeleteButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (pusrchaseInJtable.getSelectedRow() >= 0) {
					/*deleteGood();
					showGoodsByFuzzy();
					showPurchase(purchaseInfos);*/
					JOptionPane.showMessageDialog(null, "请前往商品管理页面删除");
					return;
				} else if (piPurchasetable.getSelectedRow() >= 0) {
					deletePuechaseInfo();
					showPurchase(purchaseInfos);
				} else {
					JOptionPane.showMessageDialog(null, "请先选择要删除的条目！");
				}
			}
		});
		
		piCheckButton.addMouseListener(new MouseAdapter() {
			/**
			 * 往采购单号Purchase上添加详细PurchaseInfo信息 一张单号可支持多张PurchaseInfo信息
			 */
			public void mouseClicked(MouseEvent arg0) {
				addPurchaseInfo();
			}
		});
		
		pusrchaseInJtable.addMouseListener(new MouseAdapter() {
			/**
			 * 选中表格中商品后，将商品remark写入对应文本框
			 */
			public void mouseClicked(MouseEvent e) {
				setRemark();
			}
		});
		
		piaddPurchaseButton.addMouseListener(new MouseAdapter() {
			/**
			 * 通过点击piaddPurchaseButton按钮,添加Purchase对象 即采购单号进入数据库
			 */
			public void mouseClicked(MouseEvent arg0) {
				addPurchase();
			}
		});
		
		finshbutton.addMouseListener(new MouseAdapter() {
			/**
			 * 鼠标点击finshbutton按钮，本单Purchase完成入库
			 */
			public void mouseClicked(MouseEvent e) {
				updateGstock();
			}
		});
		
		clearSelectionButton.addMouseListener(new MouseAdapter() {
			/**
			 * 鼠标点击clearSelectionButton按钮，清除表格选中
			 */
			public void mouseClicked(MouseEvent arg0) {
				clearSelect();
			}
		});
		
		updateDateButton.addMouseListener(new MouseAdapter() {
			/**
			 * 点击按钮，更新时间对应的pid
			 */
			public void mouseClicked(MouseEvent arg0) {
				setpid();
			}
		});
		
		outPressbutton.addMouseListener(new MouseAdapter() {
			/**
			 * 导出入库单
			 */
			public void mouseClicked(MouseEvent arg0) {
				outPress();
			}
		});
		
	}
	
	/**
	 * 打印导出事件处理
	 */
	public void outPress() {
		if (purchaseInfos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "采购单号内无采购信息！请重新操作！");
			return;
		}
		Object[][] obj = new Object[pinfonum + 3][6];
		obj[0][0] = "采购单号";
		obj[0][1] = "商品名称";
		obj[0][2] = "数量";
		obj[0][3] = "厂家";
		obj[0][4] = "单价";
		obj[0][5] = "总价";
		int i = 1;
		for (PurchaseInfo pis : purchaseInfos) {
			obj[i][0] = pis.getPid();
			obj[i][1] = goodsService.queryGoodsById(pis.getGid()).getGname();
			obj[i][2] = pis.getPnum();
			obj[i][3] = distributorService
					.queryDistributorById(purchaseService.qureyPurchaseByPid(pis.getPid()).getDid()).getDname();
			obj[i][4] = goodsService.queryGoodsById(pis.getGid()).getGcost_price();
			obj[i][5] = pis.getPnum() * goodsService.queryGoodsById(pis.getGid()).getGcost_price();
			i++;
		}
		obj[i][0] = null;
		obj[i][1] = null;
		obj[i][2] = null;
		obj[i][3] = null;
		obj[i][4] = "    合   计：";
		obj[i][5] = piDealTextField.getText();
		obj[i + 1][0] = pdate.toLocaleString();
		obj[i + 1][1] = null;
		obj[i + 1][2] = null;
		obj[i + 1][3] = null;
		obj[i + 1][4] = "   制单人：";
		User maker = null;
		for (User u : userService.queryUser()) {
			if (u.getSid() == purchaseService.qureyPurchaseByPid((Integer) obj[1][0]).getSid()) {
				maker = u;
			}
		}
		obj[i + 1][5] = userService.queryEmployee(maker).getEname();
		JTable jtable = new JTable();
		jtable.setTableHeader(new JTableHeader());
		jtable.setModel(new DefaultTableModel(obj, new String[] { "", "", "", "采购入库单", "", "" }));
		new OutExcel(jtable);
	}

	/**
	 * 选中表格中商品后，将商品remark写入对应文本框
	 */
	protected void setRemark() {
		if (pusrchaseInJtable.getSelectedColumn() >= 0) {
			Goods good = pusrchaseInJtable.getSelectGoods();
			if (good != null) {
				piRemarkTextArea.setText(good.getGremark());
			}
		}
	}

	/**
	 * 将采购信息添加到采购单中,如果出现pid，gid相同时，则直接更新之前的内容
	 */
	public void addPurchaseInfo() {
		if(purchaseService.qureyPurchaseByPid(pid)==null){
			JOptionPane.showMessageDialog(null, "请先添加采购单号，再重新输入");
			return;
		}
		Goods good = pusrchaseInJtable.getSelectGoods();
		int pnum = 0;
		try {
			pnum = Integer
					.parseInt((String) pusrchaseInJtable.getModel().getValueAt(pusrchaseInJtable.getSelectedRow(), 5));
			if(pnum<0){
				JOptionPane.showMessageDialog(null, "数量必须大于0，请重新输入");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数量有误，请重新输入");
			return;
		}
		PurchaseInfo purchaseInfo = new PurchaseInfo(pid, good.getGid(), pnum);
		try {
			for (PurchaseInfo pi : purchaseInfos) {
				if (pi.getPid() == purchaseInfo.getPid() && pi.getGid() == purchaseInfo.getGid()) {
					purchaseInfos.remove(pi);
					purchaseInfo.setPnum(pi.getPnum() + purchaseInfo.getPnum());
					--pinfonum;
				}
			}
		}catch(Exception e) {
			
		}
		JOptionPane.showMessageDialog(null, "添加采购信息成功");
		pinfonum++;
		good.setGstock(pnum);
		goodsList.add(good);
		purchaseInfos.add(purchaseInfo);
		deal();
		piPurchasetable.removeAll();
		showPurchase(purchaseInfos);
	}

	/**
	 * 添加采购单号
	 * 
	 * @return true/false
	 */
	public void addPurchase() {
		String ename = (String) piUserdcbm.getSelectedItem();
		Employee nowemployee = userService.queryEmployee(currentUser);
		if (!nowemployee.getEname().equals(ename)) {
			for (Employee employee : employeeList) {
				if (ename.equals(employee.getEname())) {
					currentUser = userService.queryByEid(employee.getEid());
					String password = JOptionPane.showInputDialog(null, "密码：", "请输入用户密码", JOptionPane.QUESTION_MESSAGE);
					if (!currentUser.getPassword().equals(password)) {
						JOptionPane.showMessageDialog(null, "密码错误，请重新操作");
						return;
					}
				}
			}
		}
		int sid = currentUser.getSid();
		String supplier = (String) supplierdcbm.getSelectedItem();
		if ("选择供应商".equals(supplier) || "".equals(supplier)
				|| distributorService.queryDistributorByName(supplier) == null) {
			JOptionPane.showMessageDialog(null, "供应商选择错误，请重新选择");
			return;
		}
		int did = distributorService.queryDistributorByName(supplier).getDid();
		Date pdate = new Date(this.pdate.getTime());
		String remark = piRemarkTextArea.getText();
		Purchase purchase = new Purchase(pid, pdate, did, sid, remark, purchaseInfos);
		if(purchaseService.qureyPurchaseByPid(pid) == null){
			if(purchaseService.addPurchase(purchase)){
				JOptionPane.showMessageDialog(null, "添加成功");
			}
		}else{
			if(purchaseInfoService.queryByFuzzyPid(pid).isEmpty()){
				JOptionPane.showMessageDialog(null, "入库单已存在，且无具体采购信息");
				return;
			}
			JOptionPane.showMessageDialog(null, "入库单已存在，请重新选择");
			repaintAll();
			return;
		}
			

	}

	/**
	 * 记录当天已经有多少入库单
	 */
	public void setCount() {
		List<Purchase> purchaselist = purchaseService.queryAll();
		int i = 1;
		for (Purchase purchases : purchaselist) {
			if (purchases.getPdate().getYear() == pdate.getYear()) {
				if (purchases.getPdate().getMonth() == pdate.getMonth()) {
					if (purchases.getPdate().getDay() == pdate.getDay()) {
						if (purchases.getPid() > 0) {
							i++;
						}
					}
				}
			}
		}
		count = i;
	}

	/**
	 * 在表格中展示当前Purchase中将要入库的PurchaseInfo
	 */
	public void showPurchase(List<PurchaseInfo> purchaseInfolist) {
		String[] PURCHASE_COLUMN_NAMES = { "采购单号", "商品名称", "数量" };
		Object[][] obj = new Object[purchaseInfolist.size()][3];
		int i = 0;
		for (PurchaseInfo purchaseInfos : purchaseInfolist) {
			if (purchaseInfos.getPid() == pid) {
				obj[i][0] = pid;
				obj[i][1] = goodsService.queryGoodsById(purchaseInfos.getGid()).getGname();
				obj[i][2] = purchaseInfos.getPnum();
				i++;
			}
		}
		piPurchasetable.setModel(new DefaultTableModel(obj, PURCHASE_COLUMN_NAMES) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		piPurchasetable.getColumnModel().getColumn(0).setResizable(false);
		piPurchasetable.getColumnModel().getColumn(1).setResizable(false);
		piPurchasetable.getColumnModel().getColumn(2).setResizable(false);
		piPurchasetable.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * 商品删除
	 */
	public void deleteGood() {
		if (pusrchaseInJtable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行在进行删除操作");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "谨慎操作，是否删除", "注意", 2);
		if (select == 0) {
			try {
				Goods good = goodsService.queryGoodsById(pusrchaseInJtable.getSelectGoods().getGid());
				if (goodsService.deleteById(pusrchaseInJtable.getSelectGoods().getGid())) {
					JOptionPane.showMessageDialog(null, "删除成功");
					int i = 0;
					for (Goods goods : goodsList) {
						if (goods.getGname().equals(good.getGname())) {
							goodsList.remove(goods);
							i++;
						}
					}
					for (PurchaseInfo pi : purchaseInfos) {
						if(pi.getGid()==good.getGid()){
							purchaseInfos.remove(pi);
						}
					}
					pinfonum -= i;
					deal();
					List<Goods> glist = goodsService.fuzzyQuery(agChooseGoodsTextField.getText().trim(), "", ">-1");
					pusrchaseInJtable.setList(glist);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "系统繁忙，请稍后再试！");
			}
		}
	}

	/**
	 * 删除PurchaseInfo
	 */
	public void deletePuechaseInfo() {
		if (piPurchasetable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行在进行删除操作");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "谨慎操作，是否删除", "注意", 2);
		String gname = (String) piPurchasetable.getModel().getValueAt(piPurchasetable.getSelectedRow(), 1);
		Goods good = goodsService.queryGoodsByName(gname);
		try {
			if (select == 0) {
				for (PurchaseInfo pi : purchaseInfos) {
					if(pi.getGid()==good.getGid()){
						purchaseInfos.remove(pi);
					}
				}
				JOptionPane.showMessageDialog(null, "删除成功");
			}
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(null, "系统繁忙");
		}
	}

	/**
	 * 重置页面
	 */
	public void repaintAll() {
		this.removeAll();
		setCount();
		pid = (pdate.getYear() + 1900) * 1000000 + (pdate.getMonth() + 1) * 10000 + pdate.getDate() * 100 + count;
		purchaseIn();
		setpid();
		addListen();
		setmaker();
	}

	/**
	 * 设置表格取消选中
	 */
	public void clearSelect() {
		piPurchasetable.clearSelection();
		pusrchaseInJtable.clearSelection();
	}

	/**
	 * 将入库单的数据存入数据库，更新商品库存
	 */
	public void updateGstock() {
		int[] updateId = new int[goodsList.size()];
		if (goodsList.isEmpty()) {
			JOptionPane.showMessageDialog(null, "无数据更新，请先新增采购单！");
			return;
		}
		boolean flag = true;
		int i = 0;
		for (Goods goods : goodsList) {
			Goods good = goodsService.queryGoodsById(goods.getGid());
			good.setGstock(good.getGstock() + goods.getGstock());
			if (!goodsService.update(goods.getGid(), good)) {
				updateId[i] = goods.getGid();
				flag = false;
			}
			i++;
		}
		if (!flag) {
			StringBuffer noupdate = new StringBuffer("");
			for (Integer gid : updateId) {
				if (gid != 0) {
					noupdate.append(gid + "\t");
				}
			}
			JOptionPane.showMessageDialog(null, "商品编号："+noupdate + "更新失败");
		}
		StringBuffer sb=new StringBuffer("");
		for (PurchaseInfo pi : purchaseInfos) {
			if (!purchaseInfoService.addPurchaseInfo(pi)) {
				sb.append(pi.getGid()+"\t");
				flag=false;
			}
		}
		if(!flag){
			JOptionPane.showMessageDialog(null, "采购信息编号："+sb+ "更新失败");
			return;
		}
		JOptionPane.showMessageDialog(null, "入库成功,请凭打印单前往财务处结账！！！");
		purchaseInfos.remove(purchaseInfos);
		repaintAll();
	}

	/**
	 * 当存在采购单时，计算总成本价并显示在对应文本框
	 */
	public void deal() {
		if (goodsList.size() > 0) {
			double deal = 0;
			for (Goods good : goodsList) {
				deal += good.getGcost_price() * good.getGstock();
			}
			piDealTextField.setText(deal + "");
		}
	}

	/**
	 * 设置所选日期，设置所选日期的pid
	 */
	public void setpid() {
		pdate = piDateComboBox.getDate();
		setCount();
		pid = (pdate.getYear() + 1900) * 1000000 + (pdate.getMonth() + 1) * 10000 + pdate.getDate() * 100 + count;
		piPidTextField.setText(pid + "");
	}

	/**
	 * 商品模糊字段查询并显示在表中
	 */
	public void showGoodsByFuzzy() {
		List<Goods> glist = goodsService.fuzzyQuery(agChooseGoodsTextField.getText().trim(), "", ">-1");
		pusrchaseInJtable.setList(glist);
	}

	/**
	 * 根据需求展示供应商
	 */
	public void showSupplier() {
		if (StringUtil.isEmpty(piSupplierTextField.getText().trim())) {
			return;
		}
		supplierdcbm.removeAllElements();
		supplierdcbm.addElement("选择供应商");
		List<Distributor> distributorList = distributorService.queryByFuzzy(piSupplierTextField.getText());
		for (Distributor distributor : distributorList) {
			supplierdcbm.addElement(distributor.getDname());
		}
	}

	/**
	 * 往供应商选择复合框添加元素
	 */
	public void setSupplier() {
		supplierdcbm.addElement("选择供应商");
		List<Distributor> distributorList = distributorService.queryAll();
		for (Distributor distributor : distributorList) {
			supplierdcbm.addElement(distributor.getDname());
		}
	}
	
	/**
	 * 设置制表人复合框里所有admin权限的用户
	 */
	public void setmaker(){
		List<User> users = userService.queryUser();
		for (User user : users) {
			if ("admin".equals(user.getUsertype())) {
				Employee employee = employeeService.queryByEid(user.getEid());
				if (employee != null) {
					piUserdcbm.addElement(employee.getEname());
					employeeList.add(employee);
				}
			}
		}
	}
}
