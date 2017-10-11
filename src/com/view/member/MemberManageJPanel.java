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
 * 会员管理JPanel
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
		memberQuery.addElement("全部会员");
		memberQuery.addElement("会员id");
		memberQuery.addElement("会员等级");
		memberQuery.addElement("会员姓名");
		memberQuery.addElement("会员电话");

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
		String [] gradeArray = {"全部会员","普通会员","黄金会员","铂金会员","钻石会员","至尊会员"};
		gradeComboBox = new JComboBox(gradeArray);
		gradeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				query();
			}
		});
		
		gradeComboBox.setBounds(106, 38, 108, 24);
		add(gradeComboBox);
		String [] sexArray = {"全部会员","男","女"};
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
	 * 删除事件处理
	 * @param e
	 */
	private  void deleteMemberActionPerformed(ActionEvent e) {
		int si = mmJTable.getSelectedRow();
		if (si < 0) {
			JOptionPane.showMessageDialog(null, "请选择一行再进行删除");
			return;
		} else {
			if (JOptionPane.showConfirmDialog(null, "是否删除", "删除", JOptionPane.YES_NO_OPTION) == 0) {
				if (mmJTable.deleteRow()) {
					JOptionPane.showMessageDialog(null, "删除成功");
					query();
				} else {
					JOptionPane.showMessageDialog(null, "删除失败");
				}
			}
		}
	}

	/**
	 * 保存事件处理
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
					JOptionPane.showMessageDialog(null, "请输入正确的会员id");
					return;
				}
				
				if ((!memberService.queryByid(id+"").isEmpty()) && (!mmJTable.getIds().get(i).toString().equals(id))) {
					JOptionPane.showMessageDialog(null, id + "  会员id已经存在，请重新修改");
					return;
				} 
			}
		}
		if (updated.isEmpty() || falg) {
			JOptionPane.showMessageDialog(null, "请修改后再保存");
			return;
		}

		if (mmJTable.save()) {
			JOptionPane.showMessageDialog(null, "保存成功");
			query();
		} else {
			JOptionPane.showMessageDialog(null, "保存失败,该会员记录下有销售记录无法修改id");
		}
		
	}

	/**
	 * 搜索事件
	 */
	private void query() {
		String mgrade = gradeComboBox.getSelectedItem().toString();
		if(mgrade.equals("全部会员")) {
			mgrade = "会员";
		}
		String msex = sexComboBox.getSelectedItem().toString();
		if(msex.equals("全部会员")) {
			msex = "";
		}
		String fuzzy = mmFiltrationTextField.getText();
		mmJTable.setList(memberService.fuzzyQuery(fuzzy, msex, mgrade));
		
	}
}
