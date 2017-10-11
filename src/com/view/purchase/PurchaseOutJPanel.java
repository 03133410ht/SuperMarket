package com.view.purchase;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
import com.util.KeyTypeUtil;
import com.util.OutExcel;
import com.view.DateField;
import com.view.LogInFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 采购出库Jpanel
 * 
 * @author wuhong
 *
 */
@SuppressWarnings("all")
public class PurchaseOutJPanel extends JPanel {
	private JTextField poPidTextField;
	private JTextField agChoosePidTextField;
	private JTextField poSupplierTextField;
	private JTextField poDealTextField;
	private JTextArea poRemarkTextArea;
	private PusrchaseOutJtable pusrchaseOutJtable;
	private DefaultComboBoxModel supplierdcbm;
	private DefaultComboBoxModel poUserdcbm;
	private DateField poDateComboBox;
	private JTable poPurchasetable;
	
	private JButton poFindPurchaseInfosButton;
	private JButton poDeleteButton;
	private JButton poCheckButton;
	private JButton poaddPurchaseButton;
	private JButton finshbutton;
	private JButton clearSelectionButton;
	private JButton updateChangeButton;
	private JButton outPressbutton;

	private List<Goods> goodsList = new ArrayList<Goods>();
	private List<Employee> employeeList = new ArrayList<Employee>();
	private List<PurchaseInfo> purchaseOutInfos = new ArrayList<PurchaseInfo>();
	private List<PurchaseInfo> purchaseInInfos = new ArrayList<PurchaseInfo>();

	private PurchaseInfoService purchaseInfoService = new PurchaseInfoServiceImpl();
	private DistributorService distributorService = new DistributorServiceImpl();
	private GoodsService goodsService = new GoodsServiceImp();
	private EmployeeService employeeService = new EmployeeServiceImp();
	private UserService userService = new UserServiceImp();
	private PurchaseService purchaseService = new PurchaseServiceImpl();

	private java.util.Date pdate = new java.util.Date();
	private User currentUser;
	private int count = 1;// 当天第几张出库单
	private int pinfonum = 0;// 每张单几个PuchaseInfo
	private int pid ;
	
	/**
	 * Create the panel.
	 */
	public PurchaseOutJPanel(User currentUser) {
		this.currentUser = currentUser;
		setCount();
		pid = 0 - ((pdate.getYear() + 1900) * 1000000 + (pdate.getMonth() + 1) * 10000 + pdate.getDate() * 100 + count);
		purchaseOut();
		addListen();
		setmaker();
		showPurchase(new ArrayList<PurchaseInfo>());
	}

