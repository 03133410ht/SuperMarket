package com.dao;

import java.util.List;

import com.entity.PurchaseInfo;

/**
 * �ɹ����ݿ�����ӿ�
 * @author wuhong
 *
 */
public interface PurchaseInfoDao {

	/**
	 * ����PurchaseInfo�������ݿ�
	 * @param purchaseInfo ������PurchaseInfo����
	 * @return true/false
	 */
	public boolean addPurchaseInfo(PurchaseInfo purchaseInfo);
	
	/**
	 * ���ݲɹ����Ų��Ҳɹ������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public List<PurchaseInfo> qureyPurchaseInfoInByPid(int pid);
	
	/**
	 * ���ݲɹ����Ų��Ҳɹ�������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public List<PurchaseInfo> qureyPurchaseInfoOutByPid(int pid);
	
	/**
	 * ������Ʒ��Ų��Ҳɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public List<PurchaseInfo> qureyPurchaseInfoByGid(int gid);
	
	/**
	 * ���ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * @param did ��Ӧ�̱��
	 * @return  ���ݿ��иù�Ӧ�̶�Ӧ�����вɹ���Ϣ��list
	 */
	public List<PurchaseInfo> qureyPurchaseInfoByDid(int did);
	
	/**
	 * ������Ʒ��ɾ���ɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return true/false   
	 */
	public boolean deletePurchaseInfoByGid(int gid);
	
	/**
	 * չʾ���вɹ������Ϣ
	 * @return ���ݿ������вɹ������Ϣ��list
	 */
	public List<PurchaseInfo> queryAllIn();
	
	/**
	 * չʾ���вɹ�������Ϣ
	 * @return ���ݿ������вɹ�������Ϣ��list
	 */
	public List<PurchaseInfo> queryAllOut();
	
	/**
	 * ����pid  gid  �������ݿ�purchaseInfo����
	 * @param pid  �ɹ����
	 * @param gid  ��Ʒ���
	 * @param purchaseInfo  purchaseInfo����
	 * @return   PurchaseInfo����
	 */
	public boolean update(int pid,int gid,PurchaseInfo purchaseInfo);
	
	/**
	 * ����ģ��pid��ѯ���ݿ���purchaseInfo������
	 * @param fuzzypid ģ��pid
	 * @return list
	 */
	public List<PurchaseInfo> queryByFuzzyPid(int fuzzypid);
	
	/**
	 * ����pid��gid�������ݿ���purchaseInfo������
	 * @param int    pid
	 * @param int	 gid
	 * @return   PurchaseInfo����
	 */
	public PurchaseInfo queryPurchaseInfoByPidAndGid(int pid, int gid);
}
