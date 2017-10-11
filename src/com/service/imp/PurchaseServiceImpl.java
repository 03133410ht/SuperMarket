package com.service.imp;

import java.util.List;

import com.dao.PurchaseDao;
import com.dao.imp.PurchaseDaoImpl;
import com.entity.Purchase;
import com.entity.PurchaseInfo;
import com.service.PurchaseService;

/**
 * 调用Dao层提供给view层数据库操作的方法
 * @author wuhong
 *
 */
public class PurchaseServiceImpl implements PurchaseService{
	private PurchaseDao pod=new PurchaseDaoImpl();
	
	/**
	 * 新增Purchase对象到数据库
	 * @param purchase 新增的Purchase对象
	 * @return true/false
	 */
	public boolean addPurchase(Purchase purchase){
		if(purchase==null){
			return false;
		}
		return pod.addPurchase(purchase);
	}
	
	/**
	 * 根据采购单号查找采购信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	@Override
	public Purchase qureyPurchaseByPid(int pid) {
		return pod.qureyPurchaseByPid(pid);
	}

	/**
	 * 根据供应商编号查找采购信息
	 * @param did 供应商编号
	 * @return  数据库中该供应商对应的所有采购信息的list
	 */
	@Override
	public List<Purchase> qureyPurchaseByDid(int did) {
		return pod.qureyPurchaseByDid(did);
	}

	/**
	 * 根据采购单号删除采购信息
	 * @param pid 采购单号
	 * @return true/false   
	 */
	@Override
	public boolean deletePurchaseById(int pid) {
		return pod.deletePurchaseById(pid);
	}

	/**
	 * 展示所有采购信息
	 * @return 数据库中所有采购信息的list
	 */
	@Override
	public List<Purchase> queryAll() {
		return pod.queryAll();
	}

	/**
	 * 根据采购单号更新数据库中采购信息
	 * @param pid   采购编号
	 * @param purchaseInfo 采购信息对象
	 */
	@Override
	public boolean update(int pid, Purchase purchase) {
		return pod.update(pid, purchase);
	}

	
	
}
