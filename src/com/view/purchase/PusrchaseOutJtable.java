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
 * �ɹ��˻����
 * 
 * @author wuhong
 *
 */
public class PusrchaseOutJtable extends JTable {
	private List<PurchaseInfo> purchaseInfos;
	private Object[][] objArr;
	private List<Boolean> updated;// �ж��ǲ����޸Ĺ�
	private String[] pnum;
	private List<Integer> ids;
	private List<Integer> pds;
	private PurchaseInfoService pos = new PurchaseInfoServiceImpl();
	private GoodsService goodsService = new GoodsServiceImp();

	private final String[] POJ_COLUMN_NAMES = new String[] { "�ɹ�����", "��Ʒ����", "��Ʒ����", "��λ", "�ɱ���", "���", "�������","��������" };// ��ͷ��Ϣ

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
			ids.add(pi.getGid());// �õ����
			pds.add(pi.getPid());
			updated.add(false);// û���޸Ĺ�
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
	 * ���ñ������
	 */
	public void setList(List<PurchaseInfo> purchaseInfos) {
		purchaseOut(purchaseInfos);
	}
	
	/**
	 * ɾ��ĳһ��
	 * 
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteRow() {
		int si = this.getSelectedRow();// ���ȵõ�������һ��
		boolean flag = goodsService.deleteById(ids.get(si));
		if (flag) {
			((DefaultTableModel) this.getModel()).removeRow(si);
			updated.remove(si);
			ids.remove(si);// ȥ�����е���Ϣ
		}
		return flag;
	}

	/**
	 * ��ȡ �Ƿ���� �޸ļ���
	 * 
	 * @return list����
	 */
	public boolean getUpdated(int row) {
		return updated.get(row);
	}
	
	
	/**
	 * ��������	
	 * @param i	����
	 */
	private void setProperty(int i) {
		pnum[i] = (String)getValueAt(i, 5);
	}
	
	/**
	 * �õ�ѡ���е�pidֵ
	 * @return  pidֵ
	 */
	public int getSelectPid(){
		int si = this.getSelectedRow();
		if(si<0){
			return 0;
		}
		return pds.get(si);
	}
	
	/**
	 * �õ�ѡ�е���Ʒ����
	 * @return ��Ʒ����
	 */
	public Goods getSelectGoods(){
		int si = this.getSelectedRow();
		if(si<0){
			return null;
		}
		return goodsService.queryGoodsById(ids.get(si));
	}
	
	/**
	 * �õ�ѡ���ж�Ӧ��PurchaseInfo��ⵥ��Ϣ
	 * @return  PurchaseInfo����
	 */
	public PurchaseInfo getSelectPurchaseInfo(){
		int si = this.getSelectedRow();
		if(si<0){
			return null;
		}
		return pos.qureyPurchaseInfoInByPidAndGid(pds.get(si),ids.get(si));
	}
	
	/**
	 * �õ�չʾ��List<PurchaseInfo>
	 * @return  List<PurchaseInfo>
	 */
	public List<PurchaseInfo> getPurchaseInfos(){
		return purchaseInfos;
	}
}
