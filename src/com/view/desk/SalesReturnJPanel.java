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
 * �����˻���� �趨ֻ���������۳����������˺�ͬʱ��һ���˻��������ݱ��붼��һ�����۳������е�salesInfo������Ϣ
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
	 * �������
	 */
	private void salesReturn() {
		JLabel srSalesLabel = new JLabel("����");
		srSalesLabel.setBounds(10, 27, 30, 18);

		srSalesTextField = new JTextField();
		srSalesTextField.setBounds(94, 24, 140, 24);
		srSalesTextField.setEditable(false);
		srSalesTextField.setColumns(10);

		JLabel srLabel = new JLabel("���ⵥ��");
		srLabel.setBounds(10, 85, 60, 18);

		srStockTextField = new JTextField();
		srStockTextField.setBounds(94, 82, 140, 24);
		srStockTextField.setColumns(10);

		srFindSidButton = new JButton("���ҵ���");
		srFindSidButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		srFindSidButton.setBounds(303, 81, 108, 27);

		JLabel srDateLabel = new JLabel("����");
		srDateLabel.setBounds(247, 27, 34, 20);
		srDateLabel.setFont(new Font("����", Font.PLAIN, 17));

		JLabel srUserLabel = new JLabel("�Ƶ���");
		srUserLabel.setBounds(517, 85, 45, 18);

		srDateComboBox = new DateField();
		srDateComboBox.setBounds(303, 27, 166, 25);
		srDateComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		srUserdcbm = new DefaultComboBoxModel();
		srUserComboBox = new JComboBox(srUserdcbm);
		srUserComboBox.setBounds(575, 82, 166, 24);
		setUser();

		JLabel srRemarkLabel = new JLabel("��ע");
		srRemarkLabel.setBounds(911, 27, 30, 18);

		srRemarkTextArea = new JTextArea();
		srRemarkTextArea.setBounds(955, 25, 316, 86);
		srRemarkTextArea.setBorder(BorderFactory.createEtchedBorder());

		srScrollPaneTable = new JScrollPane();
		srScrollPaneTable.setBounds(44, 134, 1253, 231);

		reImburseButton = new JButton("�˿�");
		reImburseButton.setBounds(100, 455, 155, 59);
		reImburseButton.setFont(new Font("����", Font.PLAIN, 27));

		JLabel srMoneyLabel = new JLabel("�˿���");
		srMoneyLabel.setBounds(633, 476, 108, 32);
		srMoneyLabel.setFont(new Font("����", Font.PLAIN, 27));

		srMoneyTextField = new JTextField();
		srMoneyTextField.setBounds(772, 473, 131, 33);
		srMoneyTextField.setEditable(false);
		srMoneyTextField.setColumns(10);

		JLabel srSumLabel = new JLabel("������");
		srSumLabel.setBounds(941, 476, 81, 32);
		srSumLabel.setFont(new Font("����", Font.PLAIN, 27));

		srSumTextField = new JTextField();
		srSumTextField.setBounds(1048, 474, 100, 32);
		srSumTextField.setEditable(false);
		srSumTextField.setColumns(10);

		srTable = new GoodsReturnJtable(new ArrayList<SalesInfo>());
		srScrollPaneTable.setViewportView(srTable);

		JLabel memberlabel = new JLabel("��Ա��");
		memberlabel.setBounds(517, 30, 45, 18);

		memberNametextField = new JTextField();
		memberNametextField.setBounds(575, 25, 113, 27);
		memberNametextField.setEditable(false);
		memberNametextField.setColumns(10);

		memberGradeLabel = new JLabel("��Ա�ȼ�");
		memberGradeLabel.setBounds(702, 26, 60, 21);

		firstPageButton = new JButton("��ҳ");
		firstPageButton.setBounds(64, 402, 93, 23);

		prePageButton = new JButton("��һҳ");
		prePageButton.setBounds(185, 402, 93, 23);

		nextPageButton = new JButton("��һҳ");
		nextPageButton.setBounds(303, 402, 93, 23);

		lastPageButton = new JButton("βҳ");
		lastPageButton.setBounds(427, 402, 93, 23);

		deleteButton = new JButton("ɾ��");
		deleteButton.setBounds(1178, 402, 93, 23);

		confirmButton = new JButton("ȷ��");
		confirmButton.setBounds(1055, 402, 93, 23);

		notePageLabel = new JLabel();
		notePageLabel.setBounds(1189, 374, 108, 15);

		pressButton = new JButton("����");
		pressButton.setFont(new Font("����", Font.PLAIN, 27));
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
	 * ���ؼ�����
	 */
	public void addListen() {
		srStockTextField.addKeyListener(new KeyAdapter() {
			/**
			 * �޶����۳��������ı���ֻ����������
			 */
			public void keyTyped(KeyEvent arg0) {
				KeyTypeUtil.numType(arg0);
			}

			/**
			 * �����ı��������ֶΣ�����Enterֱ�Ӳ�ѯ
			 */
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					showSales();
				}
			}
		});

		srFindSidButton.addMouseListener(new MouseAdapter() {
			/**
			 * ���²�ѯ��ť��ˢ�±��
			 */
			public void mouseClicked(MouseEvent arg0) {
				showSales();
			}
		});

		srTable.addMouseListener(new MouseAdapter() {
			/**
			 * �������ѡ���к󣬶�̬�����˻����ţ���Ա��Ϣ�����������۵�������� ˫�����ѡ���к��Զ�ˢ�²�ѯ����
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
			 * ��ת�����ҳ
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
			 * ������һҳ
			 */
			public void mouseClicked(MouseEvent arg0) {
				prePage();
				setPage();
			}
		});

		nextPageButton.addMouseListener(new MouseAdapter() {
			/**
			 * ������һҳ
			 */
			public void mouseClicked(MouseEvent arg0) {
				nextPage();
				setPage();
			}
		});

		lastPageButton.addMouseListener(new MouseAdapter() {
			/**
			 * ��תβҳ
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
			 * �����ݴ����������������һ���Լ������ݿ�
			 */
			public void mouseClicked(MouseEvent e) {
				addSalesInfo();
			}
		});

		deleteButton.addMouseListener(new MouseAdapter() {
			/**
			 * ���ɾ����ť��ɾ��ѡ�е�֮ǰ��ӵ��˻���Ϣ
			 */
			public void mouseClicked(MouseEvent e) {
				deleteSalesInfo();
			}
		});

		reImburseButton.addMouseListener(new MouseAdapter() {
			/**
			 * �����ť����������ӵ����ݿ�
			 * 
			 * @param arg0
			 */
			public void mouseClicked(MouseEvent arg0) {
				addSales();
			}
		});

		pressButton.addActionListener(new ActionListener() {
			/**
			 * ��ӡ�˻���
			 */
			public void actionPerformed(ActionEvent arg0) {
				pressSalesReturn();
			}
		});
	}

	/**
	 * �����˻���Ϣ
	 */
	protected void pressSalesReturn() {
		if (salesInfos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�˻����������˻���Ϣ�������²�����");
			return;
		}
		String[] PURCHASE_COLUMN_NAMES = { "�˻�����", "��Ʒ����", "����", "����", "�ܼ�" };
		Object[][] obj = new Object[salesInfos.size() + 3][5];
		obj[0][0] = "���ⵥ��";
		obj[0][1] = "��Ʒ����";
		obj[0][2] = "����";
		obj[0][3] = "����";
		obj[0][4] = "�ܼ�";
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
		obj[i][3] = "    ��   �ƣ�";
		obj[i][4] = srMoneyTextField.getText();
		obj[i + 1][0] = srDateComboBox.getDate().toLocaleString();
		obj[i + 1][1] = null;
		obj[i + 1][2] = null;
		obj[i + 1][3] = "   �Ƶ��ˣ�";
		String maker = userService.queryEmployee(currentUser).getEname();
		obj[i + 1][4] = maker;
		JTable jtable = new JTable();
		jtable.setTableHeader(new JTableHeader());
		jtable.setModel(new DefaultTableModel(obj, new String[] { "", "", "�˻���", "", "", "" }));
		new OutExcel(jtable);
	}

	/**
	 * ɾ���˻���Ϣ
	 */
	protected void deleteSalesInfo() {
		if (srTable.getSelectedColumn() < 0 && srTable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "����ѡ��Ҫɾ�����˻�������Ϣ��");
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
			JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
			return;
		} else {
			JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
			return;
		}
	}

	/**
	 * �����˻�����Ŀ������salesInfos��ȥ
	 */
	protected void addSalesInfo() {
		if (srTable.getSelectedColumn() < 0 && srTable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "����ѡ��Ҫ�˻���������Ϣ��");
			return;
		}
		int snum = Integer.parseInt(srTable.getModel().getValueAt(srTable.getSelectedRow(), 9).toString());
		if (snum == 0) {
			JOptionPane.showMessageDialog(null, "�����趨�˻���������");
			return;
		}
		long salesid = (long) srTable.getModel().getValueAt(srTable.getSelectedRow(), 0);
		int gid = (int) srTable.getModel().getValueAt(srTable.getSelectedRow(), 1);
		SalesInfo salesInfo = salesInfoService.queryBySalesidAndGid(salesid, gid);// ��ѯ������Ϣ
		int salesOutNum = salesInfo.getSnum();// �۳�������
		salesInfo.setSalesid(0 - salesid);
		salesInfo.setSnum(0 - snum);
		boolean flag = false;
		SalesInfo salesRe = salesInfoService.queryBySalesidAndGid(0 - salesid, gid);
		StringBuffer sb = new StringBuffer("");
		if (salesRe != null) {
			sb.append(salesid + "�����ܹ��۳�" + salesOutNum + ",֮ǰ�˻�" + (0 - salesRe.getSnum()) + ",�����˻�" + snum);
			salesOutNum = (0 - salesRe.getSnum()) + salesOutNum;
		}
		if (salesInfo.getSnum() > salesOutNum) {
			JOptionPane.showMessageDialog(null, sb + "�˻��������ܱ����۵������࣡");
			return;
		}
		for (SalesInfo si : salesInfos) {
			if (si.getSalesid() != (0 - salesid)) {
				JOptionPane.showMessageDialog(null, "��ѡ������֮ǰ���Ų�ͬ�����ܲ��������ȴ�����һ�����۵���");
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
			JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
		} else {
			salesInfos.add(salesInfo);
			JOptionPane.showMessageDialog(null, "��ӳɹ�");
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
	 * ��ӳ����������ݿ�
	 */
	public void addSales() {
		String salesReturnId = srSalesTextField.getText();// �˻���Ϣ����
		if (salesReturnId.isEmpty() || "".equals(salesReturnId)) {
			JOptionPane.showMessageDialog(null, "����ѡ��Ҫ�˻������۵��ţ�");
			return;
		}
		long salesId = 0 - Long.parseLong(salesReturnId);// ������Ϣ����
		Sales sa = salesService.querySalesID(salesId);
		Date sdate = new Date(srDateComboBox.getDate().getTime());
		if (sa.getSdate().after(sdate)) {
			JOptionPane.showMessageDialog(null, "�˻������ڱ������۳�����֮��");
			return;
		}
		String ename = (String) srUserdcbm.getSelectedItem();
		if (!ename.equals(userService.queryEmployee(currentUser).getEname())) {
			List<Employee> employeeList = employeeService.queryAll();
			for (Employee employee : employeeList) {
				if (ename.equals(employee.getEname())) {
					currentUser = userService.queryByEid(employee.getEid());// ����ȷ����ǰ�Ʊ���
					String password = JOptionPane.showInputDialog(null, "���룺", "������" + ename + "�û�����",
							JOptionPane.QUESTION_MESSAGE);
					if (!currentUser.getPassword().equals(password)) {
						JOptionPane.showMessageDialog(null, "������������²���");
						return;
					}
				}
			}
		}
		int mid = sa.getMid();
		int eid = currentUser.getEid();
		double discount = Double.parseDouble(memberService.queryDiscount(memberGradeLabel.getText()));// ��ȡ��Ա�ۿ�
		double mgathering = Math.copySign(Double.parseDouble(srMoneyTextField.getText()) * discount, 0.00);
		String remark = srRemarkTextArea.getText();
		Sales salesReturn = new Sales(Long.parseLong(salesReturnId), eid, mid, sdate, mgathering, remark);
		Sales salesIn = salesService.querySalesID(Long.parseLong(salesReturnId));
		if (salesService.addSales(salesReturn, salesInfos)) {
			JOptionPane.showMessageDialog(null, "�˿�ɹ���");
			showagain();
		} else {
			JOptionPane.showMessageDialog(null, "�˿�ʧ�ܣ�");
		}
	}

	/**
	 * ˢ��ҳ��
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
	 * ���Ų�����̬����ҳ��
	 */
	public void setPage() {
		notePageLabel.setText("��ǰҳ��" + curPage + "/" + maxPage);
	}

	/**
	 * ��ת�����һҳ
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
	 * ��ת�����һҳ
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
	 * �����Ƶ��˿ؼ�����
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
	 * ����ѡ�г������ŵĲ�ͬ����̬���ػ�Ա��Ϣ
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
	 * ����salesid�˻����������۵��ž���,�������۵��Ÿ��������۵�������򣬷����ѯ
	 */
	public void setsalesid() {
		if (srTable.getSelectedColumn() >= 0 || srTable.getSelectedRow() >= 0) {
			salesId = 0 - (Long) srTable.getModel().getValueAt(srTable.getSelectedRow(), 0);
			srSalesTextField.setText(salesId + "");
			srStockTextField.setText((0 - salesId) + "");
		}
	}

	/**
	 * �����������ڵ��ı�����ˢ��ҳ��
	 */
	public void showSales() {
		try {
			String sidStr = srStockTextField.getText();
			if (sidStr.isEmpty() || "".equals(sidStr)) {
				srTable.showagain(1, 0);// Ĭ��չʾ��һҳ
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
			JOptionPane.showMessageDialog(null, "ϵͳ��æ�����Ժ����ԣ�");
		}
	}
}
