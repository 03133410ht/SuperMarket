package com.view.member;

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

import com.entity.Member;
import com.service.MemberService;
import com.service.imp.MemberServiceImp;
import com.util.StringUtil;

public class MemberManageJTable extends JTable {
	private int mid;
	private String mname;
	private String msex;
	private String mgrade;
	private double mmonetary;
	private long mtel;
	private double maccount;
	private String mpassword;
	private TableColumn sexColumn;
	private JComboBox sexComboBox;
	private TableColumn gradeColumn;
	private JComboBox gradeComboBox;
	private TableColumn passwordColumn;
	private JPasswordField  passwordF;

	private static final String[] COLUMN_NAMES = { "\u4F1A\u5458id", "会员密码", "\u4F1A\u5458\u59D3\u540D", "\u6027\u522B",
			"\u624B\u673A", "\u4F1A\u5458\u5DF2\u6D88\u8D39\u91D1\u989D", "\u8D26\u6237\u4F59\u989D",
			"\u4F1A\u5458\u7B49\u7EA7" };
	private List<Member> memberList;
	private List<Boolean> ori;// 判断这行是不是数据库中的
	private List<Boolean> updated;// 判断是不是修改过
	private List<Integer> ids;// 存储编号id
	private List<String> listMname; // 存储会员名称
	private Object[][] o;
	private MemberService memberService = new MemberServiceImp();

	public MemberManageJTable(List<Member> memberList) {
		initialize(memberList);
	}

	/**
	 * 加载表格内容
	 * 
	 * @param list
	 */
	private void initialize(List<Member> memberList) {
		this.memberList = memberList;
		this.ori = new ArrayList<Boolean>();
		this.updated = new ArrayList<Boolean>();
		this.ids = new ArrayList<Integer>();
		this.listMname = new ArrayList<String>();
		o = new Object[memberList.size()][8];
		passwordF =new JPasswordField();
		int i = 0;
		for (Member member : memberList) {// 将数据保存到数组中
			o[i][0] = member.getMid();
			o[i][1] = member.getMpassword();
			//passwordF.setText(member.getMpassword());
			o[i][2] = member.getMname();
			o[i][3] = member.getMsex();
			o[i][4] = member.getMtel();
			o[i][5] = member.getMmonetary();
			o[i][6] = member.getMaccount();
			o[i][7] = member.getMgrade();

			ori.add(true);// 这行在数据库中
			updated.add(false);// 没有修改过
			ids.add(member.getMid());// 得到编号
			listMname.add(member.getMname());// 得到名字
			++i;
		}
		this.setModel(new DefaultTableModel(o, COLUMN_NAMES));
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
		
		
		getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(passwordF));
		getColumnModel().getColumn(1).setCellRenderer(new PasswordCellRenderer());
		
		sexColumn = getColumnModel().getColumn(3);
		sexComboBox = new JComboBox();
		sexComboBox.addItem("男");
		sexComboBox.addItem("女");
		sexColumn.setCellEditor(new DefaultCellEditor(sexComboBox));
		
		gradeColumn = getColumnModel().getColumn(7);
		gradeComboBox = new JComboBox();
		gradeComboBox.addItem("普通会员");
		gradeComboBox.addItem("黄金会员");
		gradeComboBox.addItem("铂金会员");
		gradeComboBox.addItem("钻石会员");
		gradeComboBox.addItem("至尊会员");
		gradeColumn.setCellEditor(new DefaultCellEditor(gradeComboBox));

	}

	/**
	 * jtable 下密码框
	 * 
	 * @author Goddard
	 *
	 */
	class PasswordCellRenderer extends JPasswordField
	implements TableCellRenderer {

	    public PasswordCellRenderer() {
	        super();
	        this.setText("filler123");
	    }

	    public Component getTableCellRendererComponent(
	    JTable arg0,
	    Object arg1,
	    boolean arg2,
	    boolean arg3,
	    int arg4,
	    int arg5) {
	        return this;
	    }
	}
	public void setList(List<Member> memberList) {
		initialize( memberList);
	}

	public static String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	public List<Boolean> getOri() {
		return ori;
	}

	public List<Boolean> getUpdated() {
		return updated;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public List<String> getListMname() {
		return listMname;
	}

	public Object[][] getO() {
		return o;
	}

	/**
	 * 删除某一行
	 * 
	 * @return 是否删除成功
	 */
	public boolean deleteRow() {
		int si = this.getSelectedRow();// 首先得到这是哪一行
		boolean flag = memberService.deleteById(ids.get(si));
		if (flag) {
			((DefaultTableModel) this.getModel()).removeRow(si);
			updated.remove(si);
			listMname.remove(si);
			ori.remove(si);
			ids.remove(si);// 去掉所有的信息
		}
		return flag;
	}

	/**
	 * 添加一个空行
	 */
	public void addRow() {
		((DefaultTableModel) this.getModel()).addRow(new Object[10]);// 加入空行
		ori.add(false);
		updated.add(false);
		listMname.add(null);
		ids.add(null);
	}

	/**
	 * 保存修改
	 * 
	 * @return 是否保存成功
	 */
	public boolean save() {
		boolean flag = false;
		for (int i = 0; i < ori.size(); i++) {
			if (!ori.get(i)) {
				if(setProperty(i)) {
					return flag;
				}
				flag = memberService
						.addMember(new Member(mid, mname, msex, mgrade, mmonetary, mtel, maccount, mpassword));

			}
			if (updated.get(i)) {
				if(setProperty(i)) {
					return flag;
				}
				flag = memberService.updateMember(ids.get(i),
						new Member(mid, mname, msex, mgrade, mmonetary, mtel, maccount, mpassword));
			}
		}
		return flag;
	}

	/**
	 * 设置属性
	 * 
	 * @param i
	 */
	private boolean setProperty(int i) {
		String id = getValueAt(i, 0).toString();
		if((!StringUtil.isNumber(id)) || id.length()>10) {
			JOptionPane.showMessageDialog(null, "请输入正确的会员id");
			return true;
		}
		mid = Integer.parseInt(id.trim());
		
		
		mpassword = (String) getValueAt(i, 1);
		if (StringUtil.isEmpty(mpassword) || mpassword.length() >10||mpassword.length()<6) {
			JOptionPane.showMessageDialog(null, "会员密码不能为空,且长度必须为6到10之间");
			return true;
		}
		mpassword = mpassword.trim();

		
		mname = (String) getValueAt(i, 2);
		if (StringUtil.isEmpty(mname) || mname.length()>25) {
			JOptionPane.showMessageDialog(null, "会员姓名不能为空，且长度不能大于25");
			return true;
		}
		mname = mname.trim();

		msex = getValueAt(i, 3).toString();

		
		String tel = getValueAt(i, 4).toString();
		if (!StringUtil.isTel(tel)) {
			JOptionPane.showMessageDialog(null, "手机号码格式不正确");
			return true;
		}
		mtel = Long.parseLong(tel.trim());

		
		String monetary = getValueAt(i, 5).toString();
		if (!StringUtil.isPrice(monetary)) {
			JOptionPane.showMessageDialog(null, "以消费金额格式不正确，最多10位数，只能有2位小数");
			return true;
		}
		mmonetary = Double.parseDouble(monetary.trim());

		
		String account = getValueAt(i, 6).toString();
		if (!StringUtil.isPrice(account)) {
			JOptionPane.showMessageDialog(null, "账户余额格式不正确，最多10位数，只能有2位小数");
			return true;
		}
		maccount = Double.parseDouble(account.trim());

		mgrade = getValueAt(i, 7).toString();
		return false;

	}

}
