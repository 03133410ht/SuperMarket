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
 * �ɹ�����Jpanel
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
	private int count = 1;// ����ڼ��ų��ⵥ
	private int pinfonum = 0;// ÿ�ŵ�����PuchaseInfo
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

		JLabel agChooseGoodsLabel = new JLabel("ѡ��ɹ�����");
		agChooseGoodsLabel.setBounds(14, 117, 98, 18);

		agChoosePidTextField = new JTextField();
		agChoosePidTextField.setBounds(125, 111, 117, 30);
		agChoosePidTextField.setColumns(10);

		JLabel poSupplierLabel = new JLabel("��Ӧ��");
		poSupplierLabel.setBounds(248, 11, 52, 20);

		poSupplierTextField = new JTextField();
		poSupplierTextField.setEditable(false);
		poSupplierTextField.setBounds(303, 9, 117, 30);
		poSupplierTextField.setColumns(10);

		poFindPurchaseInfosButton = new JButton("���������Ϣ");
		poFindPurchaseInfosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		poFindPurchaseInfosButton.setBounds(302, 114, 118, 30);

		supplierdcbm = new DefaultComboBoxModel();
		supplierdcbm.addElement("ѡ��Ӧ��");

		poDeleteButton = new JButton("ɾ��");
		poDeleteButton.setBounds(441, 63, 124, 30);

		JLabel poDateLabel = new JLabel("����");
		poDateLabel.setBounds(441, 8, 32, 24);
		poDateLabel.setFont(new Font("����", Font.PLAIN, 16));

		poDateComboBox = new DateField();
		poDateComboBox.setBounds(487, 8, 167, 25);
		poDateComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		JLabel poUserLabel = new JLabel("�Ƶ���");
		poUserLabel.setBounds(14, 69, 53, 18);

		poUserdcbm = new DefaultComboBoxModel();

		JComboBox poUserComboBox = new JComboBox(poUserdcbm);
		/*
		 *��һ�ν���ϵͳ��Ĭ���Ʊ�����ʾ��ǰ��¼�û� 
		 */
		if (currentUser != null) {
			poUserComboBox.setSelectedItem(userService.queryEmployee(currentUser).getEname());
		}
		poUserComboBox.setEditable(false);
		poUserComboBox.setBounds(81, 62, 161, 30);

		JLabel poRemarkLabel = new JLabel("��ע");
		poRemarkLabel.setBounds(581, 61, 30, 18);

		poRemarkTextArea = new JTextArea();
		poRemarkTextArea.setBounds(625, 46, 301, 95);
		poRemarkTextArea.setBorder(BorderFactory.createEtchedBorder());

		JScrollPane poScrollPane = new JScrollPane();
		poScrollPane.setBounds(30, 147, 1253, 285);

		poCheckButton = new JButton("��ӳ�����Ϣ");
		poCheckButton.setBounds(193, 450, 155, 64);
		poCheckButton.setFont(new Font("����", Font.PLAIN, 18));

		JLabel poDealLabel = new JLabel("Ӧ��");
		poDealLabel.setBounds(991, 477, 64, 37);
		poDealLabel.setFont(new Font("����", Font.PLAIN, 32));

		poDealTextField = new JTextField();
		poDealTextField.setBounds(1105, 467, 155, 47);
		poDealTextField.setEditable(false);
		poDealTextField.setColumns(10);

		pusrchaseOutJtable = new PusrchaseOutJtable(new ArrayList<PurchaseInfo>());
		poScrollPane.setViewportView(pusrchaseOutJtable);


		poaddPurchaseButton = new JButton("�������ⵥ��");
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

		JLabel poDayPuchaseLabel = new JLabel("���γ��ⵥ");
		poDayPuchaseLabel.setBounds(873, 13, 75, 17);
	

		finshbutton = new JButton("���ͨ��");
		finshbutton.setFont(new Font("����", Font.PLAIN, 18));
		finshbutton.setBounds(709, 450, 155, 64);

		clearSelectionButton = new JButton("�����ѡ��");
		clearSelectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		clearSelectionButton.setBounds(302, 63, 118, 30);

		updateChangeButton = new JButton("����ѡ��ʱ��");
		updateChangeButton.setBounds(681, 10, 122, 23);

		outPressbutton = new JButton("��    ��");
		outPressbutton.setFont(new Font("����", Font.PLAIN, 18));
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
	 * ���ؼ�����
	 */
	public void addListen(){
		outPressbutton.addMouseListener(new MouseAdapter() {
			/**
			 * ��ӡ���ⵥ
			 */
			public void mouseClicked(MouseEvent e) {
				outPress();
			}
		});
		
		updateChangeButton.addMouseListener(new MouseAdapter() {
			/**
			 * ����ѡ���ʱ����������pid
			 */
			public void mouseClicked(MouseEvent e) {
				setpid();
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
		
		agChoosePidTextField.addKeyListener(new KeyAdapter() {
			/**
			 * ���Ʋ�ѯ�ı��򣬼���ֻ����������
			 */
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
			}
		});
		agChoosePidTextField.addFocusListener(new FocusAdapter() {
			/**
			 * ���ı���ʧȥ���㣬�Զ������ı����ѯ
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
			 * �����ť��ѯ�ɹ������Ϣ��
			 */
			public void mouseClicked(MouseEvent e) {
				showPurchaseInfo();
			}
		});
		
		poDeleteButton.addMouseListener(new MouseAdapter() {
			/**
			 * �����ť��������ڱ��ѡ�����������ɾ��
			 */
			public void mouseClicked(MouseEvent arg0) {
				if (pusrchaseOutJtable.getSelectedRow() >= 0) {
					//deleteGood();
					JOptionPane.showMessageDialog(null, "��ǰ����Ʒ����ҳ��ɾ��");
					return;
				} else if (poPurchasetable.getSelectedRow() >= 0) {
					deletePuechaseInfo();
				} else {
					JOptionPane.showMessageDialog(null, "����ѡ��Ҫɾ������Ŀ��");
				}
			}
		});
		
		poCheckButton.addMouseListener(new MouseAdapter() {
			/**
			 * ���ɹ�����Purchase�������ϸPurchaseInfo��Ϣ һ�ŵ��ſ�֧�ֶ���PurchaseInfo��Ϣ
			 */
			public void mouseClicked(MouseEvent arg0) {
				addPurchaseInfo();
			}
		});
		
		pusrchaseOutJtable.addMouseListener(new MouseAdapter() {
			/**
			 * �����ť���Զ����ù�Ӧ����Ϣ���ı���
			 */
			public void mouseClicked(MouseEvent e) {
				setSupplier();
			}
		});
		
		poaddPurchaseButton.addMouseListener(new MouseAdapter() {
			/**
			 * ͨ�����piaddPurchaseButton��ť,���Purchase���� �����ⵥ�Ž������ݿ�
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
				
				if (updateGstock() && updatePurchaseInfo()) {
					JOptionPane.showMessageDialog(null, "���ɹ�����ƾ��ӡ��ǰ�����񴦽��ˣ�");
					purchaseOutInfos.removeAll(purchaseOutInfos);
					purchaseInInfos.removeAll(purchaseInInfos);
				}
				repaintAll();
			}
		});
	}
	
	
	
	/**
	 * ��ӡ���ⵥ
	 */
	public void outPress() {
		if (purchaseOutInfos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�ɹ��������޳�����Ϣ�������²�����");
			return;
		}
		String[] PURCHASE_COLUMN_NAMES = { "���ⵥ��", "��Ʒ����", "����", "����", "����", "�ܼ�" };
		Object[][] obj = new Object[pinfonum + 3][6];
		obj[0][0] = "���ⵥ��";
		obj[0][1] = "��Ʒ����";
		obj[0][2] = "����";
		obj[0][3] = "����";
		obj[0][4] = "����";
		obj[0][5] = "�ܼ�";
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
		obj[i][4] = "    ��   �ƣ�";
		obj[i][5] = poDealTextField.getText();
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
		jtable.setModel(new DefaultTableModel(obj, new String[] { "", "", "", "�ɹ����ⵥ", "", "" }));
		new OutExcel(jtable);

	}
	
	/**
	 * ���������ݸ��µ����ݿ��У�������ݿ��д�����ͬpid��gid��PurchaseInfo����pnum���
	 * ���������Ϣ���£�����¼������Ϣ
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
					pi.setPnum(oldnum + po.getPnum());// ������ⵥ�������
					Purchase p = purchaseService.qureyPurchaseByPid(pi.getPid());
					if (p.getRemark() == null) {
						p.setRemark("");
					}
					p.setRemark((p.getRemark().isEmpty() ? ""
							: (p.getRemark() + "\n")) + pdate.toLocaleString() + ","
							+ goodsService.queryGoodsById(pi.getGid()).getGname() + "�������:" +
							pi.getPnum() + "=" + oldnum
							+ "+(" + po.getPnum() + ")");// ��¼��������ĸı�
					flag=purchaseInfoService.update(pi.getPid(),po.getGid(), pi);
					flag=purchaseService.update(p.getPid(), p);
				}
			}
		}
		return flag;
	}

	/**
	 * ���ݱ����ѡ�еĲɹ���ˢ��ҳ��
	 */
	protected void setSupplier() {
		if (pusrchaseOutJtable.getSelectedColumn() > 0) {
			Purchase purchase = purchaseService.qureyPurchaseByPid(pusrchaseOutJtable.getSelectPid());
			if (purchase == null) {
				JOptionPane.showMessageDialog(null, "ϵͳ��æ�����Ժ����ԣ�");
				return;
			}
			Distributor distributor = distributorService.queryDistributorById(purchase.getDid());
			if (distributor == null) {
				JOptionPane.showMessageDialog(null, "ϵͳ��æ�����Ժ����ԣ�");
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
	 * ����ҳ�������ѯPurchaseInfo
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
	 * ���ɹ���Ϣ��ӵ��ɹ�����.�������pid��gid��ͬʱ����ֱ�Ӹ���֮ǰ������
	 */
	public void addPurchaseInfo() {
		if(purchaseService.qureyPurchaseByPid(pid)==null){
			JOptionPane.showMessageDialog(null, "������ӳ��ⵥ�ţ�����������");
			return;
		}
		Goods good = pusrchaseOutJtable.getSelectGoods();
		int pnum = 0;
		try {
			pnum = 0 - Integer.parseInt(
					(String) pusrchaseOutJtable.getModel().getValueAt(pusrchaseOutJtable.getSelectedRow(), 7));
			PurchaseInfo purchaseInInfo = pusrchaseOutJtable.getSelectPurchaseInfo();
			if ((0 - pnum) > purchaseInInfo.getPnum()) {
				JOptionPane.showMessageDialog(null, "���ⵥ�������ܳ�����Ӧ��ⵥ����������������");
				return;
			}
			if ((0 - pnum) > pusrchaseOutJtable.getSelectGoods().getGstock()) {
				JOptionPane.showMessageDialog(null, "���ⵥ�������ܳ�����Ʒ��棬����������");
				return;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "������������������");
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
		JOptionPane.showMessageDialog(null, "��ӳ�����Ϣ�ɹ�");
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
	 * ��ӳ��ⵥ��
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
		Purchase selectPurchase = purchaseService.qureyPurchaseByPid(pusrchaseOutJtable.getSelectPid());
		if ("ѡ��Ӧ��".equals(supplier) || "".equals(supplier)
				|| distributorService.queryDistributorByName(supplier) == null
				|| !supplier.equals(distributorService.queryDistributorById(selectPurchase.getDid()).getDname())) {
			JOptionPane.showMessageDialog(null, "��Ӧ��ѡ�����������ѡ��");
			return;
		}
		int did = distributorService.queryDistributorByName(supplier).getDid();
		Date pdate = new Date(this.pdate.getTime());
		Purchase pi = purchaseService.qureyPurchaseByPid(pusrchaseOutJtable.getSelectPurchaseInfo().getPid());
		Date inDate = new Date(pi.getPdate().getTime());
		if (pdate.before(inDate)) {
			JOptionPane.showMessageDialog(null, "���ⵥ����������ⵥ����������ѡ��");
			return;
		}

		String remark = poRemarkTextArea.getText();
		Purchase purchase = new Purchase(pid, pdate, did, sid, remark, purchaseOutInfos);
		if(purchaseService.qureyPurchaseByPid(pid) == null){
			if(purchaseService.addPurchase(purchase)){
				JOptionPane.showMessageDialog(null, "��ӳɹ�");
			}
		}else{
			if(purchaseInfoService.queryByFuzzyPid(pid).isEmpty()){
				JOptionPane.showMessageDialog(null, "���ⵥ�Ѵ��ڣ����޾���ɹ���Ϣ");
				return;
			}
			JOptionPane.showMessageDialog(null, "���ⵥ�Ѵ��ڣ�������ѡ��");
			repaintAll();
			return;
		}
	}

	/**
	 * ��¼�����Ѿ��ж��ٳ��ⵥ
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
	 * �ڱ����չʾ��ǰPurchase��PurchaseInfo
	 */
	public void showPurchase(List<PurchaseInfo> purchaseInfos) {
		String[] PURCHASE_COLUMN_NAMES = { "���ⵥ��", "��Ʒ����", "����" };
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
	 * ��Ʒɾ��
	 */
	public void deleteGood() {
		if (pusrchaseOutJtable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��һ���ڽ���ɾ������");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "�����������Ƿ�ɾ��", "ע��", 2);
		if (select == 0) {
			try {
				Goods good = goodsService.queryGoodsById(pusrchaseOutJtable.getSelectGoods().getGid());
				if (goodsService.deleteById(pusrchaseOutJtable.getSelectGoods().getGid())) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
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
				JOptionPane.showMessageDialog(null, "ϵͳ��æ�����Ժ����ԣ�");
			}
		}
	}

	/**
	 * ɾ��PurchaseInfo
	 */
	public void deletePuechaseInfo() {
		if (poPurchasetable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��һ���ڽ���ɾ������");
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "�����������Ƿ�ɾ��", "ע��", 2);
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
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
				showPurchase(purchaseOutInfos);		
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "ϵͳ��æ�����Ժ����ԣ�");
			}
		}

	}

	/**
	 * ����ҳ��
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
	 * ���ñ��ȡ��ѡ��
	 */
	public void clearSelect() {
		poPurchasetable.clearSelection();
		pusrchaseOutJtable.clearSelection();
	}

	/**
	 * ����ⵥ�����ݴ������ݿ⣬������Ʒ���
	 */
	public boolean updateGstock() {
		int[] updateId = new int[goodsList.size()];
		if (goodsList.isEmpty()) {
			JOptionPane.showMessageDialog(null, "��������Ҫ���");
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
			JOptionPane.showMessageDialog(null, noupdate + "����ʧ��");
		}

		return flag;
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
			poDealTextField.setText(deal + "");
		}
	}

	/**
	 * ������ѡ���ڣ�������ѡ���ڵ�pid
	 */
	public void setpid() {
		pdate = poDateComboBox.getDate();
		setCount();
		pid = 0 - ((pdate.getYear() + 1900) * 1000000 + (pdate.getMonth() + 1) * 10000 + pdate.getDate() * 100 + count);
		poPidTextField.setText(pid + "");
	}
	
	/**
	 * �����Ʊ�����ֻ��adminȨ�޵��û�����ѡ��
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
