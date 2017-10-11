package com.dao.imp;

import java.util.List;

import com.dao.BaseDao;
import com.dao.GoodsDao;
import com.dao.PurchaseInfoDao;
import com.entity.Purchase;
import com.entity.PurchaseInfo;

/**
 * PurchaseInfoDao的实现累
 * @author wuhong
 *
 */
public class PurchaseInfoDaoImpl extends BaseDao implements PurchaseInfoDao{
	private GoodsDao gdi=new GoodsDaoImp();
	
	/**
	 * 新增PurchaseInfo对象到数据库
	 * @param purchaseInfo 新增的PurchaseInfo对象
	 * @return true/false
	 */
	public boolean addPurchaseInfo(PurchaseInfo purchaseInfo){
		String sql="INSERT INTO PURCHASE_INFO VALUES(?,?,?)";
		return update(sql, purchaseInfo.getPid(),purchaseInfo.getGid(),purchaseInfo.getPnum());
	}
	
	/**
	 * 根据采购单号查找采购入库信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoInByPid(int pid) {
		String sql="SELECT * FROM PURCHASE_INFO WHERE PID = ? AND PNUM >= 0 ORDER BY PID";
		List<PurchaseInfo> purchaseInfoList=query(sql, PurchaseInfo.class, pid);
		if(purchaseInfoList.isEmpty()){
			return null;
		}
		return purchaseInfoList;
	}
	
	/**
	 * 根据采购单号查找采购出库信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoOutByPid(int pid) {
		String sql="SELECT * FROM PURCHASE_INFO WHERE PID = ? WHERE PNUM <=0 ORDER BY PID";
		List<PurchaseInfo> purchaseInfoList=query(sql, PurchaseInfo.class, pid);
		if(purchaseInfoList.isEmpty()){
			return null;
		}
		return purchaseInfoList;
	}
	
	/**
	 * 根据商品编号查找采购信息
	 * @param pid 采购单号
	 * @return 采购对象
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoByGid(int gid) {
		String sql="SELECT * FROM PURCHASE_INFO WHERE GID = ? ORDER BY PID";
		List<PurchaseInfo> purchaseInfoList=query(sql, PurchaseInfo.class, gid);
		if(purchaseInfoList.isEmpty()){
			return null;
		}
		return purchaseInfoList;
	}

	/**
	 * 根据供应商编号查找采购信息
	 * @param 供应商编号
	 * @return list
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoByDid(int did) {
		String sql="SELECT * FROM PURCHASE_INFO WHERE DID = ? ORDER BY PID";
		return query(sql, PurchaseInfo.class, did);
	}

	/**
	 * 根据商品id删除数据库中对应数据
	 * @param 采购id
	 * @return true/false
	 */
	@Override
	public boolean deletePurchaseInfoByGid(int gid) {
		String sql="DELETE FROM PURCHASE_INFO WHERE GID = ?";
		return update(sql, gid);
	}

	/**
	 * 查询所有采购入库信息数据
	 * @return list
	 */
	@Override
	public List<PurchaseInfo> queryAllIn() {
		String sql="SELECT * FROM PURCHASE_INFO WHERE PID >=0 ORDER BY PID";
		return query(sql, PurchaseInfo.class);
	}
	
	/**
	 * 查询所有采购出库信息数据
	 * @return list
	 */
	@Override
	public List<PurchaseInfo> queryAllOut() {
		String sql="SELECT * FROM PURCHASE_INFO WHERE PNUM <=0 ORDER BY PID";
		return query(sql, PurchaseInfo.class);
	}

	/**
	 * 按商品编号更新单次数据库中的数据
	 * @param gid     商品编号
	 * @param purchaseInfo  采购信息PurchaseInfo对象
	 * @return true/false
	 * 
	 */
	@Override
	public boolean update(int pid,int gid, PurchaseInfo purchaseInfo) {
		String sql="UPDATE PURCHASE_INFO SET PNUM=? WHERE PID = ? AND GID=? ";
		return update(sql, purchaseInfo.getPnum(),pid,gid);
	}
	
	/**
	 * 根据模糊pid查询数据库中采购入库purchaseInfo的内容
	 * @param fuzzypid 模糊pid
	 * @return list
	 */
	public List<PurchaseInfo> queryByFuzzyPid(int fuzzypid){
		String sql="SELECT * FROM PURCHASE_INFO WHERE PID like ? AND PID >= 0 ORDER BY PID";
		return query(sql, PurchaseInfo.class,"%"+fuzzypid+"%");
	}

	/**
	 * 根据pid，gid锁定数据库中purchaseInfo的内容
	 * @param int    pid
	 * @param int	 gid
	 * @return   PurchaseInfo对象
	 */
	public PurchaseInfo queryPurchaseInfoByPidAndGid(int pid, int gid) {
		String sql="SELECT * FROM PURCHASE_INFO WHERE PID = ? AND GID = ?";
		List<PurchaseInfo> purchaseInfos=query(sql, PurchaseInfo.class, pid,gid);
		if(purchaseInfos.isEmpty()){
			return null;
		}
		return query(sql, PurchaseInfo.class, pid,gid).get(0);
	}
}
