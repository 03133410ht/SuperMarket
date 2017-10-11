package com.view.employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.entity.Employee;
import com.entity.User;
import com.service.EmployeeService;
import com.service.imp.EmployeeServiceImp;
import com.util.KeyTypeUtil;
import com.util.StringUtil;
import java.awt.Label;

/**
 * ���Ա��JPanel
 * 
 * @author Goddard
 *
 */
public class AddEmployeeJPanel extends JPanel {
	private JTextField aeEidTextField;
	private JTextField aeTelTextField;
	private JTextField aeAdressTextField;
	private JTextField aeNameTextField;
	private JTextField aeEmailTextField;
	private JTextField aeUsernameTextField;
	private JPasswordField passwordField;
	private JComboBox aeSexComboBox;
	private EmployeeService employeeService = new EmployeeServiceImp();
	private JPanel addEmployee;
	private JLabel eidLabel;
	private JLabel etelLabel;
	private JLabel usernameLabel;
	private JLabel enameNewLabel;
	private JLabel epasswordLabel;
	private JLabel eadressLabel;
	private JLabel emailLabel;
	private User curUser;
	private JComboBox accountTypeComboBox;

	/**
	 * Create the panel.
	 */
	public AddEmployeeJPanel(User curUser) {
		addEmployee();
		this.curUser = curUser;
	}

	private void addEmployee() {
		addEmployee = new JPanel();
		addEmployee.setBounds(126, 0, 1076, 465);
		addEmployee.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u589e\u52a0\u5458\u5de5",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		add(addEmployee);
		loadModule();
		loadButton();

	}

	private void loadModule() {
		loadEidModule();
		loadTelModule();
		loadNameModule();
		loadSexModule();
		loadUsernameModule();
		loadPasswordModule();
		loadAdressModule();
		loadEmailModule();
		setLayout(null);
		addEmployee.setLayout(null);

		JLabel accountTypeLabel = new JLabel("\u8D26\u6237\u7C7B\u578B");
		accountTypeLabel.setFont(new Font("����", Font.PLAIN, 22));
		accountTypeLabel.setBounds(51, 421, 88, 23);
		addEmployee.add(accountTypeLabel);

		String[] at = { "admin", "employee" };
		accountTypeComboBox = new JComboBox(at);
		accountTypeComboBox.setBounds(170, 418, 186, 34);
		addEmployee.add(accountTypeComboBox);

	}

