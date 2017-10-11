package com.view.member;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.entity.Member;
import com.service.MemberService;
import com.service.imp.MemberServiceImp;
import com.util.OutExcel;
import com.util.StringUtil;
/**
 * ��Ա����JPanel
 * @author Goddard
 *
 */
public class MemberManageJPanel extends JPanel {
	private JTextField mmFiltrationTextField;
	private MemberManageJTable mmJTable;
	private JComboBox gradeComboBox;
	private JComboBox sexComboBox;
	private MemberService memberService =new MemberServiceImp();	

	
	public MemberManageJPanel() {
		memberManage();
	}

	private void memberManage() {
		JLabel mmGradeLabel = new JLabel("\u4F1A\u5458\u7B49\u7EA7");
		mmGradeLabel.setBounds(29, 41, 63, 18);

		mmFiltrationTextField = new JTextField();
		mmFiltrationTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				query();
			}
		});
		mmFiltrationTextField.setBounds(483, 38, 125, 24);
		mmFiltrationTextField.setColumns(10);

		JButton mmSearchButton = new JButton("\u641C\u7D22");
		mmSearchButton.setBounds(637, 37, 63, 27);
		mmSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				query();
			}
		});

		JButton mmSaveButton = new JButton("\u4FDD\u5B58");
		mmSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveActionPerformed(e);
			}
		});
		mmSaveButton.setBounds(758, 37, 96, 27);

		JButton mmDeleteButton = new JButton("\u5220\u9664");
		mmDeleteButton.setBounds(911, 37, 96, 27);
		mmDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMemberActionPerformed(e);
			}
		});

		JButton mmExcelButton = new JButton("\u5BFC\u51FAexcel");
		mmExcelButton.setBounds(1068, 37, 96, 27);
		mmExcelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OutExcel(mmJTable);
			}
		});

		DefaultComboBoxModel memberQuery = new DefaultComboBoxModel();
		memberQuery.addElement("ȫ����Ա");
		memberQuery.addElement("��Աid");
		memberQuery.addElement("��Ա�ȼ�");
		memberQuery.addElement("��Ա����");
		memberQuery.addElement("��Ա�绰");

		JScrollPane mmScrollPane = new JScrollPane();
		mmScrollPane.setBounds(29, 107, 1254, 383);

		mmJTable = new MemberManageJTable(new ArrayList<Member>());
		setLayout(null);
		mmScrollPane.setViewportView(mmJTable);
		add(mmScrollPane);
		add(mmGradeLabel);
		add(mmFiltrationTextField);
		add(mmSearchButton);
		add(mmSaveButton);
		add(mmDeleteButton);
		add(mmExcelButton);
		String [] gradeArray = {"ȫ����Ա","��ͨ��Ա","�ƽ��Ա","�����Ա","��ʯ��Ա","�����Ա"};
		gradeComboBox = new JComboBox(gradeArray);
		gradeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				query();
			}
		});
		
		gradeComboBox.setBounds(106, 38, 108, 24);
		add(gradeComboBox);
		String [] sexArray = {"ȫ����Ա","��","Ů"};
		sexComboBox = new JComboBox(sexArray);
		sexComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				query();
			}
		});
		
		sexComboBox.setBounds(333, 38, 108, 24);
		add(sexComboBox);
		
		JLabel sexLabel = new JLabel("\u4F1A\u5458\u6027\u522B");
		sexLabel.setBounds(247, 41, 72, 18);
		add(sexLabel);
	}
	
	/**
	 * ɾ���¼�����
	 * @param e
	 */
	private  void deleteMemberActionPerformed(ActionEvent e) {
		int si = mmJTable.getSelectedRow();
		if (si < 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��һ���ٽ���ɾ��");
			return;
		} else {
			if (JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ��", "ɾ��", JOptionPane.YES_NO_OPTION) == 0) {
				if (mmJTable.deleteRow()) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
					query();
				} else {
					JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
				}
			}
		}
	}

	/**
	 * �����¼�����
	 * @param e
	 */
	private  void saveActionPerformed(ActionEvent e) {
		List<Boolean> updated = mmJTable.getUpdated();
		boolean falg = true;
		String  id = "";
		String name = null;
		for (int i = 0; i < updated.size(); i++) {
			if (updated.get(i)) {
				falg = false;
				id = mmJTable.getModel().getValueAt(i, 0).toString();
				if((!StringUtil.isNumber(id) || id.length()>10)) {
					JOptionPane.showMessageDialog(null, "��������ȷ�Ļ�Աid");
					return;
				}
				
				if ((!memberService.queryByid(id+"").isEmpty()) && (!mmJTable.getIds().get(i).toString().equals(id))) {
					JOptionPane.showMessageDialog(null, id + "  ��Աid�Ѿ����ڣ��������޸�");
					return;
				} 
			}
		}
		if (updated.isEmpty() || falg) {
			JOptionPane.showMessageDialog(null, "���޸ĺ��ٱ���");
			return;
		}

		if (mmJTable.save()) {
			JOptionPane.showMessageDialog(null, "����ɹ�");
			query();
		} else {
			JOptionPane.showMessageDialog(null, "����ʧ��,�û�Ա��¼�������ۼ�¼�޷��޸�id");
		}
		
	}

	/**
	 * �����¼�
	 */
	private void query() {
		String mgrade = gradeComboBox.getSelectedItem().toString();
		if(mgrade.equals("ȫ����Ա")) {
			mgrade = "��Ա";
		}
		String msex = sexComboBox.getSelectedItem().toString();
		if(msex.equals("ȫ����Ա")) {
			msex = "";
		}
		String fuzzy = mmFiltrationTextField.getText();
		mmJTable.setList(memberService.fuzzyQuery(fuzzy, msex, mgrade));
		
	}
}
