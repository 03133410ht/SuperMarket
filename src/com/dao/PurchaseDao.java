package com.dao;

import java.util.List;

import com.entity.Purchase;

/**
 * �ɹ������ݿ�����ӿ�
 * @author wuhong
 *
 */
public interface PurchaseDao {

	/**
	 * ����Purchase�������ݿ�
	 * @param purchase ������Purchase����
	 * @return true/false
	 */
	public boolean addPurchase(Purchase purchase);
	
	/**
	 * ���ݲɹ����Ų��Ҳɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public Purchase qureyPurchaseByPid(int pid);
	
	/**
	 * ���ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * @param did ��Ӧ�̱��
	 * @return  ���ݿ��иù�Ӧ�̶�Ӧ�����вɹ���Ϣ��list
	 */
	public List<Purchase> qureyPurchaseByDid(int did);
	
	/**
	 * ���ݲɹ�����ɾ���ɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return true/false   
	 */
	public boolean deletePurchaseById(int pid);
	
	/**
	 * չʾ���вɹ���Ϣ
	 * @return ���ݿ������вɹ���Ϣ��list
	 */
	public List<Purchase> queryAll();
	
	/**
	 * ���ݲɹ����Ÿ������ݿ��вɹ���Ϣ
	 */
	public boolean update(int pid,Purchase purchase);
}
