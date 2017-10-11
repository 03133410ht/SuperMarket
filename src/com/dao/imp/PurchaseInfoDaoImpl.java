package com.dao.imp;

import java.util.List;

import com.dao.BaseDao;
import com.dao.GoodsDao;
import com.dao.PurchaseInfoDao;
import com.entity.Purchase;
import com.entity.PurchaseInfo;

/**
 * PurchaseInfoDao��ʵ����
 * @author wuhong
 *
 */
public class PurchaseInfoDaoImpl extends BaseDao implements PurchaseInfoDao{
	private GoodsDao gdi=new GoodsDaoImp();
	
	/**
	 * ����PurchaseInfo�������ݿ�
	 * @param purchaseInfo ������PurchaseInfo����
	 * @return true/false
	 */
	public boolean addPurchaseInfo(PurchaseInfo purchaseInfo){
		String sql="INSERT INTO PURCHASE_INFO VALUES(?,?,?)";
		return update(sql, purchaseInfo.getPid(),purchaseInfo.getGid(),purchaseInfo.getPnum());
	}
	
	/**
	 * ���ݲɹ����Ų��Ҳɹ������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
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
	 * ���ݲɹ����Ų��Ҳɹ�������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
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
	 * ������Ʒ��Ų��Ҳɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
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
	 * ���ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * @param ��Ӧ�̱��
	 * @return list
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoByDid(int did) {
		String sql="SELECT * FROM PURCHASE_INFO WHERE DID = ? ORDER BY PID";
		return query(sql, PurchaseInfo.class, did);
	}

	/**
	 * ������Ʒidɾ�����ݿ��ж�Ӧ����
	 * @param �ɹ�id
	 * @return true/false
	 */
	@Override
	public boolean deletePurchaseInfoByGid(int gid) {
		String sql="DELETE FROM PURCHASE_INFO WHERE GID = ?";
		return update(sql, gid);
	}

	/**
	 * ��ѯ���вɹ������Ϣ����
	 * @return list
	 */
	@Override
	public List<PurchaseInfo> queryAllIn() {
		String sql="SELECT * FROM PURCHASE_INFO WHERE PID >=0 ORDER BY PID";
		return query(sql, PurchaseInfo.class);
	}
	
	/**
	 * ��ѯ���вɹ�������Ϣ����
	 * @return list
	 */
	@Override
	public List<PurchaseInfo> queryAllOut() {
		String sql="SELECT * FROM PURCHASE_INFO WHERE PNUM <=0 ORDER BY PID";
		return query(sql, PurchaseInfo.class);
	}

	/**
	 * ����Ʒ��Ÿ��µ������ݿ��е�����
	 * @param gid     ��Ʒ���
	 * @param purchaseInfo  �ɹ���ϢPurchaseInfo����
	 * @return true/false
	 * 
	 */
	@Override
	public boolean update(int pid,int gid, PurchaseInfo purchaseInfo) {
		String sql="UPDATE PURCHASE_INFO SET PNUM=? WHERE PID = ? AND GID=? ";
		return update(sql, purchaseInfo.getPnum(),pid,gid);
	}
	
	/**
	 * ����ģ��pid��ѯ���ݿ��вɹ����purchaseInfo������
	 * @param fuzzypid ģ��pid
	 * @return list
	 */
	public List<PurchaseInfo> queryByFuzzyPid(int fuzzypid){
		String sql="SELECT * FROM PURCHASE_INFO WHERE PID like ? AND PID >= 0 ORDER BY PID";
		return query(sql, PurchaseInfo.class,"%"+fuzzypid+"%");
	}

	/**
	 * ����pid��gid�������ݿ���purchaseInfo������
	 * @param int    pid
	 * @param int	 gid
	 * @return   PurchaseInfo����
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
