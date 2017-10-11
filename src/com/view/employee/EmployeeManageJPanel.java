package com.view.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.entity.EmployeeUser;
import com.entity.User;
import com.service.EmployeeService;
import com.service.imp.EmployeeServiceImp;
import com.util.KeyTypeUtil;
import com.util.OutExcel;
/**
 * Ա������JPanel
 * @author Goddard
 *
 */
public class EmployeeManageJPanel extends JPanel {
	private JTextField emFiltrationTextField;
	private EmployeeManageJTable emJTable;
	private EmployeeService employeeService = new EmployeeServiceImp();
	private User currentUser;
	
	public EmployeeManageJPanel(User currentUser) {
		this.currentUser = currentUser;
		employeeManage();
	}

	private void employeeManage() {

		emFiltrationTextField = new JTextField();
		emFiltrationTextField.setBounds(117, 31, 190, 36);
		emFiltrationTextField.setColumns(10);

		JButton emSearchButton = new JButton("\u641C\u7D22");
		emSearchButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(KeyTypeUtil.pressEnter(e)) {
					searchActionPerformed();
				}
			}
		});
		emSearchButton.setFont(new Font("����", Font.BOLD, 16));
		emSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchActionPerformed();
			}
		});
		emSearchButton.setBounds(367, 30, 86, 36);

		JButton emSaveButton = new JButton("\u4FDD\u5B58");
		emSaveButton.setFont(new Font("����", Font.BOLD, 16));
		emSaveButton.setForeground(Color.BLACK);
		emSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePerformed();
			}
		});
		emSaveButton.setBounds(571, 30, 86, 36);

		JButton emDeleteButton = new JButton("\u5220\u9664");
		emDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteActonPerformed();
			}
		});
		emDeleteButton.setForeground(Color.BLACK);
		emDeleteButton.setFont(new Font("����", Font.BOLD, 16));
		emDeleteButton.setBounds(705, 30, 86, 36);

		JButton emExcelButton = new JButton("\u5BFC\u51FAexcel");
		emExcelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 new OutExcel(emJTable);
			}
		});
		emExcelButton.setFont(new Font("����", Font.BOLD, 16));
		emExcelButton.setForeground(Color.BLACK);
		emExcelButton.setBounds(832, 30, 112, 36);

		JScrollPane emScrollPane = new JScrollPane();
		emScrollPane.setBounds(30, 101, 1235, 404);

		emJTable = new EmployeeManageJTable(new ArrayList<EmployeeUser>(),currentUser);
		
		setLayout(null);
		emScrollPane.setViewportView(emJTable);
		add(emScrollPane);
		add(emFiltrationTextField);
		add(emSearchButton);
		add(emSaveButton);
		add(emDeleteButton);
		add(emExcelButton);
	}
	/**
	 * �����¼�����
	 */
	private  void savePerformed() {
		List<Boolean> isUpdate = emJTable.getUpdated();
		boolean flag = false;
		for(boolean b:isUpdate) {
			if(b) {
				flag = true;
				break;
			}
		}
		if(flag) {
			if(emJTable.save()) {
				JOptionPane.showMessageDialog(null, "����ɹ�");
			}else {
				JOptionPane.showMessageDialog(null, "����ʧ��");
			}
		}else {
			JOptionPane.showMessageDialog(null, "���޸ĺ��ٱ���");
		}
		
	}

	/**
	 * ɾ���¼�����
	 */
	private void deleteActonPerformed() {
		if(emJTable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "����ѡ��һ����ɾ��");
			return;
		}else {
			if (JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ��", "ɾ��", JOptionPane.YES_NO_OPTION) == 0) {
				if (emJTable.deleteRow()) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
					searchActionPerformed();
				} else {
					JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
				}
			}
		}
	}

	/**
	 * �����¼�����
	 */
	private  void searchActionPerformed() {
		List<EmployeeUser> employeeUser = employeeService.fuzzyQuery(emFiltrationTextField.getText());
		if(employeeUser.isEmpty()) {
			JOptionPane.showMessageDialog(null, "û���ҵ����");
		}else {
			emJTable.setList(employeeUser,currentUser);
		}
	}

}
