package com.dao.imp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.dao.BaseDao;
import com.dao.PurchaseDao;
import com.entity.Purchase;
import com.entity.PurchaseInfo;

/**
 * PurchaseDao��ʵ����
 * 
 * @author wuhong
 *
 */
public class PurchaseDaoImpl extends BaseDao implements PurchaseDao {

	/**
	 * ����Purchase�������ݿ�
	 * @param purchase ������Purchase����
	 * @return true/false
	 */
	public boolean addPurchase(Purchase purchase){
		String sql="INSERT INTO PURCHASE VALUES(?,?,?,?,?) ";
		return update(sql, purchase.getPid(),purchase.getSid(),new Date(purchase.getPdate().getTime()),purchase.getRemark(),purchase.getDid());
	}

	/**
	 * ���ݲɹ����Ų��Ҳɹ���Ϣ
	 * 
	 * @param pid
	 *            �ɹ�����
	 * @return �ɹ�����
	 */
	@Override
	public Purchase qureyPurchaseByPid(int pid) {
		String sql = "SELECT * FROM PURCHASE WHERE PID = ?";
		List<Purchase> purchaseList = query(sql, Purchase.class, pid);
		if (purchaseList.isEmpty()) {
			return null;
		}
		return purchaseList.get(0);
	}

	/**
	 * ���ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * 
	 * @param ��Ӧ�̱��
	 * @return list
	 */
	@Override
	public List<Purchase> qureyPurchaseByDid(int did) {
		String sql = "SELECT * FROM PURCHASE WHERE DID = ? ORDER BY PID";
		return query(sql, Purchase.class, did);
	}

	/**
	 * ���ݲɹ�idɾ�����ݿ��ж�Ӧ����
	 * 
	 * @param �ɹ�id
	 * @return true/false
	 */
	@Override
	public boolean deletePurchaseById(int pid) {
		String sql = "DELETE FROM PURCHASE WHERE PID = ?";
		return update(sql, pid);
	}

	/**
	 * ��ѯ���вɹ���Ϣ����
	 * 
	 * @return list
	 */
	@Override
	public List<Purchase> queryAll() {
		String sql = "SELECT * FROM PURCHASE ORDER BY PID";
		return query(sql, Purchase.class);
	}

	/**
	 * ����Ÿ������ݿ��е�����
	 * 
	 * @param pid
	 *            �ɹ����
	 * @param purchaseInfo
	 *            �ɹ���ϢPurchaseInfo����
	 * @return true/false
	 * 
	 */
	@Override
	public boolean update(int pid, Purchase purchase) {
		String sql = "UPDATE PURCHASE SET SID = ?,DID = ?,PDATE = ?,REMARK = ? " + " WHERE PID=? ";
		return update(sql, purchase.getSid(), purchase.getDid(), new Date(purchase.getPdate().getTime()),
				purchase.getRemark(), purchase.getPid());
	}

}
