package com.dao;

import java.util.List;

import com.entity.PurchaseInfo;

/**
 * 采购数据库操作接口
 * @author wuhong
 *
 */
public interface PurchaseInfoDao {

	/**
	 * 新增PurchaseInfo对象到数据库
	 * @param purchaseInfo 新增的PurchaseInfo对象
	 * @return true/false
	 */
	public boolean addPurchaseInfo(PurchaseInfo purchaseInfo);
	
	/**
	 * 根据采购单号查找采购入库信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	public List<PurchaseInfo> qureyPurchaseInfoInByPid(int pid);
	
	/**
	 * 根据采购单号查找采购出库信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	public List<PurchaseInfo> qureyPurchaseInfoOutByPid(int pid);
	
	/**
	 * 根据商品编号查找采购信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	public List<PurchaseInfo> qureyPurchaseInfoByGid(int gid);
	
	/**
	 * 根据供应商编号查找采购信息
	 * @param did 供应商编号
	 * @return  数据库中该供应商对应的所有采购信息的list
	 */
	public List<PurchaseInfo> qureyPurchaseInfoByDid(int did);
	
	/**
	 * 根据商品号删除采购信息
	 * @param pid 采购单号
	 * @return true/false   
	 */
	public boolean deletePurchaseInfoByGid(int gid);
	
	/**
	 * 展示所有采购入库信息
	 * @return 数据库中所有采购入库信息的list
	 */
	public List<PurchaseInfo> queryAllIn();
	
	/**
	 * 展示所有采购出库信息
	 * @return 数据库中所有采购出库信息的list
	 */
	public List<PurchaseInfo> queryAllOut();
	
	/**
	 * 根据pid  gid  更新数据库purchaseInfo内容
	 * @param pid  采购编号
	 * @param gid  商品编号
	 * @param purchaseInfo  purchaseInfo对象
	 * @return   PurchaseInfo对象
	 */
	public boolean update(int pid,int gid,PurchaseInfo purchaseInfo);
	
	/**
	 * 根据模糊pid查询数据库中purchaseInfo的内容
	 * @param fuzzypid 模糊pid
	 * @return list
	 */
	public List<PurchaseInfo> queryByFuzzyPid(int fuzzypid);
	
	/**
	 * 根据pid，gid锁定数据库中purchaseInfo的内容
	 * @param int    pid
	 * @param int	 gid
	 * @return   PurchaseInfo对象
	 */
	public PurchaseInfo queryPurchaseInfoByPidAndGid(int pid, int gid);
}
