package com.view.purchase;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.entity.Goods;
import com.service.GoodsService;
import com.service.PurchaseInfoService;
import com.service.imp.GoodsServiceImp;
import com.service.imp.PurchaseInfoServiceImpl;
import com.util.KeyTypeUtil;
import com.util.StringUtil;

/**
 * �ɹ������
 * 
 * @author wuhong
 *
 */
@SuppressWarnings("all")
public class PusrchaseInJtable extends JTable {
	private final String [] COLUMN_NAMES={"��Ʒ����", "��Ʒ����", "��λ", "���", "�ɱ���", "�������"};
	private Object [] [] objArr;
	private String [] pnum;
	private GoodsService goodsService = new GoodsServiceImp();
	private PurchaseInfoService pos=new PurchaseInfoServiceImpl();
	private List<Goods> goodsList;
	private List<Boolean> updated;
	private List<Integer> ids;// �洢���id
	
	public PusrchaseInJtable(List<Goods> goodsList) {
		purchaseIn(goodsList);
	}

	private void purchaseIn(List<Goods> goodsList) {
		this.goodsList = goodsList;
		updated = new ArrayList<Boolean>();
		ids = new ArrayList<Integer>();
		objArr = new Object[goodsList.size()][6];
		pnum=new String[goodsList.size()];
		int i=0;
		for (Goods goods : goodsList) {
			objArr[i][0]=goods.getGid();
			objArr[i][1]=goods.getGname();
			objArr[i][2]=goods.getGunit();
			objArr[i][3]=goods.getGstock();
			objArr[i][4]=goods.getGcost_price();
			objArr[i][5]=pnum[i];
			updated.add(false);// û���޸Ĺ�
			ids.add(goods.getGid());// �õ����
			++i;
		}
		
		setModel(new DefaultTableModel(objArr,COLUMN_NAMES){
			boolean[] columnEditables ={ false, false, false, false, false, true};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			
		});
		((DefaultTableModel) this.getModel()).addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					updated.set(e.getLastRow(), true);
					setProperty(e.getLastRow());
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
	 * �õ�Goodslist
	 * @return list
	 */
	public List<Goods> getGoodsList(){
		return goodsList;
	}

	/**
	 * ���ñ������
	 */
	public void setList(List<Goods> goodsList) {
		purchaseIn(goodsList);
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
}
