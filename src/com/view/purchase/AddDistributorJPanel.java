package com.view.purchase;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.entity.Distributor;
import com.service.DistributorService;
import com.service.imp.DistributorServiceImpl;
import com.util.KeyTypeUtil;
import com.util.StringUtil;


/**
 * 添加供应商Jpanel
 * 
 * @author wuhong
 *
 */
public class AddDistributorJPanel extends JPanel {
	private JPanel addDistributorPanel;
	private JTextField agDidTextField;
	private JTextField agDnameTextField;
	private JTextField agDtelTextField;
	private JTextField agDemailTextField;
	private JLabel telHintLabel;
	private JLabel emailHintLabel;
	private DistributorService distributorService = new DistributorServiceImpl();
	
	public AddDistributorJPanel() {
		AddDistributor();
	}

	private void AddDistributor() {
		addDistributorPanel = new JPanel();
		addDistributorPanel.setBounds(93, 46, 1076, 388);
		addDistributorPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "添加供应商",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));

		JButton addGoodsButton = new JButton("添加供应商");//添加供应商
		addGoodsButton.setBounds(209, 452, 147, 62);
		addGoodsButton.setFont(new Font("宋体", Font.PLAIN, 22));
		addGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDistributorActionPerformed();
			}
		});

		JButton agemptyButton = new JButton("\u6E05\u7A7A\u6587\u672C\u6846");//重置文本框和label
		agemptyButton.setBounds(757, 456, 147, 55);
		agemptyButton.setFont(new Font("宋体", Font.PLAIN, 22));
		agemptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDistributorPanel.removeAll();
				addDistributorPanel.repaint();
				loadLabel();
				loadTextField();
			}
		});
		
		setLayout(null);
		
		loadLabel();
		loadTextField();
		
		add(addDistributorPanel);
		addDistributorPanel.setLayout(null);
		
		addDistributorPanel.add(telHintLabel);
		
		
		
		addDistributorPanel.add(emailHintLabel);
		add(addGoodsButton);
		add(agemptyButton);
	}
	
	/**
	 * 添加供应商事件处理
	 */
	private  void addDistributorActionPerformed() {
		if(StringUtil.isEmpty(agDidTextField.getText())) {
			JOptionPane.showMessageDialog(null, "供应商编号不能为空，请重新输入");
			return;
		}else if(agDidTextField.getText().length()>10) {
			JOptionPane.showMessageDialog(null, "供应商编号长度不能大于10，请重新输入");
			return;
		}
		int did = Integer.parseInt(agDidTextField.getText());
		if(distributorService.queryDistributorById(did) != null) {
			JOptionPane.showMessageDialog(null, "供应商编号已经存在，请重新输入");
			return;
		}
		
		String dname = agDnameTextField.getText();
		if(StringUtil.isEmpty(dname)) {
			JOptionPane.showMessageDialog(null, "供应商名称不能为空，请重新输入");
			return;
		}else if(dname.length()>50) {
			JOptionPane.showMessageDialog(null, "供应商名称长度不能大于50，请重新输入");
			return;
		}
		if(distributorService.queryDistributorByName(dname) != null) {
			JOptionPane.showMessageDialog(null, "供应商名称已经存在，请重新输入");
			return;
		}
		
		if(StringUtil.isEmpty(agDtelTextField.getText())) {
			JOptionPane.showMessageDialog(null, "电话号码不能为空，请重新输入");
			return;
		}else if(!(agDtelTextField.getText().matches("(0\\d{2,3}-){0,1}\\d{7,8}|1[34578]\\d{9}"))) {
			JOptionPane.showMessageDialog(null, "手机号码或电话号码不符合规范，请重新输入");
			return;
		}
		
		if(StringUtil.isEmpty(agDemailTextField.getText())) {
			JOptionPane.showMessageDialog(null, "邮箱不能为空，请重新输入");
			return;
		}else if(!(agDemailTextField.getText().matches("[_a-z\\d\\-\\./]+@[_a-z\\d\\-]+(\\.[_a-z\\d\\-]+)*(\\.(info|biz|com|edu|gov|net|am|bz|cn|cx|hk|jp|tw|vc|vn))$"))) {
			JOptionPane.showMessageDialog(null, "邮箱格式不正确，请重新输入");
			return;
		}
		
		Distributor distributor=new Distributor(did,dname,agDtelTextField.getText(),agDemailTextField.getText());
		if(distributorService.addDistributor(distributor)){
			JOptionPane.showMessageDialog(null, "添加供应商成功");
			addDistributorPanel.removeAll();
			addDistributorPanel.repaint();
			loadLabel();
			loadTextField();
		}else{
			JOptionPane.showMessageDialog(null, "添加供应商失败");
		}
	}

	/**
	 * 加载TextField
	 * @return 
	 */
	private void loadTextField() {
		agDidTextField = new JTextField();
		agDidTextField.setBounds(167, 44, 186, 32);
		agDidTextField.setColumns(10);
		agDidTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
			}
		});

		agDnameTextField = new JTextField();
		agDnameTextField.setBounds(787, 45, 186, 30);
		agDnameTextField.setColumns(10);

		agDtelTextField = new JTextField();
		agDtelTextField.setBounds(167, 145, 186, 35);
		agDtelTextField.setColumns(10);
		agDtelTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				telFocusLostListener(e);
			}
		});

		agDemailTextField = new JTextField();
		agDemailTextField.setBounds(787, 145, 186, 32);
		agDemailTextField.setColumns(10);
		agDemailTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				emailFocusLostListener(e);
			}
		});
	
		addDistributorPanel.add(agDidTextField);
		addDistributorPanel.add(agDtelTextField);
		addDistributorPanel.add(agDemailTextField);
		addDistributorPanel.add(agDnameTextField);
	}
	
	/**
	 * 邮箱文本框失去焦点事件处理
	 * @param e
	 */
	private  void emailFocusLostListener(FocusEvent e) {
		if(StringUtil.isEmail(agDemailTextField.getText())) {
			emailHintLabel.setVisible(false);
		}else {
			emailHintLabel.setVisible(true);
		}
	}

	/**
	 * 联系方式文本框失去焦点事件处理
	 * @param e
	 */
	private  void telFocusLostListener(FocusEvent e) {
		if(StringUtil.isTel(agDtelTextField.getText())) {
			telHintLabel.setVisible(false);
		}else {
			telHintLabel.setVisible(true);
		}
		
	}

	/**
	 * 加载label
	 */
	private void loadLabel() {
		JLabel agDidLabel = new JLabel("供应商编号");
		agDidLabel.setBounds(47, 44, 110, 36);
		agDidLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		addDistributorPanel.add(agDidLabel);

		JLabel agDnameLabel = new JLabel("供应商名称");
		agDnameLabel.setBounds(583, 49, 110, 26);
		agDnameLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		addDistributorPanel.add(agDnameLabel);

		JLabel agDtelLabel = new JLabel("联系方式");
		agDtelLabel.setBounds(51, 146, 88, 26);
		agDtelLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		addDistributorPanel.add(agDtelLabel);

		JLabel agDemailLabel = new JLabel("邮箱");
		agDemailLabel.setBounds(605, 145, 44, 26);
		agDemailLabel.setFont(new Font("宋体", Font.PLAIN, 22));
		addDistributorPanel.add(agDemailLabel);
		
		telHintLabel = new JLabel("电话号码格式错误");
		telHintLabel.setForeground(Color.RED);
		telHintLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		telHintLabel.setBounds(191, 195, 135, 18);
		telHintLabel.setVisible(false);
		
		
		emailHintLabel = new JLabel("邮箱格式错误");
		emailHintLabel.setForeground(Color.RED);
		emailHintLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		emailHintLabel.setBounds(811, 190, 143, 18);
		emailHintLabel.setVisible(false);
	}
}
