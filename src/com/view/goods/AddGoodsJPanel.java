package com.view.goods;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.entity.Goods;
import com.service.GoodsService;
import com.service.imp.GoodsServiceImp;
import com.util.KeyTypeUtil;
import com.util.StringUtil;

/**
 * �����ƷJpanel
 * 
 * @author Goddard
 *
 */
public class AddGoodsJPanel extends JPanel {
	private JPanel addGoodsPanel;
	private JTextField agGidTextField;
	private JTextField agGnameTextField;
	private JTextField agCostTextField;
	private JTextField agPriceTextField;
	private JTextField agCategoryTextField;
	private JTextField agGunitTextField;
	private JTextField agPointTextField;
	private JTextField agRemarkTextField;
	private JLabel costHintLabel;
	private JLabel priceHintLabel;
	private JButton addGoodsButton;
	private GoodsService goodsService = new GoodsServiceImp();
	
	
	public AddGoodsJPanel() {
		addGoods();
	}

	private void addGoods() {
		addGoodsPanel = new JPanel();
		addGoodsPanel.setBounds(93, 46, 1076, 388);
		addGoodsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6DFB\u52A0\u5546\u54C1",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));

		addGoodsButton = new JButton("\u6DFB\u52A0\u5546\u54C1");//�����Ʒ
		addGoodsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGoodsActionPerformed();
			}
		});
		addGoodsButton.setBounds(209, 452, 147, 62);
		addGoodsButton.setFont(new Font("����", Font.PLAIN, 22));
		
		JButton agemptyButton = new JButton("\u6E05\u7A7A\u6587\u672C\u6846");//�����ı����label
		agemptyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGoodsPanel.removeAll();
				addGoodsPanel.repaint();
				loadLabel();
				loadTextField();
			}
		});
		agemptyButton.setBounds(757, 456, 147, 55);
		agemptyButton.setFont(new Font("����", Font.PLAIN, 22));
		
		setLayout(null);
		
		loadLabel();
		loadTextField();
		
		add(addGoodsPanel);
		addGoodsPanel.setLayout(null);
		
		addGoodsPanel.add(costHintLabel);
		
		
		
		addGoodsPanel.add(priceHintLabel);
		add(addGoodsButton);
		add(agemptyButton);
	}
	
	/**
	 * �����Ʒ�¼�����
	 */
	private  void addGoodsActionPerformed() {
		String gidText = agGidTextField.getText();
		if(StringUtil.isEmpty(gidText)) {
			JOptionPane.showMessageDialog(null, "��Ʒ���벻��Ϊ�գ�����������");
			return;
		}else if(gidText.length()>18) {
			JOptionPane.showMessageDialog(null, "��Ʒ���볤�Ȳ��ܴ���18������������");
			return;
		}
		int gid = Integer.parseInt(gidText);
		if(goodsService.queryGoodsById(gid) != null) {
			JOptionPane.showMessageDialog(null, "��Ʒ�����Ѿ����ڣ�����������");
			return;
		}
		
		
		String gname = agGnameTextField.getText();
		if(StringUtil.isEmpty(gname)) {
			JOptionPane.showMessageDialog(null, "��Ʒ���Ʋ���Ϊ�գ�����������");
			return;
		}else if(gname.length()>50) {
			JOptionPane.showMessageDialog(null, "��Ʒ���Ƴ��Ȳ��ܴ���50������������");
			return;
		}
		if(goodsService.queryGoodsByName(gname) != null) {
			JOptionPane.showMessageDialog(null, "��Ʒ�����Ѿ����� ������������");
			return;
		}
		
		
		String costText = agCostTextField.getText();
		if(StringUtil.isEmpty(costText)) {
			JOptionPane.showMessageDialog(null, "�ɱ��۲���Ϊ�գ�����������");
			return;
		}else if(costText.length()>18) {
			JOptionPane.showMessageDialog(null, "�ɱ��۳��Ȳ��ܴ���18������������");
			return;
		}else if(!(costText.matches("\\d{0,10}(\\.\\d{1,2})?") || costText == "")) {
			JOptionPane.showMessageDialog(null, "���ֲ����Ϲ淶�������2λС����С������������֣�����������");
			return;
		}
		double gcost = Double.parseDouble(costText);
		
		
		String priceText = agPriceTextField.getText();
		if(StringUtil.isEmpty(priceText)) {
			JOptionPane.showMessageDialog(null, "�ۼ۲���Ϊ�գ�����������");
			return;
		}else if(priceText.length()>18) {
			JOptionPane.showMessageDialog(null, "�ۼ۳��Ȳ��ܴ���18������������");
			return;
		}else if(!(priceText.matches("\\d{0,10}(\\.\\d{1,2})?") || priceText == "")) {
			JOptionPane.showMessageDialog(null, "���ֲ����Ϲ淶�������2λС����С������������֣�����������");
			return;
		}
		double gprice = Double.parseDouble(agPriceTextField.getText());
		
		
		String gcategory = agCategoryTextField.getText();
		if(StringUtil.isEmpty(gcategory)) {
			JOptionPane.showMessageDialog(null, "�����Ϊ�գ�����������");
			return;
		}else if(gcategory.length()>20) {
			JOptionPane.showMessageDialog(null, "��𳤶Ȳ��ܴ���20������������");
			return;
		}
		
		String gunit = agGunitTextField.getText();
		if(StringUtil.isEmpty(gunit)) {
			JOptionPane.showMessageDialog(null, "��λ����Ϊ�գ�����������");
			return;
		}else if(gunit.length()>20) {
			JOptionPane.showMessageDialog(null, "��λ���Ȳ��ܴ���20������������");
			return;
		}
		
		String pointText = agPointTextField.getText();
		if(StringUtil.isEmpty(pointText)) {
			JOptionPane.showMessageDialog(null, "�������ֲ���Ϊ�գ�����������");
			return;
		}else if(pointText.length()>10) {
			JOptionPane.showMessageDialog(null, "�������ֲ��ܴ���10������������");
			return;
		}
		int gpoints = Integer.parseInt(pointText);
		
		

		
		String gremark = agRemarkTextField.getText().trim();
		if(gremark.length()>100) {
			JOptionPane.showMessageDialog(null, "��ע���ܴ���100������������");
			return;
		}
		
		if(goodsService.addGoods(new Goods(gid, gname, gprice, gcost, 0, 1, gcategory, gpoints, gunit, gremark))) {
			JOptionPane.showMessageDialog(null, "�����Ʒ�ɹ�");
			addGoodsPanel.removeAll();
			addGoodsPanel.repaint();
			loadLabel();
			loadTextField();
		}else {
			JOptionPane.showMessageDialog(null, "�����Ʒʧ��");
			
		}
	}

	/**
	 * ����TextField
	 * @return 
	 */
	private void loadTextField() {
		agGidTextField = new JTextField();
		agGidTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
				
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		agGidTextField.setBounds(167, 44, 186, 32);
		agGidTextField.setColumns(10);

		agGnameTextField = new JTextField();
		agGnameTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		agGnameTextField.setBounds(787, 45, 186, 32);
		agGnameTextField.setColumns(10);

		agCostTextField = new JTextField();
		agCostTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				costFocusLostListener(e);
			}
		});
		agCostTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.priceType(e);
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		agCostTextField.setBounds(167, 145, 186, 32);
		agCostTextField.setColumns(10);

		agPriceTextField = new JTextField();
		agPriceTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		agPriceTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				priceFocusLostListener(e);
			}
		});
		agPriceTextField.setBounds(787, 145, 186, 32);
		agPriceTextField.setColumns(10);

		agCategoryTextField = new JTextField();
		agCategoryTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		agCategoryTextField.setBounds(167, 243, 186, 32);
		agCategoryTextField.setColumns(10);

		agGunitTextField = new JTextField();
		agGunitTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		
		agGunitTextField.setBounds(787, 239, 186, 32);
		agGunitTextField.setColumns(10);

		agPointTextField = new JTextField();
		agPointTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		agPointTextField.setBounds(167, 332, 186, 32);
		agPointTextField.setColumns(10);

		agRemarkTextField = new JTextField();
		agRemarkTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					addGoodsActionPerformed();
				}
			}
		});
		agRemarkTextField.setBounds(787, 334, 186, 32);
		agRemarkTextField.setColumns(10);
		addGoodsPanel.add(agGidTextField);
		addGoodsPanel.add(agCostTextField);
		addGoodsPanel.add(agCategoryTextField);
		addGoodsPanel.add(agPointTextField);
		addGoodsPanel.add(agRemarkTextField);
		addGoodsPanel.add(agGunitTextField);
		addGoodsPanel.add(agPriceTextField);
		addGoodsPanel.add(agGnameTextField);
	}
	
	/**
	 * �۸��ı���ʧȥ�����¼�����
	 * @param e
	 */
	private  void priceFocusLostListener(FocusEvent e) {
		if(StringUtil.isPrice(agPriceTextField.getText())) {
			priceHintLabel.setVisible(false);
		}else {
			priceHintLabel.setVisible(true);
		}
	}

	/**
	 * �ɱ����ı���ʧȥ�����¼�����
	 * @param e
	 */
	private  void costFocusLostListener(FocusEvent e) {
		if(StringUtil.isPrice(agCostTextField.getText())) {
			costHintLabel.setVisible(false);
		}else {
			costHintLabel.setVisible(true);
		}
		
	}

	/**
	 * ����label
	 */
	private void loadLabel() {
		JLabel agGidLabel = new JLabel("\u5546\u54C1\u6761\u7801");
		agGidLabel.setBounds(51, 44, 88, 26);
		agGidLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agGidLabel);

		JLabel agGnameLabel = new JLabel("\u5546\u54C1\u540D\u79F0");
		agGnameLabel.setBounds(605, 49, 88, 26);
		agGnameLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agGnameLabel);

		JLabel agCostLabel = new JLabel("\u6210\u672C\u4EF7");
		agCostLabel.setBounds(51, 146, 66, 26);
		agCostLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agCostLabel);

		JLabel agPriceLabel = new JLabel("\u552E\u4EF7");
		agPriceLabel.setBounds(605, 145, 44, 26);
		agPriceLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agPriceLabel);

		JLabel agCategoryLabel = new JLabel("\u5546\u54C1\u7C7B\u522B");
		agCategoryLabel.setBounds(51, 251, 88, 26);
		agCategoryLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agCategoryLabel);

		JLabel agGunitLabel = new JLabel("\u5355\u4F4D");
		agGunitLabel.setBounds(605, 244, 44, 26);
		agGunitLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agGunitLabel);

		JLabel agPointLabel = new JLabel("\u5355\u4E2A\u79EF\u5206");
		agPointLabel.setBounds(51, 342, 88, 26);
		agPointLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agPointLabel);

		JLabel agRemarkLabel = new JLabel("\u5907\u6CE8");
		agRemarkLabel.setBounds(605, 334, 44, 26);
		agRemarkLabel.setFont(new Font("����", Font.PLAIN, 22));
		addGoodsPanel.add(agRemarkLabel);
		
		costHintLabel = new JLabel("\u5F53\u524D\u4EF7\u683C\u4E0D\u7B26\u5408\u89C4\u8303");
		costHintLabel.setForeground(Color.RED);
		costHintLabel.setFont(new Font("����", Font.PLAIN, 14));
		costHintLabel.setBounds(191, 195, 135, 18);
		costHintLabel.setVisible(false);
		
		
		priceHintLabel = new JLabel("\u5F53\u524D\u4EF7\u683C\u4E0D\u7B26\u5408\u89C4\u8303");
		priceHintLabel.setForeground(Color.RED);
		priceHintLabel.setFont(new Font("����", Font.PLAIN, 14));
		priceHintLabel.setBounds(811, 190, 143, 18);
		priceHintLabel.setVisible(false);
	}
}
