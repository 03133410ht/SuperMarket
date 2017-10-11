package com.view.goods;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.entity.Goods;
import com.entity.GoodsCategory;
import com.service.GoodsService;
import com.service.imp.GoodsServiceImp;
import com.util.ImportExcel;
import com.util.OutExcel;
import com.util.StringUtil;

import jxl.read.biff.BiffException;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * ��Ʒ����Jpanel
 * 
 * @author Goddard
 *
 */
public class GoodsManageJPanel extends JPanel {
	private JComboBox gmInquireRangeComboBox;
	private JTextField gmInquireTextField;
	private GoodsManageJtable gmJTable;
	private List<Goods> list;
	private JList goodsCategorylist;
	private JPanel gmCategoryPanel;
	private List<GoodsCategory> categoryString;
	private GoodsService goodsService = new GoodsServiceImp();

	public GoodsManageJPanel() {
		goodsManage();
	}

	private void goodsManage() {
		JLabel gmInquireLabel = new JLabel("\u8FC7\u6EE4");
		gmInquireLabel.setBounds(46, 30, 30, 18);

		gmInquireTextField = new JTextField();
		gmInquireTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				query();
			}
		});
		gmInquireTextField.setBounds(90, 28, 153, 24);
		gmInquireTextField.setColumns(10);

		JButton gmInquireButton = new JButton("\u641C\u7D22");
		gmInquireButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (goodsService.queryCategory().size() + 1 != goodsCategorylist.getModel().getSize()) {
					gmCategoryPanel.removeAll();
					gmCategoryPanel.updateUI();
					loadCategoryList();
					gmCategoryPanel.updateUI();
				}
				query();
			}
		});
		gmInquireButton.setBounds(273, 26, 63, 27);

		JLabel gmInquireRangeLabel = new JLabel("\u67E5\u8BE2\u8303\u56F4");
		gmInquireRangeLabel.setBounds(383, 30, 60, 18);

		DefaultComboBoxModel queryRange = new DefaultComboBoxModel();
		queryRange.addElement("ȫ����Ʒ");
		queryRange.addElement("��ʾ���С��10����Ʒ");
		queryRange.addElement("��ʾ���Ϊ0 ����Ʒ");
		queryRange.addElement("����ʾ���Ϊ0 ����Ʒ");

		gmInquireRangeComboBox = new JComboBox(queryRange);
		gmInquireRangeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				query();
			}
		});

		gmInquireRangeComboBox.setBounds(469, 27, 189, 24);

		JScrollPane gmScrollPane = new JScrollPane();
		gmScrollPane.setBounds(202, 89, 1051, 413);

		JButton gmExcelButton = new JButton("\u5BFC\u51FAexcel");
		gmExcelButton.setBounds(969, 26, 103, 27);
		gmExcelButton.addActionListener(new ActionListener() {// ����excle
			public void actionPerformed(ActionEvent e) {
				new OutExcel(gmJTable);
			}
		});

		gmCategoryPanel = new JPanel();
		gmCategoryPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		gmCategoryPanel.setBounds(46, 89, 148, 413);
		add(gmCategoryPanel);
		loadCategoryList();

		gmJTable = new GoodsManageJtable(new ArrayList<Goods>());

		gmScrollPane.setViewportView(gmJTable);

		JButton gmDeleteButton = new JButton("\u5220\u9664");
		gmDeleteButton.addActionListener(new ActionListener() {// ɾ���¼�����
			public void actionPerformed(ActionEvent e) {
				deleteGoodsActionPerformed(e);
			}
		});
		gmDeleteButton.setBounds(846, 26, 86, 27);

		JButton gmSaveButton = new JButton("\u4FDD\u5B58");
		gmSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveActionPerformed(e);
			}
		});
		gmSaveButton.setBounds(709, 26, 96, 27);
		setLayout(null);
		add(gmInquireLabel);
		add(gmInquireTextField);
		add(gmInquireButton);
		add(gmInquireRangeLabel);
		add(gmInquireRangeComboBox);
		add(gmSaveButton);
		add(gmDeleteButton);
		add(gmExcelButton);
		add(gmScrollPane);

		JButton importExcelButton = new JButton("\u5BFC\u5165Excel");
		importExcelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importExcelActionPerformed();
			}
		});
		importExcelButton.setBounds(1102, 26, 103, 27);
		add(importExcelButton);

	}

	/**
	 * ����excel�¼�����
	 */
	private void importExcelActionPerformed() {
		if (gmJTable.getRowCount() == 0) {
			try {
				gmJTable.addRow(new ImportExcel().getObj());
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (JOptionPane.showConfirmDialog(null, "��ǰ���Ϊ�գ���Ҫ��ձ���ٵ���excel���Ƿ���ձ��", "��ձ��",
					JOptionPane.YES_NO_OPTION) == 0) {
				gmJTable.deleteAll();
				try {
					gmJTable.addRow(new ImportExcel().getObj());
				} catch (BiffException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * ���ز�ͬ���ѡ�������
	 * 
	 * @return
	 */
	private void loadCategoryList() {
		DefaultListModel goodsListModel = new DefaultListModel();

		categoryString = goodsService.queryCategory();// ���ز�ͬ���
		goodsListModel.addElement("������Ʒ");
		for (GoodsCategory goodsCategory : categoryString) {
			goodsListModel.addElement(goodsCategory.getGcategory());
		}
		gmCategoryPanel.setLayout(null);
		goodsCategorylist = new JList(goodsListModel);
		goodsCategorylist.setSelectedIndex(0);
		goodsCategorylist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				query();
			}
		});
		goodsCategorylist.setBounds(0, 0, 148, 413);
		goodsCategorylist.setBorder(BorderFactory.createEtchedBorder());
		gmCategoryPanel.add(goodsCategorylist);
	}

	/**
	 * �����¼�����
	 */
	private void saveActionPerformed(ActionEvent evt) {
		List<Boolean> ori = gmJTable.getOri();
		for (boolean isori : ori) {
			if (!isori) {
				if (gmJTable.save()) {
					JOptionPane.showMessageDialog(null, "���浽���ݿ�ɹ�");
					query();
					return;
				}
			}
		}
		List<Boolean> updated = gmJTable.getUpdated();
		boolean falg = true;
		String id = "";
		String name = null;
		for (int i = 0; i < updated.size(); i++) {
			if (updated.get(i)) {
				falg = false;
				id = gmJTable.getModel().getValueAt(i, 0).toString();
				if ((!StringUtil.isNumber(id)) || id.length() > 18) {
					JOptionPane.showMessageDialog(null, "��Ʒ���벻�ܴ���18λ���ұ���������");
					return;
				}
				name = gmJTable.getModel().getValueAt(i, 1).toString();
				if (goodsService.queryGoodsById(Integer.parseInt(id)) != null
						&& (!gmJTable.getids().get(i).toString().equals(id))) {
					JOptionPane.showMessageDialog(null, id + "  ��Ʒ�����Ѿ����ڣ��������޸�");
					return;
				} else if (goodsService.queryGoodsByName(name) != null
						&& (!gmJTable.getListGname().get(i).equals(name))) {
					JOptionPane.showMessageDialog(null, name + "  ��Ʒ�����Ѿ����ڣ��������޸�");
					return;
				}
			}
		}
		if (updated.isEmpty() || falg) {
			JOptionPane.showMessageDialog(null, "���޸ĺ��ٱ���");
			return;
		}

		if (gmJTable.save()) {
			JOptionPane.showMessageDialog(null, "����ɹ�");
			query();
		} else {
			JOptionPane.showMessageDialog(null, "����ʧ��,��ǰ��Ʒ��ʽ����ȷ���ߵ�ǰ��Ʒ���������Ӽ�¼���޷��޸�");
		}

	}

	/**
	 * ɾ����Ʒ�¼�����
	 */
	private void deleteGoodsActionPerformed(ActionEvent evt) {
		int si = gmJTable.getSelectedRow();
		if (si < 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��һ���ٽ���ɾ��");
			return;
		} else {
			if (JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ��", "ɾ��", JOptionPane.YES_NO_OPTION) == 0) {
				if (gmJTable.deleteRow()) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
					query();
				} else {
					JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
				}
			}
		}
	}

	/**
	 * ��ѯ��Ʒͨ������
	 */
	private void query() {
		String inquireRange = gmInquireRangeComboBox.getSelectedItem().toString();
		if (inquireRange.equals("ȫ����Ʒ")) {
			inquireRange = ">- 1";
		} else if (inquireRange.equals("����ʾ���Ϊ0 ����Ʒ")) {
			inquireRange = "!= 0";
		} else if (inquireRange.equals("��ʾ���Ϊ0 ����Ʒ")) {
			inquireRange = "= 0";
		} else if (inquireRange.equals("��ʾ���С��10����Ʒ")) {
			inquireRange = "< 10";
		}
		String category = goodsCategorylist.getSelectedValue().toString();
		if (category.equals("������Ʒ")) {
			category = "";
		}

		String inquireText = gmInquireTextField.getText();

		gmJTable.setList(goodsService.fuzzyQuery(inquireText, category, inquireRange));
	}
}
