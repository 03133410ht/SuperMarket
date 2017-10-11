package com.service.imp;

import java.util.List;

import com.dao.PurchaseInfoDao;
import com.dao.imp.PurchaseInfoDaoImpl;
import com.entity.PurchaseInfo;
import com.service.PurchaseInfoService;

/**
 * 调用Dao层提供给view层数据库操作的方法
 * @author wuhong
 *
 */
public class PurchaseInfoServiceImpl implements PurchaseInfoService{
	private PurchaseInfoDao pod=new PurchaseInfoDaoImpl();
	
	/**
	 * 新增PurchaseInfo对象到数据库
	 * @param purchaseInfo 新增的PurchaseInfo对象
	 * @return true/false
	 */
	public boolean addPurchaseInfo(PurchaseInfo purchaseInfo){
		if(purchaseInfo==null){
			return false;
		}
		return pod.addPurchaseInfo(purchaseInfo);
	}
	
	/**
	 * 根据采购单号查找采购入库信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoInByPid(int pid) {
		return pod.qureyPurchaseInfoInByPid(pid);
	}
	
	/**
	 * 根据采购单号查找采购出库信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoOutByPid(int pid) {
		return pod.qureyPurchaseInfoOutByPid(pid);
	}
	
	/**
	 * 根据商品编号查找采购入库信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoByGid(int gid) {
		return pod.qureyPurchaseInfoByGid(gid);
	}

	/**
	 * 根据供应商编号查找采购信息
	 * @param did 供应商编号
	 * @return  数据库中该供应商对应的所有采购信息的list
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoByDid(int did) {
		return pod.qureyPurchaseInfoByDid(did);
	}

	/**
	 * 根据采购单号删除采购信息
	 * @param pid 采购单号
	 * @return true/false   
	 */
	@Override
	public boolean deletePurchaseInfoByGid(int gid) {
		return pod.deletePurchaseInfoByGid(gid);
	}

	/**
	 * 展示所有采购入库信息
	 * @return 数据库中所有采购入库信息的list
	 */
	@Override
	public List<PurchaseInfo> queryAllIn() {
		return pod.queryAllIn();
	}

	/**
	 * 展示所有采购出库信息
	 * @return 数据库中所有采购出信息的list
	 */
	@Override
	public List<PurchaseInfo> queryAllOut() {
		return pod.queryAllOut();
	}
	
	/**
	 * 根据采购单号更新数据库中采购信息
	 * @param pid   采购编号
	 * @param purchaseInfo 采购信息对象
	 */
	@Override
	public boolean update(int pid,int gid, PurchaseInfo purchaseInfo) {
		return pod.update(pid,gid, purchaseInfo);
	}

	/**
	 * 调用dao层方法，模糊查询PurchaseInfo
	 * @param   fuzzypid  模糊pid
	 * @return   List
	 */
	public List<PurchaseInfo> queryByFuzzyPid(int fuzzypid) {
		return pod.queryByFuzzyPid(fuzzypid);
	}

	/**
	 * 根据pid，gid调用dao层方法锁定数据库中purchaseInfo的内容
	 * @param int    pid
	 * @param int	 gid
	 * @return   PurchaseInfo对象
	 */
	public PurchaseInfo qureyPurchaseInfoInByPidAndGid(int pid, int gid) {
		return pod.queryPurchaseInfoByPidAndGid(pid,gid);
	}
}
