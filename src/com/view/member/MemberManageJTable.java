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

	private static final String[] COLUMN_NAMES = { "\u4F1A\u5458id", "��Ա����", "\u4F1A\u5458\u59D3\u540D", "\u6027\u522B",
			"\u624B\u673A", "\u4F1A\u5458\u5DF2\u6D88\u8D39\u91D1\u989D", "\u8D26\u6237\u4F59\u989D",
			"\u4F1A\u5458\u7B49\u7EA7" };
	private List<Member> memberList;
	private List<Boolean> ori;// �ж������ǲ������ݿ��е�
	private List<Boolean> updated;// �ж��ǲ����޸Ĺ�
	private List<Integer> ids;// �洢���id
	private List<String> listMname; // �洢��Ա����
	private Object[][] o;
	private MemberService memberService = new MemberServiceImp();

	public MemberManageJTable(List<Member> memberList) {
		initialize(memberList);
	}

	/**
	 * ���ر������
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
		for (Member member : memberList) {// �����ݱ��浽������
			o[i][0] = member.getMid();
			o[i][1] = member.getMpassword();
			//passwordF.setText(member.getMpassword());
			o[i][2] = member.getMname();
			o[i][3] = member.getMsex();
			o[i][4] = member.getMtel();
			o[i][5] = member.getMmonetary();
			o[i][6] = member.getMaccount();
			o[i][7] = member.getMgrade();

			ori.add(true);// ���������ݿ���
			updated.add(false);// û���޸Ĺ�
			ids.add(member.getMid());// �õ����
			listMname.add(member.getMname());// �õ�����
			++i;
		}
		this.setModel(new DefaultTableModel(o, COLUMN_NAMES));
		((DefaultTableModel) this.getModel()).addTableModelListener(new TableModelListener() {// �ж������ǲ��Ǳ��޸Ĺ�
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
		sexComboBox.addItem("��");
		sexComboBox.addItem("Ů");
		sexColumn.setCellEditor(new DefaultCellEditor(sexComboBox));
		
		gradeColumn = getColumnModel().getColumn(7);
		gradeComboBox = new JComboBox();
		gradeComboBox.addItem("��ͨ��Ա");
		gradeComboBox.addItem("�ƽ��Ա");
		gradeComboBox.addItem("�����Ա");
		gradeComboBox.addItem("��ʯ��Ա");
		gradeComboBox.addItem("�����Ա");
		gradeColumn.setCellEditor(new DefaultCellEditor(gradeComboBox));

	}

	/**
	 * jtable �������
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
	 * ɾ��ĳһ��
	 * 
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteRow() {
		int si = this.getSelectedRow();// ���ȵõ�������һ��
		boolean flag = memberService.deleteById(ids.get(si));
		if (flag) {
			((DefaultTableModel) this.getModel()).removeRow(si);
			updated.remove(si);
			listMname.remove(si);
			ori.remove(si);
			ids.remove(si);// ȥ�����е���Ϣ
		}
		return flag;
	}

	/**
	 * ���һ������
	 */
	public void addRow() {
		((DefaultTableModel) this.getModel()).addRow(new Object[10]);// �������
		ori.add(false);
		updated.add(false);
		listMname.add(null);
		ids.add(null);
	}

	/**
	 * �����޸�
	 * 
	 * @return �Ƿ񱣴�ɹ�
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
	 * ��������
	 * 
	 * @param i
	 */
	private boolean setProperty(int i) {
		String id = getValueAt(i, 0).toString();
		if((!StringUtil.isNumber(id)) || id.length()>10) {
			JOptionPane.showMessageDialog(null, "��������ȷ�Ļ�Աid");
			return true;
		}
		mid = Integer.parseInt(id.trim());
		
		
		mpassword = (String) getValueAt(i, 1);
		if (StringUtil.isEmpty(mpassword) || mpassword.length() >10||mpassword.length()<6) {
			JOptionPane.showMessageDialog(null, "��Ա���벻��Ϊ��,�ҳ��ȱ���Ϊ6��10֮��");
			return true;
		}
		mpassword = mpassword.trim();

		
		mname = (String) getValueAt(i, 2);
		if (StringUtil.isEmpty(mname) || mname.length()>25) {
			JOptionPane.showMessageDialog(null, "��Ա��������Ϊ�գ��ҳ��Ȳ��ܴ���25");
			return true;
		}
		mname = mname.trim();

		msex = getValueAt(i, 3).toString();

		
		String tel = getValueAt(i, 4).toString();
		if (!StringUtil.isTel(tel)) {
			JOptionPane.showMessageDialog(null, "�ֻ������ʽ����ȷ");
			return true;
		}
		mtel = Long.parseLong(tel.trim());

		
		String monetary = getValueAt(i, 5).toString();
		if (!StringUtil.isPrice(monetary)) {
			JOptionPane.showMessageDialog(null, "�����ѽ���ʽ����ȷ�����10λ����ֻ����2λС��");
			return true;
		}
		mmonetary = Double.parseDouble(monetary.trim());

		
		String account = getValueAt(i, 6).toString();
		if (!StringUtil.isPrice(account)) {
			JOptionPane.showMessageDialog(null, "�˻�����ʽ����ȷ�����10λ����ֻ����2λС��");
			return true;
		}
		maccount = Double.parseDouble(account.trim());

		mgrade = getValueAt(i, 7).toString();
		return false;

	}

}
