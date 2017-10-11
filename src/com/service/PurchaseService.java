package com.service;

import java.util.List;

import com.entity.Purchase;

/**
 * �ɹ���Service�ӿ�
 * @author wuhong
 *
 */
public interface PurchaseService {

	/**
	 * ����Dao������Purchase�������ݿ�
	 * @param purchase ������Purchase����
	 * @return true/false
	 */
	public boolean addPurchase(Purchase purchase);
	
	/**
	 * ����Dao����ݲɹ����Ų��Ҳɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	public Purchase qureyPurchaseByPid(int pid);
	
	/**
	 * ����Dao����ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * @param did ��Ӧ�̱��
	 * @return  ���ݿ��иù�Ӧ�̶�Ӧ�����вɹ���Ϣ��list
	 */
	public List<Purchase> qureyPurchaseByDid(int did);
	
	/**
	 * ����Dao����ݲɹ�����ɾ���ɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return true/false   
	 */
	public boolean deletePurchaseById(int pid);
	
	/**
	 * ����Dao��չʾ���вɹ���Ϣ
	 * @return ���ݿ������вɹ���Ϣ��list
	 */
	public List<Purchase> queryAll();
	
	/**
	 * ����Dao����ݲɹ����Ÿ������ݿ��вɹ���Ϣ
	 */
	public boolean update(int pid,Purchase purchase);
}
