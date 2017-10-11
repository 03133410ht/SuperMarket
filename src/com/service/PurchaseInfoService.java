package com.service;

import java.util.List;

import com.entity.Purchase;
import com.entity.PurchaseInfo;

/**
 * �ɹ�Service�ӿ�
 * @author wuhong
 *
 */
public interface PurchaseInfoService {
	
	/**
	 * ����Dao������PurchaseInfo�������ݿ�
	 * @param purchaseInfo ������PurchaseInfo����
	 * @return true/false
	 */
	public boolean addPurchaseInfo(PurchaseInfo purchaseInfo);
	
	/**
	 * ����Dao����ݲɹ����Ų��Ҳɹ������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public List<PurchaseInfo> qureyPurchaseInfoInByPid(int pid);
	
	/**
	 * ����Dao����ݲɹ����Ų��Ҳɹ�������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public List<PurchaseInfo> qureyPurchaseInfoOutByPid(int pid);
	
	/**
	 * ����Dao�������Ʒ��Ų��Ҳɹ������Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public List<PurchaseInfo> qureyPurchaseInfoByGid(int gid);
	
	/**
	 * ����Dao����ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * @param did ��Ӧ�̱��
	 * @return  ���ݿ��иù�Ӧ�̶�Ӧ�����вɹ���Ϣ��list
	 */
	public List<PurchaseInfo> qureyPurchaseInfoByDid(int did);
	
	/**
	 * ����Dao����ݲɹ�����ɾ���ɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return true/false   
	 */
	public boolean deletePurchaseInfoByGid(int gid);
	
	/**
	 * ����Dao��չʾ���вɹ������Ϣ
	 * @return ���ݿ������вɹ������Ϣ��list
	 */
	public List<PurchaseInfo> queryAllIn();
	
	/**
	 * ����Dao��չʾ���вɹ�������Ϣ
	 * @return ���ݿ������вɹ�������Ϣ��list
	 */
	public List<PurchaseInfo> queryAllOut();
	
	/**
	 * ����Dao����ݲɹ����Ÿ������ݿ��вɹ���Ϣ
	 * @param pid   �ɹ����
	 * @param gid   ��Ʒ���
	 * @param purchaseInfo �ɹ���Ϣ����
	 */
	public boolean update(int pid,int gid,PurchaseInfo purchaseInfo);
	
	/**
	 * ����ģ��pid��ѯ���ݿ���purchaseInfo������
	 * @param fuzzypid ģ��pid
	 * @return list
	 */
	public List<PurchaseInfo> queryByFuzzyPid(int fuzzypid);
	
	/**
	 * ����pid��gid����dao�㷽���������ݿ���purchaseInfo������
	 * @param int    pid
	 * @param int	 gid
	 * @return   PurchaseInfo����
	 */
	public PurchaseInfo qureyPurchaseInfoInByPidAndGid(int pid, int gid);
}
