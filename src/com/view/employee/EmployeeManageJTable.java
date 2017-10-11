package com.view.employee;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.entity.EmployeeUser;
import com.entity.Member;
import com.entity.User;
import com.service.EmployeeService;
import com.service.imp.EmployeeServiceImp;
import com.util.StringUtil;

/**
 * 员工管理表格
 * 
 * @author Goddard
 *
 */
public class EmployeeManageJTable extends JTable {
	private static final String[] COLUMN_NAMES = { "\u5458\u5DE5id", "\u59D3\u540D", "\u6027\u522B", "\u5730\u5740",
			"\u7535\u8BDD", "\u90AE\u7BB1\u5730\u5740", "\u8D26\u6237", "\u5BC6\u7801", "账户类型" };

	private List<Boolean> ori;// 判断这行是不是数据库中的
	private List<Boolean> updated;// 判断是不是修改过
	private List<Integer> ids;// 存储编号id
	private List<EmployeeUser> employeeUserList;
	private DefaultTableModel defaultTableModel;
	private JPasswordField passwordF;
	private JComboBox sexComboBox;
	private TableColumn sexColumn;
	private TableColumn accountColumn;
	private JComboBox accountTypeComboBox;
	private EmployeeService employeeService = new EmployeeServiceImp();
	private int eid;
	private String ename;
	private String esex;
	private String eadress;
	private long etel;
	private String email;
	private String username; // 帐号
	private String password; // 密码
	private String usertype; // 用户类型

	public EmployeeManageJTable(List<EmployeeUser> employeeUserList, User curUser) {
		employeeManage(employeeUserList, curUser);
	}

	

	public List<Boolean> getUpdated() {
		return updated;
	}



	private void employeeManage(List<EmployeeUser> employeeUserList, User curUser) {
		if (employeeUserList.isEmpty()) {
			return;
		}
		this.ori = new ArrayList<Boolean>();
		this.updated = new ArrayList<Boolean>();
		this.ids = new ArrayList<Integer>();
		this.employeeUserList = employeeUserList;
		passwordF = new JPasswordField();
		defaultTableModel = new DefaultTableModel(new Object[][] {}, COLUMN_NAMES) {
			boolean[] columnEditables = new boolean[] {false, true, true, true, true, true, true, true, true };
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		this.setModel(defaultTableModel);
		((DefaultTableModel) this.getModel()).addTableModelListener(new TableModelListener() {// 判断这行是不是被修改过
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					updated.set(e.getLastRow(), true);
				}
			}

		});
		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(0).setResizable(false);
		getColumnModel().getColumn(1).setResizable(false);
		getColumnModel().getColumn(2).setResizable(false);
		getColumnModel().getColumn(3).setResizable(false);
		getColumnModel().getColumn(4).setResizable(false);
		getColumnModel().getColumn(5).setResizable(false);
		getColumnModel().getColumn(6).setResizable(false);
		getColumnModel().getColumn(7).setResizable(false);
		getColumnModel().getColumn(8).setResizable(false);

		getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(passwordF));
		getColumnModel().getColumn(7).setCellRenderer(new PasswordCellRenderer());
		for (EmployeeUser eu : employeeUserList) {
			if (curUser.getSid() != 10000 && eu.getUsertype().equals("admin")) {
				continue;
			}
			if(eu.getEid() == 10000) {
				continue;
			}
			defaultTableModel.addRow(new Object[] { eu.getEid(), eu.getEname(), eu.getEsex(), eu.getEadress(),
					eu.getEtel(), eu.getEmail(), eu.getUsername(), eu.getPassword(), eu.getUsertype() });
			ori.add(true);// 这行在数据库中
			updated.add(false);// 没有修改过
			ids.add(eu.getEid());// 得到编号
		}
		sexColumn = getColumnModel().getColumn(2);
		sexComboBox = new JComboBox();
		sexComboBox.addItem("男");
		sexComboBox.addItem("女");
		sexColumn.setCellEditor(new DefaultCellEditor(sexComboBox));

		accountColumn = getColumnModel().getColumn(8);
		accountTypeComboBox = new JComboBox();
		accountTypeComboBox.addItem("admin");
		accountTypeComboBox.addItem("employee");
		accountColumn.setCellEditor(new DefaultCellEditor(accountTypeComboBox));
	}

	/**
	 * 设置数据
	 * 
	 * @param employeeUserList
	 */
	public void setList(List<EmployeeUser> employeeUserList, User curUser) {
		employeeManage(employeeUserList, curUser);
	}

	/**
	 * jtable 下密码框
	 * 
	 * @author Goddard
	 *
	 */
	class PasswordCellRenderer extends JPasswordField implements TableCellRenderer {

		public PasswordCellRenderer() {
			super();
			this.setText("filler123");
		}

		public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
				int arg5) {
			return this;
		}
	}

	/**
	 * 删除某一行
	 * 
	 * @return 是否删除成功
	 */
	public boolean deleteRow() {
		int si = this.getSelectedRow();// 首先得到这是哪一行
		boolean flag = employeeService.deleteByEid(ids.get(si));
		if (flag) {
			((DefaultTableModel) this.getModel()).removeRow(si);
			updated.remove(si);
			ori.remove(si);
			ids.remove(si);// 去掉所有的信息
		}
		return flag;
	}

	/**
	 * 保存修改
	 * 
	 * @return 是否保存成功
	 */
	public boolean save() {
		boolean flag = false;
		for (int i = 0; i < ori.size(); i++) {
			/*if (!ori.get(i)) {
				if (setProperty(i)) {
					return flag;
				}
				flag = employeeService.updateEmployeeUser(new EmployeeUser(eid, ename, esex, eadress, etel, email, username, password, usertype));
			}*/
			if (updated.get(i)) {
				if(!setProperty(i)) {
					return flag;
				}
				flag = employeeService.updateEmployeeUser(new EmployeeUser(eid, ename, esex, eadress, etel, email, username, password, usertype));
			}
			
		}
		return flag;
	}

	/**
	 * 设置属性
	 * @param i
	 * @return
	 */
	private boolean setProperty(int i) {
		boolean flag = true;
		try {
			eid = Integer.parseInt(getModel().getValueAt(i, 0).toString());
			ename = getModel().getValueAt(i, 1).toString();
			esex = getModel().getValueAt(i, 2).toString();
			eadress = getModel().getValueAt(i, 3).toString();
			etel = Long.parseLong(getModel().getValueAt(i, 4).toString());
			if(!StringUtil.isTel(getModel().getValueAt(i, 4).toString())) {
				JOptionPane.showMessageDialog(null, "手机不符合格式");
				return false;
			}
			email = getModel().getValueAt(i, 5).toString();
			if(!StringUtil.isEmail(email)) {
				JOptionPane.showMessageDialog(null, "邮箱不符合格式");
				return false;
			}
			username = getModel().getValueAt(i, 6).toString();
			if(username.length()>10) {
				JOptionPane.showMessageDialog(null, "账户名称必须小于10位");
				return false;
			}
			password = getModel().getValueAt(i, 7).toString();
			if(password.length()>10 ||password.length()<6) {
				JOptionPane.showMessageDialog(null, "密码必须为6-10 位");
				return false;
			}
			usertype = getModel().getValueAt(i, 8).toString();	
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "请输入正确的格式");
			flag = false;
		}
		return flag;
	}
}
