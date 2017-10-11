package com.dao;

import java.util.List;

import com.entity.Purchase;

/**
 * 采购单数据库操作接口
 * @author wuhong
 *
 */
public interface PurchaseDao {

	/**
	 * 新增Purchase对象到数据库
	 * @param purchase 新增的Purchase对象
	 * @return true/false
	 */
	public boolean addPurchase(Purchase purchase);
	
	/**
	 * 根据采购单号查找采购信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	public Purchase qureyPurchaseByPid(int pid);
	
	/**
	 * 根据供应商编号查找采购信息
	 * @param did 供应商编号
	 * @return  数据库中该供应商对应的所有采购信息的list
	 */
	public List<Purchase> qureyPurchaseByDid(int did);
	
	/**
	 * 根据采购单号删除采购信息
	 * @param pid 采购单号
	 * @return true/false   
	 */
	public boolean deletePurchaseById(int pid);
	
	/**
	 * 展示所有采购信息
	 * @return 数据库中所有采购信息的list
	 */
	public List<Purchase> queryAll();
	
	/**
	 * 根据采购单号更新数据库中采购信息
	 */
	public boolean update(int pid,Purchase purchase);
}
