package com.dao.imp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.dao.BaseDao;
import com.dao.PurchaseDao;
import com.entity.Purchase;
import com.entity.PurchaseInfo;

/**
 * PurchaseDao的实现累
 * 
 * @author wuhong
 *
 */
public class PurchaseDaoImpl extends BaseDao implements PurchaseDao {

	/**
	 * 新增Purchase对象到数据库
	 * @param purchase 新增的Purchase对象
	 * @return true/false
	 */
	public boolean addPurchase(Purchase purchase){
		String sql="INSERT INTO PURCHASE VALUES(?,?,?,?,?) ";
		return update(sql, purchase.getPid(),purchase.getSid(),new Date(purchase.getPdate().getTime()),purchase.getRemark(),purchase.getDid());
	}

	/**
	 * 根据采购单号查找采购信息
	 * 
	 * @param pid
	 *            采购单号
	 * @return 采购对象
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
	 * 根据供应商编号查找采购信息
	 * 
	 * @param 供应商编号
	 * @return list
	 */
	@Override
	public List<Purchase> qureyPurchaseByDid(int did) {
		String sql = "SELECT * FROM PURCHASE WHERE DID = ? ORDER BY PID";
		return query(sql, Purchase.class, did);
	}

	/**
	 * 根据采购id删除数据库中对应数据
	 * 
	 * @param 采购id
	 * @return true/false
	 */
	@Override
	public boolean deletePurchaseById(int pid) {
		String sql = "DELETE FROM PURCHASE WHERE PID = ?";
		return update(sql, pid);
	}

	/**
	 * 查询所有采购信息数据
	 * 
	 * @return list
	 */
	@Override
	public List<Purchase> queryAll() {
		String sql = "SELECT * FROM PURCHASE ORDER BY PID";
		return query(sql, Purchase.class);
	}

	/**
	 * 按编号更新数据库中的数据
	 * 
	 * @param pid
	 *            采购编号
	 * @param purchaseInfo
	 *            采购信息PurchaseInfo对象
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
