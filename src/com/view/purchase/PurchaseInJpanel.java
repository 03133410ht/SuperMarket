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
 * �ɹ����Jpanel
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
	private int count = 1;// ����ڼ�����ⵥ
	private int pinfonum = 0;// ÿ�ŵ�����PuchaseInfo
	private int pid;
	private JTable piPurchasetable;
	private User currentUser;

	/**
	 * ����Jpanel
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
	 * �������ҳ��
	 */
	private void purchaseIn() {
		JLabel piPidLabel = new JLabel("\u5355\u53F7");
		piPidLabel.setBounds(14, 17, 40, 18);

		piPidTextField = new JTextField();
		piPidTextField.setEditable(false);
		piPidTextField.setBounds(77, 14, 117, 30);
		piPidTextField.setColumns(10);
		piPidTextField.setText("" + pid);

		JLabel agChooseGoodsLabel = new JLabel("ѡ����Ʒ");
		agChooseGoodsLabel.setBounds(14, 114, 60, 18);

		agChooseGoodsTextField = new JTextField();
		agChooseGoodsTextField.setBounds(77, 108, 117, 30);
		agChooseGoodsTextField.setColumns(10);

		JLabel piSupplierLabel = new JLabel("��Ӧ��");
		piSupplierLabel.setBounds(208, 16, 52, 18);

		piSupplierTextField = new JTextField();
		piSupplierTextField.setBounds(274, 13, 130, 30);
		piSupplierTextField.setColumns(10);

		piFindGoodsButton = new JButton("������Ʒ");
		piFindGoodsButton.setBounds(271, 108, 129, 30);

		supplierdcbm = new DefaultComboBoxModel();
		setSupplier();

		piSupplierComboBox = new JComboBox(supplierdcbm);
		piSupplierComboBox.setBounds(414, 12, 129,30);

		piDeleteButton = new JButton("ɾ��");
		piDeleteButton.setBounds(414, 57, 129, 30);

		JLabel piDateLabel = new JLabel("����");
		piDateLabel.setBounds(557, 13, 32, 24);
		piDateLabel.setFont(new Font("����", Font.PLAIN, 16));

		piDateComboBox = new DateField();
		piDateComboBox.setBounds(599, 10, 167, 25);
		piDateComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		JLabel piUserLabel = new JLabel("�Ƶ���");
		piUserLabel.setBounds(14, 64, 53, 18);

		piUserdcbm = new DefaultComboBoxModel();

		JComboBox piUserComboBox = new JComboBox(piUserdcbm);
		piUserComboBox.setEditable(false);
		piUserComboBox.setBounds(77, 57, 117, 27);
		/*
		 * ��һ�ν���ϵͳ��Ĭ���Ʊ���Ϊ��ǰ��¼�û�
		 */
		if (currentUser != null) {
			piUserComboBox.setSelectedItem(userService.queryEmployee(currentUser).getEname());
		}

		JLabel piRemarkLabel = new JLabel("��ע");
		piRemarkLabel.setBounds(557, 63, 30, 18);

		piRemarkTextArea = new JTextArea();
		piRemarkTextArea.setBounds(599, 64, 281, 76);
		piRemarkTextArea.setBorder(BorderFactory.createEtchedBorder());

		JScrollPane piScrollPane = new JScrollPane();
		piScrollPane.setBounds(30, 147, 1253, 285);

		piCheckButton = new JButton("��Ӳɹ���Ϣ");
		piCheckButton.setBounds(193, 450, 155, 64);
		piCheckButton.setFont(new Font("����", Font.PLAIN, 18));

		JLabel piDealLabel = new JLabel("Ӧ��");
		piDealLabel.setBounds(991, 477, 64, 37);
		piDealLabel.setFont(new Font("����", Font.PLAIN, 32));

		piDealTextField = new JTextField();
		piDealTextField.setBounds(1105, 467, 155, 47);
		piDealTextField.setEditable(false);
		piDealTextField.setColumns(10);

		pusrchaseInJtable = new PusrchaseInJtable(new ArrayList<Goods>());

		piaddPurchaseButton = new JButton("�����ɹ�����");
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

		JLabel piDayPuchaseLabel = new JLabel("���βɹ���");
		piDayPuchaseLabel.setBounds(827, 46, 80, 17);

		finshbutton = new JButton("���ͨ��");
		finshbutton.setFont(new Font("����", Font.PLAIN, 18));
		finshbutton.setBounds(623, 450, 155, 64);
	
		clearSelectionButton = new JButton("�����ѡ��");
		clearSelectionButton.setBounds(271, 57, 133, 30);

		updateDateButton = new JButton("����ѡ��ʱ��");
		updateDateButton.setBounds(780, 10, 129, 23);

		outPressbutton = new JButton("����");
		outPressbutton.setFont(new Font("����", Font.PLAIN, 18));
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
			 * ���ı���ʧȥ����ʱ�Զ������ı������ݲ�ѯ
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
			 * չʾ��Ӧ�����ı���
			 */
			public void focusLost(FocusEvent e) {
				showSupplier();
			}
		});
		
		piFindGoodsButton.addMouseListener(new MouseAdapter() {
			/**
			 * �����ı���ģ����ѯ
			 */
			public void mouseClicked(MouseEvent e) {
				showGoodsByFuzzy();
			}
		});
		
		piSupplierComboBox.addItemListener(new ItemListener() {
			/**
			 * ����piSupplierComboBox����ѡ����Ŀ�ı�ʱ����piSupplierTextFieldչʾѡ�е�Supplier
			 */
			public void itemStateChanged(ItemEvent arg0) {
				try {
					if (!"ѡ��Ӧ��".equals((String) supplierdcbm.getSelectedItem())) {
						piSupplierTextField.setText((String) supplierdcbm.getSelectedItem());
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "ϵͳ��æ�����Ժ����ԣ�");
				}
			}
		});
		
		piDeleteButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (pusrchaseInJtable.getSelectedRow() >= 0) {
					/*deleteGood();
					showGoodsByFuzzy();
					showPurchase(purchaseInfos);*/
					JOptionPane.showMessageDialog(null, "��ǰ����Ʒ����ҳ��ɾ��");
					return;
				} else if (piPurchasetable.getSelectedRow() >= 0) {
					deletePuechaseInfo();
					showPurchase(purchaseInfos);
				} else {
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫɾ������Ŀ��");
				}
			}
		});
		
		piCheckButton.addMouseListener(new MouseAdapter() {
			/**
			 * ���ɹ�����Purchase�������ϸPurchaseInfo��Ϣ һ�ŵ��ſ�֧�ֶ���PurchaseInfo��Ϣ
			 */
			public void mouseClicked(MouseEvent arg0) {
				addPurchaseInfo();
			}
		});
		
		pusrchaseInJtable.addMouseListener(new MouseAdapter() {
			/**
			 * ѡ�б������Ʒ�󣬽���Ʒremarkд���Ӧ�ı���
			 */
			public void mouseClicked(MouseEvent e) {
				setRemark();
			}
		});
		
		piaddPurchaseButton.addMouseListener(new MouseAdapter() {
			/**
			 * ͨ�����piaddPurchaseButton��ť,���Purchase���� ���ɹ����Ž������ݿ�
			 */
			public void mouseClicked(MouseEvent arg0) {
				addPurchase();
			}
		});
		
		finshbutton.addMouseListener(new MouseAdapter() {
			/**
			 * �����finshbutton��ť������Purchase������
			 */
			public void mouseClicked(MouseEvent e) {
				updateGstock();
			}
		});
		
		clearSelectionButton.addMouseListener(new MouseAdapter() {
			/**
			 * �����clearSelectionButton��ť��������ѡ��
			 */
			public void mouseClicked(MouseEvent arg0) {
				clearSelect();
			}
		});
		
		updateDateButton.addMouseListener(new MouseAdapter() {
			/**
			 * �����ť������ʱ���Ӧ��pid
			 */
			public void mouseClicked(MouseEvent arg0) {
				setpid();
			}
		});
		
		outPressbutton.addMouseListener(new MouseAdapter() {
			/**
			 * ������ⵥ
			 */
			public void mouseClicked(MouseEvent arg0) {
				outPress();
			}
		});
		
	}
	
	/**
	 * ��ӡ�����¼�����
	 */
	public void outPress() {
		if (purchaseInfos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�ɹ��������޲ɹ���Ϣ�������²�����");
			return;
		}
		Object[][] obj = new Object[pinfonum + 3][6];
		obj[0][0] = "�ɹ�����";
		obj[0][1] = "��Ʒ����";
		obj[0][2] = "����";
		obj[0][3] = "����";
		obj[0][4] = "����";
		obj[0][5] = "�ܼ�";
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
		obj[i][4] = "    ��   �ƣ�";
		obj[i][5] = piDealTextField.getText();
		obj[i + 1][0] = pdate.toLocaleString();
		obj[i + 1][1] = null;
		obj[i + 1][2] = null;
		obj[i + 1][3] = null;
		obj[i + 1][4] = "   �Ƶ��ˣ�";
		User maker = null;
		for (User u : userService.queryUser()) {
			if (u.getSid() == purchaseService.qureyPurchaseByPid((Integer) obj[1][0]).getSid()) {
				maker = u;
			}
		}
		obj[i + 1][5] = userService.queryEmployee(maker).getEname();
		JTable jtable = new JTable();
		jtable.setTableHeader(new JTableHeader());
		jtable.setModel(new DefaultTableModel(obj, new String[] { "", "", "", "�ɹ���ⵥ", "", "" }));
		new OutExcel(jtable);
	}

	/**
	 * ѡ�б������Ʒ�󣬽���Ʒremarkд���Ӧ�ı���
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
	 * ���ɹ���Ϣ��ӵ��ɹ�����,�������pid��gid��ͬʱ����ֱ�Ӹ���֮ǰ������
	 */
	public void addPurchaseInfo() {
		if(purchaseService.qureyPurchaseByPid(pid)==null){
			JOptionPane.showMessageDialog(null, "������Ӳɹ����ţ�����������");
			return;
		}
		Goods good = pusrchaseInJtable.getSelectGoods();
		int pnum = 0;
		try {
			pnum = Integer
					.parseInt((String) pusrchaseInJtable.getModel().getValueAt(pusrchaseInJtable.getSelectedRow(), 5));
			if(pnum<0){
				JOptionPane.showMessageDialog(null, "�����������0������������");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "������������������");
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
		JOptionPane.showMessageDialog(null, "��Ӳɹ���Ϣ�ɹ�");
		pinfonum++;
		good.setGstock(pnum);
		goodsList.add(good);
		purchaseInfos.add(purchaseInfo);
		deal();
		piPurchasetable.removeAll();
		showPurchase(purchaseInfos);
	}

	/**
	 * ��Ӳɹ�����
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
					String password = JOptionPane.showInputDialog(null, "���룺", "�������û�����", JOptionPane.QUESTION_MESSAGE);
					if (!currentUser.getPassword().equals(password)) {
						JOptionPane.showMessageDialog(null, "������������²���");
						return;
					}
				}
			}
		}
		int sid = currentUser.getSid();
		String supplier = (String) supplierdcbm.getSelectedItem();
		if ("ѡ��Ӧ��".equals(supplier) || "".equals(supplier)
				|| distributorService.queryDistributorByName(supplier) == null) {
			JOptionPane.showMessageDialog(null, "��Ӧ��ѡ�����������ѡ��");
			return;
		}
		int did = distributorService.queryDistributorByName(supplier).getDid();
		Date pdate = new Date(this.pdate.getTime());
		String remark = piRemarkTextArea.getText();
		Purchase purchase = new Purchase(pid, pdate, did, sid, remark, purchaseInfos);
		if(purchaseService.qureyPurchaseByPid(pid) == null){
			if(purchaseService.addPurchase(purchase)){
				JOptionPane.showMessageDialog(null, "��ӳɹ�");
			}
		}else{
			if(purchaseInfoService.queryByFuzzyPid(pid).isEmpty()){
				JOptionPane.showMessageDialog(null, "��ⵥ�Ѵ��ڣ����޾���ɹ���Ϣ");
				return;
			}
			JOptionPane.showMessageDialog(null, "��ⵥ�Ѵ��ڣ�������ѡ��");
			repaintAll();
			return;
		}
			

	}

	/**
	 * ��¼�����Ѿ��ж�����ⵥ
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
	 * �ڱ����չʾ��ǰPurchase�н�Ҫ����PurchaseInfo
	 */
	public void showPurchase(List<PurchaseInfo> purchaseInfolist) {
		String[] PURCHASE_COLUMN_NAMES = { "�ɹ�����", "��Ʒ����", "����" };
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
	 * ��Ʒɾ��
	 */
	public void deleteGood() {
		if (pusrchaseInJtable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��һ���ڽ���ɾ������");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "�����������Ƿ�ɾ��", "ע��", 2);
		if (select == 0) {
			try {
				Goods good = goodsService.queryGoodsById(pusrchaseInJtable.getSelectGoods().getGid());
				if (goodsService.deleteById(pusrchaseInJtable.getSelectGoods().getGid())) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
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
				JOptionPane.showMessageDialog(null, "ϵͳ��æ�����Ժ����ԣ�");
			}
		}
	}

	/**
	 * ɾ��PurchaseInfo
	 */
	public void deletePuechaseInfo() {
		if (piPurchasetable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��һ���ڽ���ɾ������");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "�����������Ƿ�ɾ��", "ע��", 2);
		String gname = (String) piPurchasetable.getModel().getValueAt(piPurchasetable.getSelectedRow(), 1);
		Goods good = goodsService.queryGoodsByName(gname);
		try {
			if (select == 0) {
				for (PurchaseInfo pi : purchaseInfos) {
					if(pi.getGid()==good.getGid()){
						purchaseInfos.remove(pi);
					}
				}
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
			}
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(null, "ϵͳ��æ");
		}
	}

	/**
	 * ����ҳ��
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
	 * ���ñ��ȡ��ѡ��
	 */
	public void clearSelect() {
		piPurchasetable.clearSelection();
		pusrchaseInJtable.clearSelection();
	}

	/**
	 * ����ⵥ�����ݴ������ݿ⣬������Ʒ���
	 */
	public void updateGstock() {
		int[] updateId = new int[goodsList.size()];
		if (goodsList.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�����ݸ��£����������ɹ�����");
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
			JOptionPane.showMessageDialog(null, "��Ʒ��ţ�"+noupdate + "����ʧ��");
		}
		StringBuffer sb=new StringBuffer("");
		for (PurchaseInfo pi : purchaseInfos) {
			if (!purchaseInfoService.addPurchaseInfo(pi)) {
				sb.append(pi.getGid()+"\t");
				flag=false;
			}
		}
		if(!flag){
			JOptionPane.showMessageDialog(null, "�ɹ���Ϣ��ţ�"+sb+ "����ʧ��");
			return;
		}
		JOptionPane.showMessageDialog(null, "���ɹ�,��ƾ��ӡ��ǰ�����񴦽��ˣ�����");
		purchaseInfos.remove(purchaseInfos);
		repaintAll();
	}

	/**
	 * �����ڲɹ���ʱ�������ܳɱ��۲���ʾ�ڶ�Ӧ�ı���
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
	 * ������ѡ���ڣ�������ѡ���ڵ�pid
	 */
	public void setpid() {
		pdate = piDateComboBox.getDate();
		setCount();
		pid = (pdate.getYear() + 1900) * 1000000 + (pdate.getMonth() + 1) * 10000 + pdate.getDate() * 100 + count;
		piPidTextField.setText(pid + "");
	}

	/**
	 * ��Ʒģ���ֶβ�ѯ����ʾ�ڱ���
	 */
	public void showGoodsByFuzzy() {
		List<Goods> glist = goodsService.fuzzyQuery(agChooseGoodsTextField.getText().trim(), "", ">-1");
		pusrchaseInJtable.setList(glist);
	}

	/**
	 * ��������չʾ��Ӧ��
	 */
	public void showSupplier() {
		if (StringUtil.isEmpty(piSupplierTextField.getText().trim())) {
			return;
		}
		supplierdcbm.removeAllElements();
		supplierdcbm.addElement("ѡ��Ӧ��");
		List<Distributor> distributorList = distributorService.queryByFuzzy(piSupplierTextField.getText());
		for (Distributor distributor : distributorList) {
			supplierdcbm.addElement(distributor.getDname());
		}
	}

	/**
	 * ����Ӧ��ѡ�񸴺Ͽ����Ԫ��
	 */
	public void setSupplier() {
		supplierdcbm.addElement("ѡ��Ӧ��");
		List<Distributor> distributorList = distributorService.queryAll();
		for (Distributor distributor : distributorList) {
			supplierdcbm.addElement(distributor.getDname());
		}
	}
	
	/**
	 * �����Ʊ��˸��Ͽ�������adminȨ�޵��û�
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