	private void purchaseOut() {
		JLabel poPidLabel = new JLabel("\u5355\u53F7");
		poPidLabel.setBounds(14, 12, 53, 18);

		poPidTextField = new JTextField();
		poPidTextField.setEditable(false);

		poPidTextField.setBounds(81, 6, 161, 30);
		poPidTextField.setColumns(10);
		poPidTextField.setText("" + pid);

		JLabel agChooseGoodsLabel = new JLabel("选择采购单号");
		agChooseGoodsLabel.setBounds(14, 117, 98, 18);

		agChoosePidTextField = new JTextField();
		agChoosePidTextField.setBounds(125, 111, 117, 30);
		agChoosePidTextField.setColumns(10);

		JLabel poSupplierLabel = new JLabel("供应商");
		poSupplierLabel.setBounds(248, 11, 52, 20);

		poSupplierTextField = new JTextField();
		poSupplierTextField.setEditable(false);
		poSupplierTextField.setBounds(303, 9, 117, 30);
		poSupplierTextField.setColumns(10);

		poFindPurchaseInfosButton = new JButton("查找入库信息");
		poFindPurchaseInfosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		poFindPurchaseInfosButton.setBounds(302, 114, 118, 30);

		supplierdcbm = new DefaultComboBoxModel();
		supplierdcbm.addElement("选择供应商");

		poDeleteButton = new JButton("删除");
		poDeleteButton.setBounds(441, 63, 124, 30);

		JLabel poDateLabel = new JLabel("日期");
		poDateLabel.setBounds(441, 8, 32, 24);
		poDateLabel.setFont(new Font("宋体", Font.PLAIN, 16));

		poDateComboBox = new DateField();
		poDateComboBox.setBounds(487, 8, 167, 25);
		poDateComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		JLabel poUserLabel = new JLabel("制单人");
		poUserLabel.setBounds(14, 69, 53, 18);

		poUserdcbm = new DefaultComboBoxModel();

		JComboBox poUserComboBox = new JComboBox(poUserdcbm);
		/*
		 *第一次进入系统，默认制表人显示当前登录用户 
		 */
		if (currentUser != null) {
			poUserComboBox.setSelectedItem(userService.queryEmployee(currentUser).getEname());
		}
		poUserComboBox.setEditable(false);
		poUserComboBox.setBounds(81, 62, 161, 30);

		JLabel poRemarkLabel = new JLabel("备注");
		poRemarkLabel.setBounds(581, 61, 30, 18);

		poRemarkTextArea = new JTextArea();
		poRemarkTextArea.setBounds(625, 46, 301, 95);
		poRemarkTextArea.setBorder(BorderFactory.createEtchedBorder());

		JScrollPane poScrollPane = new JScrollPane();
		poScrollPane.setBounds(30, 147, 1253, 285);

		poCheckButton = new JButton("添加出库信息");
		poCheckButton.setBounds(193, 450, 155, 64);
		poCheckButton.setFont(new Font("宋体", Font.PLAIN, 18));

		JLabel poDealLabel = new JLabel("应付");
		poDealLabel.setBounds(991, 477, 64, 37);
		poDealLabel.setFont(new Font("宋体", Font.PLAIN, 32));

		poDealTextField = new JTextField();
		poDealTextField.setBounds(1105, 467, 155, 47);
		poDealTextField.setEditable(false);
		poDealTextField.setColumns(10);

		pusrchaseOutJtable = new PusrchaseOutJtable(new ArrayList<PurchaseInfo>());
		poScrollPane.setViewportView(pusrchaseOutJtable);


		poaddPurchaseButton = new JButton("新增出库单号");
		poaddPurchaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		poaddPurchaseButton.setBounds(441, 114, 124, 30);

		poPurchasetable = new JTable();
		poPurchasetable.setColumnSelectionAllowed(false);
		poPurchasetable.setCellSelectionEnabled(false);
		poPurchasetable.setRowSelectionAllowed(true);
		poPurchasetable.setBounds(623, 61, 305, 72);

		JScrollPane purchaseScrollPane = new JScrollPane();
		purchaseScrollPane.setBounds(963, 10, 304, 132);
		purchaseScrollPane.setViewportView(poPurchasetable);

		JLabel poDayPuchaseLabel = new JLabel("当次出库单");
		poDayPuchaseLabel.setBounds(873, 13, 75, 17);
	

		finshbutton = new JButton("审核通过");
		finshbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		finshbutton.setBounds(709, 450, 155, 64);

		clearSelectionButton = new JButton("清除行选中");
		clearSelectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		clearSelectionButton.setBounds(302, 63, 118, 30);

		updateChangeButton = new JButton("重新选择时间");
		updateChangeButton.setBounds(681, 10, 122, 23);

		outPressbutton = new JButton("导    出");
		outPressbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		outPressbutton.setBounds(451, 450, 155, 64);

		add(updateChangeButton);
		setLayout(null);
		add(poPidLabel);
		add(poPidTextField);
		add(agChooseGoodsLabel);
		add(agChoosePidTextField);
		add(poSupplierLabel);
		add(poSupplierTextField);
		add(poFindPurchaseInfosButton);
		add(poDeleteButton);
		add(poUserLabel);
		add(poDateLabel);
		add(poDateComboBox);
		add(poUserComboBox);
		add(poRemarkLabel);
		add(poRemarkTextArea);
		add(poScrollPane);
		add(poCheckButton);
		add(poDealLabel);
		add(poDealTextField);
		add(outPressbutton);
		add(finshbutton);
		add(clearSelectionButton);
		add(poaddPurchaseButton);
		add(poDayPuchaseLabel);
		add(purchaseScrollPane);
	}

