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
 * ���Ա��JPanel
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
	 * �����ı���ͱ�ǩ
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
	 * ���ػ�Ա�ȼ�������ͱ�ǩ
	 */
	private void loadGradeModule() {
		JLabel amGradeLabel = new JLabel("\u4F1A\u5458\u7B49\u7EA7");
		amGradeLabel.setBounds(605, 158, 88, 26);
		amGradeLabel.setFont(new Font("����", Font.PLAIN, 22));
		DefaultComboBoxModel memberGrade = new DefaultComboBoxModel();
		memberGrade.addElement("��ͨ��Ա");
		memberGrade.addElement("�ƽ��Ա");
		memberGrade.addElement("�����Ա");
		memberGrade.addElement("��ʯ��Ա");
		memberGrade.addElement("�����Ա");
		amGradeComboBox = new JComboBox(memberGrade);
		amGradeComboBox.setBounds(787, 146, 189, 34);
		addMember.add(amGradeLabel);
		addMember.add(amGradeComboBox);
	}

	/**
	 * ���ػ�Ա�Ա���������ı�
	 */
	private void loadSexModule() {
		JLabel amSexLabel = new JLabel("\u6027\u522B");
		amSexLabel.setBounds(51, 148, 44, 26);
		amSexLabel.setFont(new Font("����", Font.PLAIN, 22));
		DefaultComboBoxModel memberSex = new DefaultComboBoxModel();
		memberSex.addElement("��");
		memberSex.addElement("Ů");
		amSexComboBox = new JComboBox(memberSex);
		amSexComboBox.setBounds(167, 147, 186, 34);
		addMember.add(amSexLabel);
		addMember.add(amSexComboBox);
	}

	/**
	 * ���ػ�Ա���Ʊ�ǩ���ı���
	 */
	private void loadNmaeModule() {
		JLabel amNameLabel = new JLabel("\u4F1A\u5458\u59D3\u540D");
		amNameLabel.setBounds(605, 49, 88, 26);
		amNameLabel.setFont(new Font("����", Font.PLAIN, 22));
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
	 * ���ػ�Ա�����ı���ͱ�ǩ
	 */
	private void loadPasswordModule() {
		JLabel mPasswordLabel = new JLabel("\u4F1A\u5458\u5BC6\u7801");
		mPasswordLabel.setFont(new Font("����", Font.PLAIN, 22));
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
	 * ���س�ֵ����ǩ���ı���
	 */
	private void loadMoneyModule() {
		JLabel amMoneyLabel = new JLabel("\u5145\u503C\u91D1\u989D");
		amMoneyLabel.setBounds(605, 251, 88, 26);
		amMoneyLabel.setFont(new Font("����", Font.PLAIN, 22));

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
	 * ���ػ�Ա�ֻ���ǩ���ı���
	 */
	private void loadTelModule() {
		JLabel amTelLabel = new JLabel("\u4F1A\u5458\u624B\u673A");
		amTelLabel.setBounds(51, 251, 88, 26);
		amTelLabel.setFont(new Font("����", Font.PLAIN, 22));

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
	 * ���ػ�Աid��ǩ���ı���
	 */
	private void loadMidModule() {
		JLabel amMidLabel = new JLabel("\u4F1A\u5458id");
		amMidLabel.setBounds(51, 44, 66, 26);
		amMidLabel.setFont(new Font("����", Font.PLAIN, 22));

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
	 * ���ذ�ť
	 */
	public void loadButton() {
		JButton amAddMemberButton = new JButton("\u6DFB\u52A0\u4F1A\u5458");
		amAddMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMemberACtionPerformed();
			}
		});
		amAddMemberButton.setBounds(240, 456, 147, 62);
		amAddMemberButton.setFont(new Font("����", Font.PLAIN, 22));

		JButton amEmptyButton = new JButton("\u6E05\u7A7A\u6587\u672C\u6846");
		amEmptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// ����ı����¼�
				addMember.removeAll();
				addMember.updateUI();
				loadModule();
			}
		});
		amEmptyButton.setBounds(788, 460, 147, 55);
		amEmptyButton.setFont(new Font("����", Font.PLAIN, 22));
		add(amAddMemberButton);
		add(amEmptyButton);
	}

	/**
	 * ���Ա���¼�����
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
				JOptionPane.showMessageDialog(null, "��Աid���ܴ���10");
				return;
			}
			if(!memberService.queryByid(id).isEmpty()) {
				JOptionPane.showMessageDialog(null, "��Աid�Ѿ�����");
				return;
			}
		}
		int mid = Integer.parseInt(id);
		
		String mname = amNameTextField.getText();
		if(StringUtil.isEmpty(mname)) {
			JOptionPane.showMessageDialog(null, "��Ա��������Ϊ��");
			return;
		}
		if(mname.length()>25) {
			JOptionPane.showMessageDialog(null, "��Ա�������Ȳ��ܴ���25");
			return;
		}
		
		String msex = amSexComboBox.getSelectedItem().toString();
		String mgrade = amGradeComboBox.getSelectedItem().toString();
		
		String tel = amTelTextField.getText();
		if(!StringUtil.isTel(tel)) {
			JOptionPane.showMessageDialog(null, "��ϵ��ʽ�������ֻ����ߵ绰�������");
			return;
		}
		long mtel = Long.parseLong(tel);
		
		String account = amMoneyTextField.getText();
		if(!StringUtil.isPrice(account)) {
			JOptionPane.showMessageDialog(null, "��ֵ�����Ϲ����Ϊ10λ��ֻ������2λС��");
			return;
		}
		int maccount = Integer.parseInt(account);
		
		String mpassword = new String (mPasswordField.getPassword());
		if(mpassword.length()>10 ||mpassword.length() <6 ) {
			JOptionPane.showMessageDialog(null, "���볤��Ӧ��Ϊ6��10λ");
			return;
		}
		
		if(memberService.addMember(new Member(mid, mname, msex, mgrade, 0, mtel, maccount, mpassword))) {
			JOptionPane.showMessageDialog(null, "��ӻ�Ա�ɹ�");
			addMember.removeAll();
			addMember.updateUI();
			loadModule();
			return;
		}else {
			JOptionPane.showMessageDialog(null, "��ӻ�Աʧ��");
		}
		
	}
}
