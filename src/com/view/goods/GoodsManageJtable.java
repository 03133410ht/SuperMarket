package com.view.goods;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.entity.Goods;
import com.service.GoodsService;
import com.service.imp.GoodsServiceImp;
import com.util.StringUtil;

/**
 * 商品管理表格
 * 
 * @author Goddard
 *
 */
public class GoodsManageJtable extends JTable {
	private int gid;
	private String gname;
	private double gprice;
	private double gcost_price;
	private int gstock;
	private double gdiscount;
	private String gcategory;
	private int gpoint;
	private String gunit;
	private String gremark;

	private static final String[] COLUMN_NAMES = { "商品条码", "\u5546\u54C1\u540D\u79F0",
			"\u5355\u4F4D", "\u6210\u672C\u4EF7", "\u552E\u4EF7", "\u5E93\u5B58\u6570\u91CF", "\u6298\u6263\u7387",
			"\u7C7B\u522B", "\u5355\u4E2A\u79EF\u5206", "\u5907\u6CE8"};
	private List<Goods> goodsList;
	private List<Boolean> ori;// 判断这行是不是数据库中的
	private List<Boolean> updated;// 判断是不是修改过
	private List<Integer> ids;// 存储编号id
	private List<String>  listGname; //存储商品名称
	private GoodsService goodsService = new GoodsServiceImp();
	private Object[][] o;

	
	public List<Boolean> getOri() {
		return ori;
	}

	public String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	public Object[][] getO() {
		return o;
	}

	public GoodsManageJtable(List<Goods> goodsList) {
		initialize(goodsList);
	}

