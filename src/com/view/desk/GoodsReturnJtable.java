package com.view.desk;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.entity.Goods;
import com.entity.SalesInfo;
import com.service.GoodsService;
import com.service.SalesInfoService;
import com.service.SalesService;
import com.service.imp.GoodsServiceImp;
import com.service.imp.SalesInfoServiceImpl;
import com.service.imp.SalesServiceImp;

/**
 * 销售退货表格
 * 
 * @author Goddard
 *
 */
public class GoodsReturnJtable extends JTable {
	private final String[] COLUMN_NAME = { "出库单号", "商品条码", "商品名称", "单位", "单价", "数量", "折扣率", "金额", "单品积分", "退货数量" };
	private SalesInfoService salesInfoService = new SalesInfoServiceImpl();
	private GoodsService goodsService = new GoodsServiceImp();
	private SalesService salesService = new SalesServiceImp();

	private Object[][] objArr;
	private int[] rnum;// 记录退货数量
	private List<SalesInfo> salesInfolist;
	private int curPage = 1;// 当前页
	private final int ROWSPAGE = 7;// 每页可以显示rowsPage条数据
	private int maxPage = 1;

	public GoodsReturnJtable(List<SalesInfo> salesInfolist) {
		this.salesInfolist = salesInfolist;
		showTable();
		goodsReturn();
	}

	private void goodsReturn() {
		setModel(new DefaultTableModel(objArr, COLUMN_NAME) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
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
		getColumnModel().getColumn(8).setResizable(false);
		getColumnModel().getColumn(9).setResizable(false);
	}

	/**
	 * 展示传递过来的集合的数据到表中
	 */
	private void showTable() {
		objArr = new Object[salesInfolist.size()][10];
		rnum = new int[salesInfolist.size()];
		int i = 0;
		for (SalesInfo salesInfo : salesInfolist) {
			objArr[i][0] = salesInfo.getSalesid();
			objArr[i][1] = salesInfo.getGid();
			objArr[i][2] = goodsService.queryGoodsById(salesInfo.getGid()).getGname();
			objArr[i][3] = goodsService.queryGoodsById(salesInfo.getGid()).getGunit();
			objArr[i][4] = goodsService.queryGoodsById(salesInfo.getGid()).getGprice();
			objArr[i][5] = salesInfo.getSnum();
			objArr[i][6] = goodsService.queryGoodsById(salesInfo.getGid()).getGdiscount();
			objArr[i][7] = goodsService.queryGoodsById(salesInfo.getGid()).getGprice() * salesInfo.getSnum()
					* goodsService.queryGoodsById(salesInfo.getGid()).getGdiscount();
			objArr[i][8] = goodsService.queryGoodsById(salesInfo.getGid()).getGpoint();
			objArr[i][9] = rnum[i];
			i++;
		}
	}

	/**
	 * 选中页码重置当前表格
	 */
	public void showagain(int page, long fuzzyid) {
		curPage = page;
		if (salesInfoService.queryPage(1, 999999, fuzzyid).size() % ROWSPAGE == 0) {
			maxPage = salesInfoService.queryPage(1, 999999, fuzzyid).size() / ROWSPAGE;
		} else {
			maxPage = salesInfoService.queryPage(1, 999999, fuzzyid).size() / ROWSPAGE + 1;
		}
		this.salesInfolist = salesInfoService.queryPage(page, ROWSPAGE, fuzzyid);
		showTable();
		goodsReturn();
	}

	/**
	 * 通过模糊id查询重置当前表格
	 */
	public void showagain(long fuzzyid) {
		this.removeAll();
		this.salesInfolist = salesInfoService.queryPage(1, 99999, fuzzyid);
		curPage = 1;
		maxPage = 1;
		showTable();
		goodsReturn();
	}

	/**
	 * 得到当前页码
	 */
	public int getCurpage() {
		return curPage;
	}

	/**
	 * 得到选中行的退货数量
	 */
	public int getRnum() {
		return rnum[this.getSelectedRow()];
	}

	/**
	 * 得到最大页数
	 * 
	 * @return int
	 */
	public int getMaxPage() {
		return maxPage;
	}

}