	/**
	 * 加载监听器
	 */
	public void addListen(){
		outPressbutton.addMouseListener(new MouseAdapter() {
			/**
			 * 打印出库单
			 */
			public void mouseClicked(MouseEvent e) {
				outPress();
			}
		});
		
		updateChangeButton.addMouseListener(new MouseAdapter() {
			/**
			 * 根据选择的时间重新设置pid
			 */
			public void mouseClicked(MouseEvent e) {
				setpid();
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
		
		agChoosePidTextField.addKeyListener(new KeyAdapter() {
			/**
			 * 限制查询文本框，键盘只能输入数字
			 */
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
			}
		});
		agChoosePidTextField.addFocusListener(new FocusAdapter() {
			/**
			 * 当文本框失去焦点，自动按照文本框查询
			 */
			public void focusLost(FocusEvent arg0) {
				if ("".equals(agChoosePidTextField.getText().trim())
						|| agChoosePidTextField.getText().trim().isEmpty()) {
					return;
				}
				List<PurchaseInfo> purchaseInfoList = purchaseInfoService
						.queryByFuzzyPid(Integer.parseInt(agChoosePidTextField.getText().trim()));
				pusrchaseOutJtable.setList(purchaseInfoList);
			}
		});
		
		poFindPurchaseInfosButton.addMouseListener(new MouseAdapter() {
			/**
			 * 点击按钮查询采购入库信息单
			 */
			public void mouseClicked(MouseEvent e) {
				showPurchaseInfo();
			}
		});
		
		poDeleteButton.addMouseListener(new MouseAdapter() {
			/**
			 * 点击按钮，如果存在表格选中条，则进行删除
			 */
			public void mouseClicked(MouseEvent arg0) {
				if (pusrchaseOutJtable.getSelectedRow() >= 0) {
					//deleteGood();
					JOptionPane.showMessageDialog(null, "请前往商品管理页面删除");
					return;
				} else if (poPurchasetable.getSelectedRow() >= 0) {
					deletePuechaseInfo();
				} else {
					JOptionPane.showMessageDialog(null, "请先选择要删除的条目！");
				}
			}
		});
		
		poCheckButton.addMouseListener(new MouseAdapter() {
			/**
			 * 往采购单号Purchase上添加详细PurchaseInfo信息 一张单号可支持多张PurchaseInfo信息
			 */
			public void mouseClicked(MouseEvent arg0) {
				addPurchaseInfo();
			}
		});
		
		pusrchaseOutJtable.addMouseListener(new MouseAdapter() {
			/**
			 * 点击按钮，自动设置供应商信息到文本框
			 */
			public void mouseClicked(MouseEvent e) {
				setSupplier();
			}
		});
		
		poaddPurchaseButton.addMouseListener(new MouseAdapter() {
			/**
			 * 通过点击piaddPurchaseButton按钮,添加Purchase对象 即出库单号进入数据库
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
				
				if (updateGstock() && updatePurchaseInfo()) {
					JOptionPane.showMessageDialog(null, "入库成功，请凭打印单前往财务处结账！");
					purchaseOutInfos.removeAll(purchaseOutInfos);
					purchaseInInfos.removeAll(purchaseInInfos);
				}
				repaintAll();
			}
		});
	}
	
	
	
	/**
	 * 打印出库单
	 */
	public void outPress() {
		if (purchaseOutInfos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "采购单号内无出库信息！请重新操作！");
			return;
		}
		String[] PURCHASE_COLUMN_NAMES = { "出库单号", "商品名称", "数量", "厂家", "单价", "总价" };
		Object[][] obj = new Object[pinfonum + 3][6];
		obj[0][0] = "出库单号";
		obj[0][1] = "商品名称";
		obj[0][2] = "数量";
		obj[0][3] = "厂家";
		obj[0][4] = "单价";
		obj[0][5] = "总价";
		int i = 1;
		for (PurchaseInfo pis : purchaseOutInfos) {
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
		obj[i][5] = poDealTextField.getText();
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
		jtable.setModel(new DefaultTableModel(obj, new String[] { "", "", "", "采购出库单", "", "" }));
		new OutExcel(jtable);

	}
	
	/**
	 * 将出库数据更新到数据库中，如果数据库中存在相同pid，gid的PurchaseInfo，则将pnum相加
	 * 并将入库信息更新，并记录更新信息
	 */
	public boolean updatePurchaseInfo(){
		boolean flag=false;
		for(PurchaseInfo po:purchaseOutInfos){
			PurchaseInfo tempPo=purchaseInfoService.qureyPurchaseInfoInByPidAndGid(po.getPid(), po.getGid());
			if(tempPo == null){
				flag=purchaseInfoService.addPurchaseInfo(po);
			}else{
				po.setPnum(po.getPnum()+tempPo.getPnum());
				flag=purchaseInfoService.update(po.getPid(),po.getGid(), po);
			}
			for (PurchaseInfo pi : purchaseInInfos) {
				if(po.getGid()==pi.getGid()){
					int oldnum = pi.getPnum();
					pi.setPnum(oldnum + po.getPnum());// 更正入库单入库数量
					Purchase p = purchaseService.qureyPurchaseByPid(pi.getPid());
					if (p.getRemark() == null) {
						p.setRemark("");
					}
					p.setRemark((p.getRemark().isEmpty() ? ""
							: (p.getRemark() + "\n")) + pdate.toLocaleString() + ","
							+ goodsService.queryGoodsById(pi.getGid()).getGname() + "入库数量:" +
							pi.getPnum() + "=" + oldnum
							+ "+(" + po.getPnum() + ")");// 记录入库数量的改变
					flag=purchaseInfoService.update(pi.getPid(),po.getGid(), pi);
					flag=purchaseService.update(p.getPid(), p);
				}
			}
		}
		return flag;
	}

	/**
	 * 根据表格中选中的采购单刷新页面
	 */
	protected void setSupplier() {
		if (pusrchaseOutJtable.getSelectedColumn() > 0) {
			Purchase purchase = purchaseService.qureyPurchaseByPid(pusrchaseOutJtable.getSelectPid());
			if (purchase == null) {
				JOptionPane.showMessageDialog(null, "系统繁忙，请稍后再试！");
				return;
			}
			Distributor distributor = distributorService.queryDistributorById(purchase.getDid());
			if (distributor == null) {
				JOptionPane.showMessageDialog(null, "系统繁忙，请稍后再试！");
				return;
			}
			poSupplierTextField.setText(distributor.getDname());
			supplierdcbm.removeAllElements();
			supplierdcbm.addElement(distributor.getDname());
			agChoosePidTextField.setText(purchase.getPid() + "");
			poRemarkTextArea.setText(purchase.getRemark());
		}
	}

	/**
	 * 根据页面操作查询PurchaseInfo
	 */
	public void showPurchaseInfo() {
		List<PurchaseInfo> purchaseInfoList = null;
		if (agChoosePidTextField.getText().trim().isEmpty() || "".equals(agChoosePidTextField.getText().trim())) {
			purchaseInfoList = purchaseInfoService.queryAllIn();
		} else {
			purchaseInfoList = purchaseInfoService
					.queryByFuzzyPid(Integer.parseInt(agChoosePidTextField.getText().trim()));
		}
		pusrchaseOutJtable.setList(purchaseInfoList);
	}

	/**
	 * 将采购信息添加到采购单中.如果出现pid，gid相同时，则直接更新之前的内容
	 */
	public void addPurchaseInfo() {
		if(purchaseService.qureyPurchaseByPid(pid)==null){
			JOptionPane.showMessageDialog(null, "请先添加出库单号，再重新输入");
			return;
		}
		Goods good = pusrchaseOutJtable.getSelectGoods();
		int pnum = 0;
		try {
			pnum = 0 - Integer.parseInt(
					(String) pusrchaseOutJtable.getModel().getValueAt(pusrchaseOutJtable.getSelectedRow(), 7));
			PurchaseInfo purchaseInInfo = pusrchaseOutJtable.getSelectPurchaseInfo();
			if ((0 - pnum) > purchaseInInfo.getPnum()) {
				JOptionPane.showMessageDialog(null, "出库单数量不能超过对应入库单数量，请重新输入");
				return;
			}
			if ((0 - pnum) > pusrchaseOutJtable.getSelectGoods().getGstock()) {
				JOptionPane.showMessageDialog(null, "出库单数量不能超过商品库存，请重新输入");
				return;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数量有误，请重新输入");
			return;
		}
		PurchaseInfo purchaseInfo = new PurchaseInfo(pid, good.getGid(), pnum);
		try {
			for (PurchaseInfo pi : purchaseOutInfos) {
				if (pi.getPid() == purchaseInfo.getPid() && pi.getGid() == purchaseInfo.getGid()) {
					purchaseOutInfos.remove(pi);
					purchaseInfo.setPnum(pi.getPnum() + purchaseInfo.getPnum());
					pinfonum--;					
				}
			}
		}catch(Exception e) {
			
		}
		JOptionPane.showMessageDialog(null, "添加出库信息成功");
		pinfonum++;
		good.setGstock(pnum);
		goodsList.add(good);
		purchaseOutInfos.add(purchaseInfo);
		PurchaseInfo pin = pusrchaseOutJtable.getSelectPurchaseInfo();
		purchaseInInfos.add(pin);
		deal();
		showPurchase(purchaseOutInfos);
	}

	/**
	 * 添加出库单号
	 * 
	 * @return true/false
	 */
	public void addPurchase() {
		String ename = (String) poUserdcbm.getSelectedItem();
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
		Purchase selectPurchase = purchaseService.qureyPurchaseByPid(pusrchaseOutJtable.getSelectPid());
		if ("选择供应商".equals(supplier) || "".equals(supplier)
				|| distributorService.queryDistributorByName(supplier) == null
				|| !supplier.equals(distributorService.queryDistributorById(selectPurchase.getDid()).getDname())) {
			JOptionPane.showMessageDialog(null, "供应商选择错误，请重新选择");
			return;
		}
		int did = distributorService.queryDistributorByName(supplier).getDid();
		Date pdate = new Date(this.pdate.getTime());
		Purchase pi = purchaseService.qureyPurchaseByPid(pusrchaseOutJtable.getSelectPurchaseInfo().getPid());
		Date inDate = new Date(pi.getPdate().getTime());
		if (pdate.before(inDate)) {
			JOptionPane.showMessageDialog(null, "出库单不能早于入库单出，请重新选择");
			return;
		}

		String remark = poRemarkTextArea.getText();
		Purchase purchase = new Purchase(pid, pdate, did, sid, remark, purchaseOutInfos);
		if(purchaseService.qureyPurchaseByPid(pid) == null){
			if(purchaseService.addPurchase(purchase)){
				JOptionPane.showMessageDialog(null, "添加成功");
			}
		}else{
			if(purchaseInfoService.queryByFuzzyPid(pid).isEmpty()){
				JOptionPane.showMessageDialog(null, "出库单已存在，且无具体采购信息");
				return;
			}
			JOptionPane.showMessageDialog(null, "出库单已存在，请重新选择");
			repaintAll();
			return;
		}
	}

	/**
	 * 记录当天已经有多少出库单
	 */
	public void setCount() {
		List<Purchase> purchaselist = purchaseService.queryAll();
		int i = 1;
		for (Purchase purchases : purchaselist) {
			if (purchases.getPdate().getYear() == pdate.getYear()) {
				if (purchases.getPdate().getMonth() == pdate.getMonth()) {
					if (purchases.getPdate().getDay() == pdate.getDay()) {
						if (purchases.getPid() < 0) {
							i++;
						}
					}
				}
			}
		}
		count = i;
	}

	/**
	 * 在表格中展示当前Purchase中PurchaseInfo
	 */
	public void showPurchase(List<PurchaseInfo> purchaseInfos) {
		String[] PURCHASE_COLUMN_NAMES = { "出库单号", "商品名称", "数量" };
		Object[][] obj = new Object[pinfonum][3];
		int i = 0;
		for (PurchaseInfo purchaseOutInfos : purchaseInfos) {
			if (purchaseOutInfos.getPid() == pid) {
				obj[i][0] = pid;
				obj[i][1] = goodsService.queryGoodsById(purchaseOutInfos.getGid()).getGname();
				obj[i][2] = purchaseOutInfos.getPnum();
				i++;
			}
		}
		poPurchasetable.setModel(new DefaultTableModel(obj, PURCHASE_COLUMN_NAMES) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		poPurchasetable.getColumnModel().getColumn(0).setResizable(false);
		poPurchasetable.getColumnModel().getColumn(1).setResizable(false);
		poPurchasetable.getColumnModel().getColumn(2).setResizable(false);
		poPurchasetable.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * 商品删除
	 */
	public void deleteGood() {
		if (pusrchaseOutJtable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行在进行删除操作");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "谨慎操作，是否删除", "注意", 2);
		if (select == 0) {
			try {
				Goods good = goodsService.queryGoodsById(pusrchaseOutJtable.getSelectGoods().getGid());
				if (goodsService.deleteById(pusrchaseOutJtable.getSelectGoods().getGid())) {
					JOptionPane.showMessageDialog(null, "删除成功");
					int i = 0;
					for (Goods goods : goodsList) {
						if (goods.getGname().equals(good.getGname())) {
							goodsList.remove(goods);
							i++;
						}
					}
					for(PurchaseInfo po:purchaseOutInfos){
						if(po.getGid()==good.getGid()){
							purchaseOutInfos.remove(po);
						}
					}
					for(PurchaseInfo pi:purchaseInInfos){
						if(pi.getGid()==good.getGid()){
							purchaseInInfos.remove(pi);
						}
					}
					pinfonum -= i;
					deal();
					List<PurchaseInfo> purchaseInfoList = purchaseInfoService
							.queryByFuzzyPid(Integer.parseInt(agChoosePidTextField.getText().trim()));
					pusrchaseOutJtable.setList(purchaseInfoList);
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
		if (poPurchasetable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行在进行删除操作");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "谨慎操作，是否删除", "注意", 2);
		if (select == 0) {
			try {
				String gname = (String) poPurchasetable.getModel().getValueAt(poPurchasetable.getSelectedRow(), 1);
				Goods good = goodsService.queryGoodsByName(gname);
				try {
					for(PurchaseInfo po:purchaseOutInfos){
						if(po.getGid()==good.getGid()){
							purchaseOutInfos.remove(po);
						}
					}
					for(PurchaseInfo pi:purchaseInInfos){
						if(pi.getGid()==good.getGid()){
							purchaseInInfos.remove(pi);
						}
					}
				}catch(Exception e) {
					
				}
				goodsList.remove(good);
				deal();
				pinfonum--;
				JOptionPane.showMessageDialog(null, "删除成功");
				showPurchase(purchaseOutInfos);		
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "系统繁忙，请稍后再试！");
			}
		}

	}

	/**
	 * 重置页面
	 */
	public void repaintAll() {
		this.removeAll();
		setCount();
		setpid();
		purchaseOut();
		addListen();
		setmaker();
	}

	/**
	 * 设置表格取消选中
	 */
	public void clearSelect() {
		poPurchasetable.clearSelection();
		pusrchaseOutJtable.clearSelection();
	}

	/**
	 * 将入库单的数据存入数据库，更新商品库存
	 */
	public boolean updateGstock() {
		int[] updateId = new int[goodsList.size()];
		if (goodsList.isEmpty()) {
			JOptionPane.showMessageDialog(null, "无数据需要审核");
			return false;
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
			JOptionPane.showMessageDialog(null, noupdate + "更新失败");
		}

		return flag;
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
			poDealTextField.setText(deal + "");
		}
	}

	/**
	 * 设置所选日期，设置所选日期的pid
	 */
	public void setpid() {
		pdate = poDateComboBox.getDate();
		setCount();
		pid = 0 - ((pdate.getYear() + 1900) * 1000000 + (pdate.getMonth() + 1) * 10000 + pdate.getDate() * 100 + count);
		poPidTextField.setText(pid + "");
	}
	
	/**
	 * 设置制表人栏只有admin权限的用户才能选择
	 */
	public void setmaker(){
		List<User> users = userService.queryUser();
		for (User user : users) {
			if ("admin".equals(user.getUsertype())) {
				Employee employee = employeeService.queryByEid(user.getEid());
				if (employee != null) {
					poUserdcbm.addElement(employee.getEname());
					employeeList.add(employee);
				}
			}
		}
	}
}
