package com.view.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.entity.Goods;
import com.entity.PurchaseInfo;
import com.service.GoodsService;
import com.service.PurchaseInfoService;
import com.service.imp.GoodsServiceImp;
import com.service.imp.PurchaseInfoServiceImpl;

/**
 * 采购退货表格
 * 
 * @author wuhong
 *
 */
public class PusrchaseOutJtable extends JTable {
	private List<PurchaseInfo> purchaseInfos;
	private Object[][] objArr;
	private List<Boolean> updated;// 判断是不是修改过
	private String[] pnum;
	private List<Integer> ids;
	private List<Integer> pds;
	private PurchaseInfoService pos = new PurchaseInfoServiceImpl();
	private GoodsService goodsService = new GoodsServiceImp();

	private final String[] POJ_COLUMN_NAMES = new String[] { "采购单号", "商品条码", "商品名称", "单位", "成本价", "库存", "入库数量","出库数量" };// 表头信息

	public PusrchaseOutJtable(List<PurchaseInfo> purchaseInfos) {
		purchaseOut(purchaseInfos);
	}

	private void purchaseOut(List<PurchaseInfo> purchaseInfos) {
		this.purchaseInfos = purchaseInfos;
		updated = new ArrayList<Boolean>();
		objArr = new Object[purchaseInfos.size()][8];
		ids = new ArrayList<Integer>();
		pds = new ArrayList<Integer>();
		pnum = new String[purchaseInfos.size()];
		int i = 0;
		for (PurchaseInfo pi : purchaseInfos) {
			objArr[i][0] = pi.getPid();
			objArr[i][1] = pi.getGid();
			objArr[i][2] = goodsService.queryGoodsById(pi.getGid()).getGname();
			objArr[i][3] = goodsService.queryGoodsById(pi.getGid()).getGunit();
			objArr[i][4] = goodsService.queryGoodsById(pi.getGid()).getGcost_price();
			objArr[i][5] = goodsService.queryGoodsById(pi.getGid()).getGstock();
			objArr[i][6] = pi.getPnum();
			objArr[i][7] = pnum[i];
			ids.add(pi.getGid());// 得到编号
			pds.add(pi.getPid());
			updated.add(false);// 没有修改过
			i++;
		}

		this.setModel(new DefaultTableModel(objArr, POJ_COLUMN_NAMES) {
			boolean[] columnEditables = { false, false, false, false, false, false, false,true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		((DefaultTableModel) this.getModel()).addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					updated.set(e.getLastRow(), true);
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
	}

	/**
	 * 设置表格内容
	 */
	public void setList(List<PurchaseInfo> purchaseInfos) {
		purchaseOut(purchaseInfos);
	}
	
	/**
	 * 删除某一行
	 * 
	 * @return 是否删除成功
	 */
	public boolean deleteRow() {
		int si = this.getSelectedRow();// 首先得到这是哪一行
		boolean flag = goodsService.deleteById(ids.get(si));
		if (flag) {
			((DefaultTableModel) this.getModel()).removeRow(si);
			updated.remove(si);
			ids.remove(si);// 去掉所有的信息
		}
		return flag;
	}

	/**
	 * 获取 是否存在 修改集合
	 * 
	 * @return list集合
	 */
	public boolean getUpdated(int row) {
		return updated.get(row);
	}
	
	
	/**
	 * 设置属性	
	 * @param i	行数
	 */
	private void setProperty(int i) {
		pnum[i] = (String)getValueAt(i, 5);
	}
	
	/**
	 * 得到选中行的pid值
	 * @return  pid值
	 */
	public int getSelectPid(){
		int si = this.getSelectedRow();
		if(si<0){
			return 0;
		}
		return pds.get(si);
	}
	
	/**
	 * 得到选中的商品对象
	 * @return 商品对象
	 */
	public Goods getSelectGoods(){
		int si = this.getSelectedRow();
		if(si<0){
			return null;
		}
		return goodsService.queryGoodsById(ids.get(si));
	}
	
	/**
	 * 得到选中行对应的PurchaseInfo入库单信息
	 * @return  PurchaseInfo对象
	 */
	public PurchaseInfo getSelectPurchaseInfo(){
		int si = this.getSelectedRow();
		if(si<0){
			return null;
		}
		return pos.qureyPurchaseInfoInByPidAndGid(pds.get(si),ids.get(si));
	}
	
	/**
	 * 得到展示的List<PurchaseInfo>
	 * @return  List<PurchaseInfo>
	 */
	public List<PurchaseInfo> getPurchaseInfos(){
		return purchaseInfos;
	}
}