	private void initialize(List<Goods> goodsList) {
		this.goodsList = goodsList;
		this.ori = new ArrayList<Boolean>();
		this.updated = new ArrayList<Boolean>();
		this.ids = new ArrayList<Integer>();
		this.listGname = new ArrayList<String>();
		o = new Object[goodsList.size()][10];
		int i = 0;
		for (Goods goods : goodsList) {// 将数据保存到数组中
			o[i][0] = goods.getGid();
			o[i][1] = goods.getGname();
			o[i][2] = goods.getGunit();
			o[i][3] = goods.getGcost_price();
			o[i][4] = goods.getGprice();
			o[i][5] = goods.getGstock();
			o[i][6] = goods.getGdiscount();
			o[i][7] = goods.getGcategory();
			o[i][8] = goods.getGpoint();
			o[i][9] = goods.getGremark();
			ori.add(true);// 这行在数据库中
			updated.add(false);// 没有修改过
			ids.add(goods.getGid());// 得到编号
			listGname.add(goods.getGname());//得到名字
			++i;
		}
		this.setModel(new DefaultTableModel(o, COLUMN_NAMES));
		((DefaultTableModel) this.getModel()).addTableModelListener(new TableModelListener() {// 判断这行是不是被修改过
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					updated.set(e.getLastRow(), true);
				}
			}

		});
		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(0).setResizable(false);
		getColumnModel().getColumn(1).setPreferredWidth(180);
		getColumnModel().getColumn(1).setMaxWidth(250);
		// getColumnModel().getColumn(1).setResizable(false);
		getColumnModel().getColumn(2).setResizable(false);
		getColumnModel().getColumn(3).setResizable(false);
		getColumnModel().getColumn(4).setResizable(false);
		getColumnModel().getColumn(5).setResizable(false);
		getColumnModel().getColumn(6).setResizable(false);
		getColumnModel().getColumn(7).setResizable(false);
		// getColumnModel().getColumn(8).setResizable(false);
		getColumnModel().getColumn(9).setResizable(false);
	}

	/**
	 * 设置表格内容
	 */
	public void setList(List<Goods> goodsList) {
		initialize(goodsList);
	}

	/**
	 * 删除某一行
	 * 
	 * @return 是否删除成功
	 */
	public boolean deleteRow() {
		int si = this.getSelectedRow();// 首先得到这是哪一行
		if(!ori.get(si)) {
			((DefaultTableModel) this.getModel()).removeRow(si);
			updated.remove(si);
			listGname.remove(si);
			ori.remove(si);
			ids.remove(si);// 去掉所有的信息
			return true;
		}
		boolean flag = goodsService.deleteById(ids.get(si));
		if (flag) {
			((DefaultTableModel) this.getModel()).removeRow(si);
			updated.remove(si);
			listGname.remove(si);
			ori.remove(si);
			ids.remove(si);// 去掉所有的信息
		}
		return flag;
	}

	/**
	 * 添加一个空行
	 */
	public void addRow(Object [][] obj) {
		this.ori = new ArrayList<Boolean>();
		this.updated = new ArrayList<Boolean>();
		this.ids = new ArrayList<Integer>();
		Object [][] obj1 = new Object[obj.length][10];
		for(int i = 0;i<obj.length;i++) {
			obj1[i] = obj[i];
			ori.add(false);
			updated.add(false);
			listGname.add(null);
			ids.add(null);
		}
		this.setModel(new DefaultTableModel(obj1, COLUMN_NAMES));
		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(0).setResizable(false);
		getColumnModel().getColumn(1).setPreferredWidth(180);
		getColumnModel().getColumn(1).setMaxWidth(250);
		// getColumnModel().getColumn(1).setResizable(false);
		getColumnModel().getColumn(2).setResizable(false);
		getColumnModel().getColumn(3).setResizable(false);
		getColumnModel().getColumn(4).setResizable(false);
		getColumnModel().getColumn(5).setResizable(false);
		getColumnModel().getColumn(6).setResizable(false);
		getColumnModel().getColumn(7).setResizable(false);
		// getColumnModel().getColumn(8).setResizable(false);
		getColumnModel().getColumn(9).setResizable(false);
		
		
	}

	/**
	 * 保存修改
	 * 
	 * @return 是否保存成功
	 */
	public boolean save() {
		boolean flag = false;
		for (int i = 0; i < ori.size(); i++) {
			if (!ori.get(i)) {
				if(setProperty(i)) {
					return flag;
				}
				flag = goodsService.addGoods(new Goods(gid, gname, gprice, gcost_price, gstock, gdiscount, gcategory,
						gpoint, gunit, gremark));
				
			}

			if (updated.get(i)) {
				if(setProperty(i)) {
					return flag;
				}
				flag = goodsService.update(ids.get(i), new Goods(gid, gname, gprice, gcost_price, gstock, gdiscount,
						gcategory, gpoint, gunit, gremark));
			}
		}
		return flag;
	}
	
	/**
	 * 清空表格
	 */
	public void deleteAll() {
		while(getRowCount() != 0) {
			((DefaultTableModel) this.getModel()).removeRow(0);
			updated.remove(0);
			listGname.remove(0);
			ori.remove(0);
			ids.remove(0);// 去掉所有的信息
		}
	}

	/**
	 * 获取 是否存在 修改集合
	 * 
	 * @return 布尔集合
	 */
	public List<Boolean> getUpdated() {
		return updated;
	}
	
	/**
	 * 获取老编号id
	 * @return	老编号集合
	 */
	public List<Integer> getids(){
		return ids;
	}
	
	/**
	 * 获取老名称
	 * @return	老名称集合
	 */
	public List<String> getListGname(){
		return listGname;
	}
	
	/**
	 * 设置属性	
	 * @param i	行数
	 */
	private boolean setProperty(int i) {
		String id = getValueAt(i, 0).toString();
		if ((!StringUtil.isNumber(id)) || id.length()>18) {
			JOptionPane.showMessageDialog(null, "商品条码不能大于18位，且必须是数字");
			return true;
		}
		gid = Integer.parseInt(id.trim());

		
		gname = (String) getValueAt(i, 1);
		if (StringUtil.isEmpty(gname) || gname.length()>25) {
			JOptionPane.showMessageDialog(null, "商品名称不能大于25位，且不能为空");
			return true;
		}
		gname = gname.trim();

		
		String price = getValueAt(i, 4).toString();
		if (!StringUtil.isPrice(price)) {
			JOptionPane.showMessageDialog(null, "售价格式不正确，最多10位数字，且只能有2位小数");
			return true;
		}
		gprice = Double.parseDouble(price.trim());

		
		String cost_price = getValueAt(i, 3).toString();
		if (!StringUtil.isPrice(cost_price)) {
			JOptionPane.showMessageDialog(null, "成本价格式不正确，最多10位数字，且只能有2位小数");
			return true;
		}
		gcost_price = Double.parseDouble(cost_price.trim());

		
		String stock = getValueAt(i, 5).toString();
		if (!StringUtil.isNumber(stock)) {
			JOptionPane.showMessageDialog(null, "库存数量格式不正确，最多10位数字");
			return true;
		}
		gstock = Integer.parseInt(stock.trim());

		
		String discount = getValueAt(i, 6).toString();
		if (!discount.matches("(0\\.\\d{1,2})|1(\\.\\d{0,10})?")) {
			JOptionPane.showMessageDialog(null, "折扣率不正确，只能0.01-1之间");
			return true;
		}
		gdiscount = Double.parseDouble(discount.trim());

		
		gcategory = (String) getValueAt(i, 7);
		if (StringUtil.isEmpty(gcategory) || gcategory.length()>10) {
			JOptionPane.showMessageDialog(null, "类别不能为空，且长度不能大于10");
			return true;
		}
		gcategory = gcategory.trim();

		
		String point = getValueAt(i, 8).toString();
		if ((!StringUtil.isNumber(point)) || point.length() >10) {
			JOptionPane.showMessageDialog(null, "积分只能为数字，且长度不能大于10");
			return true;
		}
		gpoint = Integer.parseInt(point.trim());

		
		gunit = (String) getValueAt(i, 2);
		if (StringUtil.isEmpty(gunit) || gunit.length()>10) {
			JOptionPane.showMessageDialog(null, "单位不能为空，且长度不能大于10");
			return true;
		}
		gunit = gunit.trim();

		gremark = (String) getValueAt(i, 9);
		if (!StringUtil.isEmpty(gremark)) {
			gremark = gremark.trim();
		}
		
		return false;
	}
}
