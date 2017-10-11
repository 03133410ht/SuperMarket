package com.view.member;

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

import com.entity.Member;
import com.service.MemberService;
import com.service.imp.MemberServiceImp;
import com.util.KeyTypeUtil;
import com.util.StringUtil;

/**
 * 添加员工JPanel
 * 
 * @author Goddard
 *
 */
public class AddMemberJPanel extends JPanel {
	private JPanel addMember;
	private JTextField amMidTextField;
	private JTextField amTelTextField;
	private JTextField amMoneyTextField;
	private JTextField amNameTextField;
	private JPasswordField mPasswordField;
	private MemberService memberService = new MemberServiceImp();
	private JLabel midLabel;
	private JLabel mnameNewLabel;
	private JLabel passwordLabel;
	private JLabel moneyLabel;
	private JLabel telLabel;
	private JComboBox amSexComboBox;
	private JComboBox amGradeComboBox;

	public AddMemberJPanel() {
		addMember();
	}

	private void addMember() {
		addMember = new JPanel();
		addMember.setBounds(124, 50, 1076, 388);
		addMember.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u589e\u52a0\u4f1a\u5458",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		add(addMember);

		loadModule();
		loadButton();
	}

	/**
	 * 加载文本框和标签
	 */
	private void loadModule() {
		loadMidModule();
		loadTelModule();
		loadMoneyModule();
		loadNmaeModule();
		loadSexModule();
		loadPasswordModule();
		loadGradeModule();
		setLayout(null);
		addMember.setLayout(null);

	}

	/**
	 * 加载会员等级下拉框和标签
	 */
	private void loadGradeModule() {
		JLabel amGradeLabel = new JLabel("\u4F1A\u5458\u7B49\u7EA7");
		amGradeLabel.setBounds(605, 158, 88, 26);
		amGradeLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		DefaultComboBoxModel memberGrade = new DefaultComboBoxModel();
		memberGrade.addElement("普通会员");
		memberGrade.addElement("黄金会员");
		memberGrade.addElement("铂金会员");
		memberGrade.addElement("钻石会员");
		memberGrade.addElement("至尊会员");
		amGradeComboBox = new JComboBox(memberGrade);
		amGradeComboBox.setBounds(787, 146, 189, 34);
		addMember.add(amGradeLabel);
		addMember.add(amGradeComboBox);
	}

	/**
	 * 加载会员性别下拉框和文本
	 */
	private void loadSexModule() {
		JLabel amSexLabel = new JLabel("\u6027\u522B");
		amSexLabel.setBounds(51, 148, 44, 26);
		amSexLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		DefaultComboBoxModel memberSex = new DefaultComboBoxModel();
		memberSex.addElement("男");
		memberSex.addElement("女");
		amSexComboBox = new JComboBox(memberSex);
		amSexComboBox.setBounds(167, 147, 186, 34);
		addMember.add(amSexLabel);
		addMember.add(amSexComboBox);
	}

