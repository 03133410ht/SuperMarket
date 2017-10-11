package com.view.desk;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.entity.Goods;
import com.util.StringUtil;

/**
 * 商品销售表格
 * 
 * @author Goddard
 *
 */
public class SaleGoodsJtable extends JTable {
	private static final String[] COLUMN_NAMES = { "商品条码", "\u5546\u54C1\u540D\u79F0", "\u5355\u4F4D", "\u5355\u4EF7",
			"\u6570\u91CF", "\u6298\u6263\u7387", "\u91D1\u989D", "积分" };
	private Object[][] o;
	private int i;
	private Map<Integer, Integer> gidRow;// 存储商品id和行数
	private DecimalFormat df;
	private List<Integer> pointList;
	private List<Goods> goodsList;

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	

	public SaleGoodsJtable() {
		goodsJable();
	}

	private void goodsJable() {
		df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		o = new Object[0][8];
		pointList = new ArrayList<Integer>();
		gidRow = new HashMap<Integer, Integer>();
		goodsList = new ArrayList<Goods>();
		this.setModel(new DefaultTableModel(o, COLUMN_NAMES) {
			public boolean isCellEditable(int row, int column) {
				if (column == 4) {
					return true;
				} else {
					return false;
				}
			}
		});

		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(0).setResizable(false);
		getColumnModel().getColumn(1).setResizable(false);
		getColumnModel().getColumn(2).setResizable(false);
		getColumnModel().getColumn(3).setResizable(false);
		getColumnModel().getColumn(4).setResizable(false);
		getColumnModel().getColumn(5).setResizable(false);
		getColumnModel().getColumn(6).setResizable(false);
		getColumnModel().getColumn(7).setResizable(false);

	}

	/**
	 * 添加一个空行并添加一个商品
	 */
	public void addRow(Goods goods) {
		if(goods.getGstock() == 0) {
			JOptionPane.showMessageDialog(null, "库存为0，请联系管理员");
			return;
		}
		for (Map.Entry<Integer, Integer> entry : gidRow.entrySet()) {
			if (goods.getGid() == entry.getKey()) {
				int num = Integer.parseInt(getModel().getValueAt(entry.getValue(), 4).toString());
				if ((num + 1) > goodsList.get(entry.getValue()).getGstock()) {
					JOptionPane.showMessageDialog(null, goodsList.get(entry.getValue()).getGname() + "库存数量为"
							+ goodsList.get(entry.getValue()).getGstock() + ",库存不足，请管理员修改!");
					getModel().setValueAt(goodsList.get(entry.getValue()).getGstock(), entry.getValue(), 4);
					return;
				}
				getModel().setValueAt(num + 1, entry.getValue(), 4);
				getModel().setValueAt((num + 1) * goods.getGpoint(), entry.getValue(), 7);
				getModel().setValueAt(
						df.format(Double.parseDouble(getModel().getValueAt(entry.getValue(), 3).toString()) * num
								* Double.parseDouble(getModel().getValueAt(entry.getValue(), 5).toString())),
						entry.getValue(), 6);
				return;
			}
		}
		((DefaultTableModel) getModel()).addRow(new Object[9]);// 加入空行

		getModel().setValueAt(goods.getGid(), i, 0);
		getModel().setValueAt(goods.getGname(), i, 1);
		getModel().setValueAt(goods.getGunit(), i, 2);
		getModel().setValueAt(goods.getGprice(), i, 3);
		getModel().setValueAt(1, i, 4);
		getModel().setValueAt(goods.getGdiscount(), i, 5);
		getModel().setValueAt(df.format(Double.parseDouble(getModel().getValueAt(i, 3).toString())
				* Integer.parseInt(getModel().getValueAt(i, 4).toString())
				* Double.parseDouble(getModel().getValueAt(i, 5).toString())), i, 6);
		getModel().setValueAt(goods.getGpoint(), i, 7);
		gidRow.put(goods.getGid(), i);
		pointList.add(goods.getGpoint());
		goodsList.add(goods);
		++i;

	}

	/**
	 * 删除某一行
	 * 
	 * 
	 */
	public void deleteRow() {
		int si = this.getSelectedRow();// 首先得到这是哪一行
		gidRow.remove(getModel().getValueAt(si, 0));
		pointList.remove(si);
		goodsList.remove(si);
		((DefaultTableModel) this.getModel()).removeRow(si);
		--i;
	}

	/**
	 * 更新表内数据
	 */
	public void refresh() {
		int row = getSelectedRow();
		if (row == -1) {
			return;
		}
		if(goodsList.get(row).getGstock() == 0) {
			JOptionPane.showMessageDialog(null, "库存为0，请联系管理员");
			return;
		}
		if(!StringUtil.isNumber(getModel().getValueAt(row, 4).toString())) {
			JOptionPane.showMessageDialog(null, "只能输入数字！！！！！");
			return;
		}
		
		if (Integer.parseInt(getModel().getValueAt(row, 4).toString()) > goodsList.get(row).getGstock()) {
			JOptionPane.showMessageDialog(null,
					goodsList.get(row).getGname() + "库存数量为" + goodsList.get(row).getGstock() + ",库存不足，请管理员修改!");
			getModel().setValueAt(goodsList.get(row).getGstock(), row, 4);
			return;
		}
		int num = Integer.parseInt(getModel().getValueAt(row, 4).toString());
		getModel().setValueAt(num, row, 4);
		getModel().setValueAt(df.format(Double.parseDouble(getModel().getValueAt(row, 3).toString()) * num
				* Double.parseDouble(getModel().getValueAt(row, 5).toString())), row, 6);
		getModel().setValueAt(num * pointList.get(row), row, 7);
	}

	/**
	 * 清空表格
	 */
	public void deleteAll() {
		while(getRowCount() != 0) {
			gidRow.remove(getModel().getValueAt(0, 0));
			pointList.remove(0);
			goodsList.remove(0);
			((DefaultTableModel) this.getModel()).removeRow(0);
		}
		i = 0;

	}

}