	/**
	 * ��������
	 */
	private void loadEmailModule() {
		JLabel aeEmailLabel = new JLabel("\u90AE\u7BB1");
		aeEmailLabel.setBounds(606, 142, 44, 26);// ����
		aeEmailLabel.setFont(new Font("����", Font.PLAIN, 22));
		emailLabel = new JLabel("\u90AE\u7BB1\u4E0D\u7B26\u5408\u89C4\u5219");
		emailLabel.setForeground(Color.RED);
		emailLabel.setBounds(818, 189, 117, 18);
		emailLabel.setVisible(false);
		addEmployee.add(emailLabel);
		aeEmailTextField = new JTextField();
		aeEmailTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String email = new String(aeEmailTextField.getText());
				if (!StringUtil.isEmpty(email)) {
					if (!StringUtil.isEmail(email)) {
						emailLabel.setVisible(true);
					} else {
						emailLabel.setVisible(false);
					}
				}
			}
		});
		aeEmailTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addEmployeeACtionPerformed();
				}
			}
		});
		aeEmailTextField.setBounds(788, 140, 185, 36);// ���书��
		aeEmailTextField.setColumns(10);

		addEmployee.add(aeEmailLabel);
		addEmployee.add(aeEmailTextField);
	}

	/**
	 * ����סַ
	 */
	private void loadAdressModule() {
		JLabel aeAdressLabel = new JLabel("\u4F4F\u5740");
		aeAdressLabel.setBounds(606, 247, 44, 26);// סַ
		aeAdressLabel.setFont(new Font("����", Font.PLAIN, 22));
		eadressLabel = new JLabel("\u4F4F\u5740\u4E0D\u7B26\u5408\u8BE5\u89C4\u5219");
		eadressLabel.setForeground(Color.RED);
		eadressLabel.setBounds(818, 290, 123, 18);
		eadressLabel.setVisible(false);
		addEmployee.add(eadressLabel);

		aeAdressTextField = new JTextField();
		aeAdressTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String eadress = new String(aeAdressTextField.getText());
				if (!StringUtil.isEmpty(eadress)) {
					if (eadress.length() < 1 || eadress.length() > 50) {
						eadressLabel.setVisible(true);
					} else {
						eadressLabel.setVisible(false);
					}
				}
			}
		});
		aeAdressTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addEmployeeACtionPerformed();
				}
			}
		});
		aeAdressTextField.setBounds(788, 242, 185, 35);// סַ����
		aeAdressTextField.setColumns(10);
		addEmployee.add(aeAdressLabel);
		addEmployee.add(aeAdressTextField);
	}

	/**
	 * ����Ա������
	 */
	private void loadPasswordModule() {
		JLabel aePasswordLabel = new JLabel("\u5BC6\u7801");
		aePasswordLabel.setBounds(606, 341, 44, 26);// ����
		aePasswordLabel.setFont(new Font("����", Font.PLAIN, 22));

		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String password = new String(passwordField.getPassword());
				if (!StringUtil.isEmpty(password)) {
					if (password.length() > 10 || password.length() < 6) {
						epasswordLabel.setVisible(true);
					} else {
						epasswordLabel.setVisible(false);
					}
				}
			}
		});
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addEmployeeACtionPerformed();
				}
			}
		});
		passwordField.setBounds(788, 329, 185, 39);// ���빦��
		addEmployee.add(passwordField);
		// passwordField.setColumns(25);
		epasswordLabel = new JLabel("\u5BC6\u7801\u4E0D\u7B26\u5408\u89C4\u5219");
		epasswordLabel.setForeground(Color.RED);
		epasswordLabel.setBounds(819, 370, 106, 18);
		epasswordLabel.setVisible(false);
		addEmployee.add(aePasswordLabel);
		addEmployee.add(epasswordLabel);
	}

	/**
	 * �����˻�
	 */
	private void loadUsernameModule() {
		JLabel aeUsernameLabel = new JLabel("\u8D26\u6237");
		aeUsernameLabel.setBounds(51, 342, 44, 26);// �˻�
		aeUsernameLabel.setFont(new Font("����", Font.PLAIN, 22));
		aeUsernameTextField = new JTextField();
		aeUsernameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addEmployeeACtionPerformed();
				}
			}
		});
		aeUsernameTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String username = aeUsernameTextField.getText();
				if (!StringUtil.isEmpty(username)) {
					if (username.length() > 10) {
						usernameLabel.setVisible(true);
					} else {
						usernameLabel.setVisible(false);
					}
				}
			}
		});
		aeUsernameTextField.setBounds(168, 338, 186, 39);// �˻�����
		aeUsernameTextField.setColumns(10);
		usernameLabel = new JLabel("\u8D26\u6237\u59D3\u540D\u4E0D\u7B26\u5408\u89C4\u5219");
		usernameLabel.setForeground(Color.RED);
		usernameLabel.setBounds(368, 349, 140, 18);
		usernameLabel.setVisible(false);
		addEmployee.add(aeUsernameLabel);
		addEmployee.add(aeUsernameTextField);
		addEmployee.add(usernameLabel);

	}

	private void loadSexModule() {
		JLabel aeSexLabel = new JLabel("\u6027\u522B");
		aeSexLabel.setBounds(51, 150, 44, 26);// �Ա�
		aeSexLabel.setFont(new Font("����", Font.PLAIN, 22));

		// JComboBox aeSexComboBox = new JComboBox();
		DefaultComboBoxModel employeeSex = new DefaultComboBoxModel();
		employeeSex.addElement("��");
		employeeSex.addElement("Ů");
		aeSexComboBox = new JComboBox(employeeSex);
		aeSexComboBox.setBounds(170, 142, 186, 34);// �Ա���
		addEmployee.add(aeSexLabel);
		addEmployee.add(aeSexComboBox);
	}

	/**
	 * ��������
	 */
	private void loadNameModule() {
		JLabel aeNameLabel = new JLabel("\u5458\u5DE5\u59D3\u540D");
		aeNameLabel.setBounds(606, 49, 88, 26);// Ա������
		aeNameLabel.setFont(new Font("����", Font.PLAIN, 22));
		aeNameTextField = new JTextField();
		aeNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addEmployeeACtionPerformed();
				}
			}
		});
		aeNameTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String ename = aeNameTextField.getText();
				if (!StringUtil.isEmpty(ename)) {
					if (ename.length() > 25) {
						enameNewLabel.setVisible(true);
					} else {
						enameNewLabel.setVisible(false);
					}
				}
			}
		});

		aeNameTextField.setBounds(788, 44, 185, 31);// Ա����������
		aeNameTextField.setColumns(10);
		enameNewLabel = new JLabel("\u5458\u5DE5\u59D3\u540D\u5408\u89C4\u5219");
		enameNewLabel.setForeground(Color.RED);
		enameNewLabel.setBounds(818, 83, 123, 18);
		enameNewLabel.setVisible(false);
		addEmployee.add(aeNameLabel);
		addEmployee.add(aeNameTextField);
		addEmployee.add(enameNewLabel);
	}

	/**
	 * ������ϵ�绰
	 */
	private void loadTelModule() {
		JLabel aeTelLabel = new JLabel("\u8054\u7CFB\u7535\u8BDD");
		aeTelLabel.setBounds(51, 254, 88, 26);// ��ϵ�绰
		aeTelLabel.setFont(new Font("����", Font.PLAIN, 22));
		aeTelTextField = new JTextField();

		etelLabel = new JLabel("\u8054\u7CFB\u7535\u8BDD\u4E0D\u7B26\u5408\u89C4\u5219");
		etelLabel.setForeground(Color.RED);
		etelLabel.setBounds(371, 255, 140, 25);
		etelLabel.setVisible(false);
		addEmployee.add(etelLabel);
		aeTelTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String etel = aeTelTextField.getText();
				if (StringUtil.isTel(etel) && employeeService.queryByStringid(etel).isEmpty()) {
					etelLabel.setVisible(false);
				} else {
					etelLabel.setVisible(true);
				}
			}
		});

		aeTelTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addEmployeeACtionPerformed();
				}
			}
		});

		aeTelTextField.setBounds(168, 246, 186, 34);// ��ϵ�绰����
		aeTelTextField.setColumns(10);
		addEmployee.add(aeTelLabel);
		addEmployee.add(aeTelTextField);

	}

	/**
	 * 
	 * 
	 * ����id
	 */
	private void loadEidModule() {
		JLabel aeEidLable = new JLabel("\u5458\u5DE5id");

		aeEidLable.setBounds(51, 44, 66, 26);// Ա��id
		aeEidLable.setFont(new Font("����", Font.PLAIN, 22));
		aeEidTextField = new JTextField();
		aeEidTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String eid = aeEidTextField.getText();
				if (StringUtil.isEmpty(eid)) {
					return;
				}
				if (!StringUtil.isNumber(eid)) {
					if (employeeService.queryByStringid(eid).isEmpty()) {
						eidLabel.setVisible(false);
					} else if (eid.length() > 10) {
						eidLabel.setVisible(true);
					} else {
						eidLabel.setVisible(true);
					}
				}
			}
		});
		aeEidTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
			}

			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addEmployeeACtionPerformed();
				}
			}
		});

		aeEidTextField.setBounds(170, 44, 186, 32);// Ա��id����
		eidLabel = new JLabel("\u5458\u5DE5id\u4E0D\u7B26\u5408\u89C4\u5219");
		eidLabel.setForeground(Color.RED);
		eidLabel.setBounds(196, 82, 123, 18);
		eidLabel.setVisible(false);
		addEmployee.add(eidLabel);
		addEmployee.add(aeEidLable);
		addEmployee.add(aeEidTextField);
	}

	/**
	 * ���Ա���¼�����
	 */
	public void addEmployeeACtionPerformed() {
		String id = aeEidTextField.getText();
		if (StringUtil.isEmpty(id)) {
			id = String.valueOf(Math.round(Math.random() * 100000000));

			while (!employeeService.queryByStringid(id).isEmpty()) {
				id = String.valueOf(Math.round(Math.random() * 100000000));
			}
		} else {
			if (id.length() > 10) {
				JOptionPane.showMessageDialog(null, "Ա��id���ܴ���10");
				return;
			} else if (!employeeService.queryByStringid(id).isEmpty()) {
				JOptionPane.showMessageDialog(null, "Ա��id�Ѿ�����");
				return;
			}
		}
		int eid = Integer.parseInt(id);

		String ename = aeNameTextField.getText();
		if (StringUtil.isEmpty(ename)) {
			JOptionPane.showMessageDialog(null, "Ա����������Ϊ��");
			return;
		} else if (ename.length() > 25) {
			JOptionPane.showMessageDialog(null, "Ա���������Ȳ��ܴ���25");
			return;
		}

		String esex = aeSexComboBox.getSelectedItem().toString();
		String eadress = aeAdressTextField.getText();
		if (StringUtil.isEmpty(eadress)) {
			JOptionPane.showMessageDialog(null, "סַ����Ϊ��");
			return;
		} else if (eadress.length() > 50) {
			JOptionPane.showMessageDialog(null, "סַ���Ȳ��ܴ���50");
			return;
		}

		String tel = aeTelTextField.getText();
		if (!StringUtil.isTel(tel)) {
			JOptionPane.showMessageDialog(null, "��ϵ��ʽ�������ֻ����ߵ绰�������");
			return;
		}
		long etel = Long.parseLong(tel);

		String password = new String(passwordField.getPassword());
		if (password.length() > 10 || password.length() < 6) {
			JOptionPane.showMessageDialog(null, "���볤��Ӧ��Ϊ6��10λ");
			return;
		} else if (StringUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(null, "���벻���Ϲ���");
			return;
		}

		String email = aeEmailTextField.getText();
		if (!StringUtil.isEmail(email)) {
			JOptionPane.showMessageDialog(null, "���䲻���Ϲ���");
			return;
		}

		String username = aeUsernameTextField.getText();
		if (StringUtil.isEmpty(username)) {
			JOptionPane.showMessageDialog(null, "�˻�����Ϊ��");
			return;
		}
		if (username.equals(User.class.getName())) {
			JOptionPane.showMessageDialog(null, "�˻�������");
			return;
		}
		if (employeeService.addEmployee(new Employee(eid, ename, esex, eadress, etel, email),
				new User(username, password, accountTypeComboBox.getSelectedItem().toString()))) {
			JOptionPane.showMessageDialog(null, "���Ա���ɹ�");
			addEmployee.removeAll();
			addEmployee.updateUI();
			loadModule();
			return;
		} else {
			JOptionPane.showMessageDialog(null, "���Ա��ʧ��");
		}

	}

	public void loadButton() {
		JButton aeAddEmployeeButton = new JButton("\u6DFB\u52A0\u5458\u5DE5");
		aeAddEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEmployeeACtionPerformed();
			}
		});

		aeAddEmployeeButton.setBounds(301, 473, 147, 62);// ���Ա������
		aeAddEmployeeButton.setFont(new Font("����", Font.PLAIN, 22));

		JButton aeEmptyButton = new JButton("\u6E05\u7A7A\u6587\u672C\u6846");
		aeEmptyButton.setBounds(780, 477, 147, 55);// ����ı���

		aeEmptyButton.setFont(new Font("����", Font.PLAIN, 22));
		aeEmptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEmployee.removeAll();
				addEmployee.repaint();
				loadModule();
			}
		});
		add(aeAddEmployeeButton);
		add(aeEmptyButton);
	}
}