	/**
	 * 加载会员名称标签和文本框
	 */
	private void loadNmaeModule() {
		JLabel amNameLabel = new JLabel("\u4F1A\u5458\u59D3\u540D");
		amNameLabel.setBounds(605, 49, 88, 26);
		amNameLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		amNameTextField = new JTextField();
		amNameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addMemberACtionPerformed();
				}
			}
		});
		amNameTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String mname = amNameTextField.getText();
				if (!StringUtil.isEmpty(mname)) {
					if(mname.length() > 25){
						mnameNewLabel.setVisible(true);
					}else {
						mnameNewLabel.setVisible(false);
					}
				}
			}
		});
		amNameTextField.setBounds(787, 45, 186, 34);
		amNameTextField.setColumns(10);
		addMember.add(amNameLabel);
		addMember.add(amNameTextField);

		mnameNewLabel = new JLabel("\u4F1A\u5458\u59D3\u540D\u4E0D\u7B26\u5408\u89C4\u5219");
		mnameNewLabel.setForeground(Color.RED);
		mnameNewLabel.setBounds(816, 92, 135, 18);
		mnameNewLabel.setVisible(false);
		addMember.add(mnameNewLabel);
	}

	/**
	 * 加载会员密码文本框和标签
	 */
	private void loadPasswordModule() {
		JLabel mPasswordLabel = new JLabel("\u4F1A\u5458\u5BC6\u7801");
		mPasswordLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		mPasswordLabel.setBounds(51, 344, 88, 23);
		addMember.add(mPasswordLabel);

		mPasswordField = new JPasswordField();
		mPasswordField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String password = new String(mPasswordField.getPassword());
				if (!StringUtil.isEmpty(password)) {
					if (password.length() > 10 ||password.length()<6 ) {
						passwordLabel.setVisible(true);
					} else {
						passwordLabel.setVisible(false);
					}
				}
			}
		});
		mPasswordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addMemberACtionPerformed();
				}
			}
		});
		mPasswordField.setBounds(169, 341, 186, 34);
		addMember.add(mPasswordField);
		passwordLabel = new JLabel("\u4F1A\u5458\u5BC6\u7801\u4E0D\u7B26\u5408\u89C4\u5219");
		passwordLabel.setForeground(Color.RED);
		passwordLabel.setBounds(389, 349, 135, 18);
		passwordLabel.setVisible(false);
		addMember.add(passwordLabel);
	}

	/**
	 * 加载充值金额标签和文本框
	 */
	private void loadMoneyModule() {
		JLabel amMoneyLabel = new JLabel("\u5145\u503C\u91D1\u989D");
		amMoneyLabel.setBounds(605, 251, 88, 26);
		amMoneyLabel.setFont(new Font("宋体", Font.PLAIN, 22));

		amMoneyTextField = new JTextField();
		amMoneyTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.priceType(e);
			}

			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addMemberACtionPerformed();
				}
			}
		});
		amMoneyTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String money = amMoneyTextField.getText();
				if (StringUtil.isPrice(money)) {
					moneyLabel.setVisible(false);
				} else {
					moneyLabel.setVisible(true);
				}
			}
		});
		amMoneyTextField.setBounds(787, 250, 186, 34);
		amMoneyTextField.setColumns(10);
		addMember.add(amMoneyLabel);
		addMember.add(amMoneyTextField);

		moneyLabel = new JLabel("\u5145\u503C\u91D1\u989D\u4E0D\u7B26\u5408\u89C4\u5219");
		moneyLabel.setForeground(Color.RED);
		moneyLabel.setBounds(816, 297, 135, 18);
		moneyLabel.setVisible(false);
		addMember.add(moneyLabel);
	}

	/**
	 * 加载会员手机标签和文本框
	 */
	private void loadTelModule() {
		JLabel amTelLabel = new JLabel("\u4F1A\u5458\u624B\u673A");
		amTelLabel.setBounds(51, 251, 88, 26);
		amTelLabel.setFont(new Font("宋体", Font.PLAIN, 22));

		amTelTextField = new JTextField();
		amTelTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String tel = amTelTextField.getText();
				if(StringUtil.isTel(tel)&&memberService.queryByTel("= " +tel).isEmpty()) {
					telLabel.setVisible(false);
				}else{
					telLabel.setVisible(true);
				}
			}
		});

		amTelTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addMemberACtionPerformed();
				}
			}
		});
		amTelTextField.setBounds(169, 250, 186, 34);
		amTelTextField.setColumns(10);
		addMember.add(amTelLabel);
		addMember.add(amTelTextField);

		telLabel = new JLabel("\u4F1A\u5458\u624B\u673A\u4E0D\u7B26\u5408\u89C4\u5219");
		telLabel.setForeground(Color.RED);
		telLabel.setBounds(196, 297, 146, 18);
		telLabel.setVisible(false);
		addMember.add(telLabel);
	}

	/**
	 * 加载会员id标签和文本框
	 */
	private void loadMidModule() {
		JLabel amMidLabel = new JLabel("\u4F1A\u5458id");
		amMidLabel.setBounds(51, 44, 66, 26);
		amMidLabel.setFont(new Font("宋体", Font.PLAIN, 22));

		amMidTextField = new JTextField();
		amMidTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String getid = amMidTextField.getText();
				if (!StringUtil.isEmpty(getid)) {
					if (memberService.queryByid(getid).isEmpty() ) {
						midLabel.setVisible(false);
					} else if(getid.length() > 10){
						midLabel.setVisible(true);
					}else {
						midLabel.setVisible(true);
					}
				}
			}
		});
		amMidTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
			}

			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					addMemberACtionPerformed();
				}
			}
		});

		amMidTextField.setBounds(169, 44, 186, 34);
		amMidTextField.setColumns(10);

		midLabel = new JLabel("\u4F1A\u5458id\u4E0D\u7B26\u5408\u89C4\u5219");
		midLabel.setForeground(Color.RED);
		midLabel.setBounds(196, 91, 131, 18);
		midLabel.setVisible(false);

		addMember.add(midLabel);
		addMember.add(amMidLabel);
		addMember.add(amMidTextField);
	}

	/**
	 * 加载按钮
	 */
	public void loadButton() {
		JButton amAddMemberButton = new JButton("\u6DFB\u52A0\u4F1A\u5458");
		amAddMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMemberACtionPerformed();
			}
		});
		amAddMemberButton.setBounds(240, 456, 147, 62);
		amAddMemberButton.setFont(new Font("宋体", Font.PLAIN, 22));

		JButton amEmptyButton = new JButton("\u6E05\u7A7A\u6587\u672C\u6846");
		amEmptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// 清空文本框事件
				addMember.removeAll();
				addMember.updateUI();
				loadModule();
			}
		});
		amEmptyButton.setBounds(788, 460, 147, 55);
		amEmptyButton.setFont(new Font("宋体", Font.PLAIN, 22));
		add(amAddMemberButton);
		add(amEmptyButton);
	}

	/**
	 * 添加员工事件处理
	 */
	public void addMemberACtionPerformed() {
		String id = amMidTextField.getText();
		if(StringUtil.isEmpty(id)){
			id = String.valueOf(Math.round(Math.random()*100000000));
			
			while(!memberService.queryByid( id).isEmpty()) {
				id = String.valueOf(Math.round(Math.random()*100000000));
			}
		}else {
			if(id.length()>10) {
				JOptionPane.showMessageDialog(null, "会员id不能大于10");
				return;
			}
			if(!memberService.queryByid(id).isEmpty()) {
				JOptionPane.showMessageDialog(null, "会员id已经存在");
				return;
			}
		}
		int mid = Integer.parseInt(id);
		
		String mname = amNameTextField.getText();
		if(StringUtil.isEmpty(mname)) {
			JOptionPane.showMessageDialog(null, "会员姓名不能为空");
			return;
		}
		if(mname.length()>25) {
			JOptionPane.showMessageDialog(null, "会员姓名长度不能大于25");
			return;
		}
		
		String msex = amSexComboBox.getSelectedItem().toString();
		String mgrade = amGradeComboBox.getSelectedItem().toString();
		
		String tel = amTelTextField.getText();
		if(!StringUtil.isTel(tel)) {
			JOptionPane.showMessageDialog(null, "联系方式不符合手机或者电话号码规则");
			return;
		}
		long mtel = Long.parseLong(tel);
		
		String account = amMoneyTextField.getText();
		if(!StringUtil.isPrice(account)) {
			JOptionPane.showMessageDialog(null, "充值金额不符合规则！最长为10位，只允许有2位小数");
			return;
		}
		int maccount = Integer.parseInt(account);
		
		String mpassword = new String (mPasswordField.getPassword());
		if(mpassword.length()>10 ||mpassword.length() <6 ) {
			JOptionPane.showMessageDialog(null, "密码长度应该为6到10位");
			return;
		}
		
		if(memberService.addMember(new Member(mid, mname, msex, mgrade, 0, mtel, maccount, mpassword))) {
			JOptionPane.showMessageDialog(null, "添加会员成功");
			addMember.removeAll();
			addMember.updateUI();
			loadModule();
			return;
		}else {
			JOptionPane.showMessageDialog(null, "添加会员失败");
		}
		
	}
}
