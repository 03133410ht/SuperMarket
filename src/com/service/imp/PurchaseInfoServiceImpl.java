package com.service.imp;

import java.util.List;

import com.dao.PurchaseInfoDao;
import com.dao.imp.PurchaseInfoDaoImpl;
import com.entity.PurchaseInfo;
import com.service.PurchaseInfoService;

/**
 * ����Dao���ṩ��view�����ݿ�����ķ���
 * @author wuhong
 *
 */
public class PurchaseInfoServiceImpl implements PurchaseInfoService{
	private PurchaseInfoDao pod=new PurchaseInfoDaoImpl();
	
	/**
	 * ����PurchaseInfo�������ݿ�
	 * @param purchaseInfo ������PurchaseInfo����
	 * @return true/false
	 */
	public boolean addPurchaseInfo(PurchaseInfo purchaseInfo){
		if(purchaseInfo==null){
			return false;
		}
		return pod.addPurchaseInfo(purchaseInfo);
	}
	
	/**
	 * ���ݲɹ����Ų��Ҳɹ������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoInByPid(int pid) {
		return pod.qureyPurchaseInfoInByPid(pid);
	}
	
	/**
	 * ���ݲɹ����Ų��Ҳɹ�������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoOutByPid(int pid) {
		return pod.qureyPurchaseInfoOutByPid(pid);
	}
	
	/**
	 * ������Ʒ��Ų��Ҳɹ������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoByGid(int gid) {
		return pod.qureyPurchaseInfoByGid(gid);
	}

	/**
	 * ���ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * @param did ��Ӧ�̱��
	 * @return  ���ݿ��иù�Ӧ�̶�Ӧ�����вɹ���Ϣ��list
	 */
	@Override
	public List<PurchaseInfo> qureyPurchaseInfoByDid(int did) {
		return pod.qureyPurchaseInfoByDid(did);
	}

	/**
	 * ���ݲɹ�����ɾ���ɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return true/false   
	 */
	@Override
	public boolean deletePurchaseInfoByGid(int gid) {
		return pod.deletePurchaseInfoByGid(gid);
	}

	/**
	 * չʾ���вɹ������Ϣ
	 * @return ���ݿ������вɹ������Ϣ��list
	 */
	@Override
	public List<PurchaseInfo> queryAllIn() {
		return pod.queryAllIn();
	}

	/**
	 * չʾ���вɹ�������Ϣ
	 * @return ���ݿ������вɹ�����Ϣ��list
	 */
	@Override
	public List<PurchaseInfo> queryAllOut() {
		return pod.queryAllOut();
	}
	
	/**
	 * ���ݲɹ����Ÿ������ݿ��вɹ���Ϣ
	 * @param pid   �ɹ����
	 * @param purchaseInfo �ɹ���Ϣ����
	 */
	@Override
	public boolean update(int pid,int gid, PurchaseInfo purchaseInfo) {
		return pod.update(pid,gid, purchaseInfo);
	}

	/**
	 * ����dao�㷽����ģ����ѯPurchaseInfo
	 * @param   fuzzypid  ģ��pid
	 * @return   List
	 */
	public List<PurchaseInfo> queryByFuzzyPid(int fuzzypid) {
		return pod.queryByFuzzyPid(fuzzypid);
	}

	/**
	 * ����pid��gid����dao�㷽���������ݿ���purchaseInfo������
	 * @param int    pid
	 * @param int	 gid
	 * @return   PurchaseInfo����
	 */
	public PurchaseInfo qureyPurchaseInfoInByPidAndGid(int pid, int gid) {
		return pod.queryPurchaseInfoByPidAndGid(pid,gid);
	}
}
