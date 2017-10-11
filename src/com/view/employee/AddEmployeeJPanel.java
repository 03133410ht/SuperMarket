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
 * 添加员工JPanel
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
		accountTypeLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		accountTypeLabel.setBounds(51, 421, 88, 23);
		addEmployee.add(accountTypeLabel);

		String[] at = { "admin", "employee" };
		accountTypeComboBox = new JComboBox(at);
		accountTypeComboBox.setBounds(170, 418, 186, 34);
		addEmployee.add(accountTypeComboBox);

	}

	/**
	 * 加载邮箱
	 */
	private void loadEmailModule() {
		JLabel aeEmailLabel = new JLabel("\u90AE\u7BB1");
		aeEmailLabel.setBounds(606, 142, 44, 26);// 邮箱
		aeEmailLabel.setFont(new Font("宋体", Font.PLAIN, 22));
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
		aeEmailTextField.setBounds(788, 140, 185, 36);// 邮箱功能
		aeEmailTextField.setColumns(10);

		addEmployee.add(aeEmailLabel);
		addEmployee.add(aeEmailTextField);
	}

	/**
	 * 加载住址
	 */
	private void loadAdressModule() {
		JLabel aeAdressLabel = new JLabel("\u4F4F\u5740");
		aeAdressLabel.setBounds(606, 247, 44, 26);// 住址
		aeAdressLabel.setFont(new Font("宋体", Font.PLAIN, 22));
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
		aeAdressTextField.setBounds(788, 242, 185, 35);// 住址功能
		aeAdressTextField.setColumns(10);
		addEmployee.add(aeAdressLabel);
		addEmployee.add(aeAdressTextField);
	}

	/**
	 * 加载员工密码
	 */
	private void loadPasswordModule() {
		JLabel aePasswordLabel = new JLabel("\u5BC6\u7801");
		aePasswordLabel.setBounds(606, 341, 44, 26);// 密码
		aePasswordLabel.setFont(new Font("宋体", Font.PLAIN, 22));

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
		passwordField.setBounds(788, 329, 185, 39);// 密码功能
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
	 * 加载账户
	 */
	private void loadUsernameModule() {
		JLabel aeUsernameLabel = new JLabel("\u8D26\u6237");
		aeUsernameLabel.setBounds(51, 342, 44, 26);// 账户
		aeUsernameLabel.setFont(new Font("宋体", Font.PLAIN, 22));
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
		aeUsernameTextField.setBounds(168, 338, 186, 39);// 账户功能
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
		aeSexLabel.setBounds(51, 150, 44, 26);// 性别
		aeSexLabel.setFont(new Font("宋体", Font.PLAIN, 22));

		// JComboBox aeSexComboBox = new JComboBox();
		DefaultComboBoxModel employeeSex = new DefaultComboBoxModel();
		employeeSex.addElement("男");
		employeeSex.addElement("女");
		aeSexComboBox = new JComboBox(employeeSex);
		aeSexComboBox.setBounds(170, 142, 186, 34);// 性别功能
		addEmployee.add(aeSexLabel);
		addEmployee.add(aeSexComboBox);
	}

	/**
	 * 加载姓名
	 */
	private void loadNameModule() {
		JLabel aeNameLabel = new JLabel("\u5458\u5DE5\u59D3\u540D");
		aeNameLabel.setBounds(606, 49, 88, 26);// 员工姓名
		aeNameLabel.setFont(new Font("宋体", Font.PLAIN, 22));
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

		aeNameTextField.setBounds(788, 44, 185, 31);// 员工姓名功能
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
	 * 加载联系电话
	 */
	private void loadTelModule() {
		JLabel aeTelLabel = new JLabel("\u8054\u7CFB\u7535\u8BDD");
		aeTelLabel.setBounds(51, 254, 88, 26);// 联系电话
		aeTelLabel.setFont(new Font("宋体", Font.PLAIN, 22));
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

		aeTelTextField.setBounds(168, 246, 186, 34);// 联系电话功能
		aeTelTextField.setColumns(10);
		addEmployee.add(aeTelLabel);
		addEmployee.add(aeTelTextField);

	}

	/**
	 * 
	 * 
	 * 加载id
	 */
	private void loadEidModule() {
		JLabel aeEidLable = new JLabel("\u5458\u5DE5id");

		aeEidLable.setBounds(51, 44, 66, 26);// 员工id
		aeEidLable.setFont(new Font("宋体", Font.PLAIN, 22));
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

		aeEidTextField.setBounds(170, 44, 186, 32);// 员工id功能
		eidLabel = new JLabel("\u5458\u5DE5id\u4E0D\u7B26\u5408\u89C4\u5219");
		eidLabel.setForeground(Color.RED);
		eidLabel.setBounds(196, 82, 123, 18);
		eidLabel.setVisible(false);
		addEmployee.add(eidLabel);
		addEmployee.add(aeEidLable);
		addEmployee.add(aeEidTextField);
	}

	/**
	 * 添加员工事件处理
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
				JOptionPane.showMessageDialog(null, "员工id不能大于10");
				return;
			} else if (!employeeService.queryByStringid(id).isEmpty()) {
				JOptionPane.showMessageDialog(null, "员工id已经存在");
				return;
			}
		}
		int eid = Integer.parseInt(id);

		String ename = aeNameTextField.getText();
		if (StringUtil.isEmpty(ename)) {
			JOptionPane.showMessageDialog(null, "员工姓名不能为空");
			return;
		} else if (ename.length() > 25) {
			JOptionPane.showMessageDialog(null, "员工姓名长度不能大于25");
			return;
		}

		String esex = aeSexComboBox.getSelectedItem().toString();
		String eadress = aeAdressTextField.getText();
		if (StringUtil.isEmpty(eadress)) {
			JOptionPane.showMessageDialog(null, "住址不能为空");
			return;
		} else if (eadress.length() > 50) {
			JOptionPane.showMessageDialog(null, "住址长度不能大于50");
			return;
		}

		String tel = aeTelTextField.getText();
		if (!StringUtil.isTel(tel)) {
			JOptionPane.showMessageDialog(null, "联系方式不符合手机或者电话号码规则");
			return;
		}
		long etel = Long.parseLong(tel);

		String password = new String(passwordField.getPassword());
		if (password.length() > 10 || password.length() < 6) {
			JOptionPane.showMessageDialog(null, "密码长度应该为6到10位");
			return;
		} else if (StringUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(null, "密码不符合规则");
			return;
		}

		String email = aeEmailTextField.getText();
		if (!StringUtil.isEmail(email)) {
			JOptionPane.showMessageDialog(null, "邮箱不符合规则");
			return;
		}

		String username = aeUsernameTextField.getText();
		if (StringUtil.isEmpty(username)) {
			JOptionPane.showMessageDialog(null, "账户不能为空");
			return;
		}
		if (username.equals(User.class.getName())) {
			JOptionPane.showMessageDialog(null, "账户名错误");
			return;
		}
		if (employeeService.addEmployee(new Employee(eid, ename, esex, eadress, etel, email),
				new User(username, password, accountTypeComboBox.getSelectedItem().toString()))) {
			JOptionPane.showMessageDialog(null, "添加员工成功");
			addEmployee.removeAll();
			addEmployee.updateUI();
			loadModule();
			return;
		} else {
			JOptionPane.showMessageDialog(null, "添加员工失败");
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

		aeAddEmployeeButton.setBounds(301, 473, 147, 62);// 添加员工功能
		aeAddEmployeeButton.setFont(new Font("宋体", Font.PLAIN, 22));

		JButton aeEmptyButton = new JButton("\u6E05\u7A7A\u6587\u672C\u6846");
		aeEmptyButton.setBounds(780, 477, 147, 55);// 清除文本框

		aeEmptyButton.setFont(new Font("宋体", Font.PLAIN, 22));
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
