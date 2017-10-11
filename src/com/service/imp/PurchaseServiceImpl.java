package com.service.imp;

import java.util.List;

import com.dao.PurchaseDao;
import com.dao.imp.PurchaseDaoImpl;
import com.entity.Purchase;
import com.entity.PurchaseInfo;
import com.service.PurchaseService;

/**
 * ����Dao���ṩ��view�����ݿ�����ķ���
 * @author wuhong
 *
 */
public class PurchaseServiceImpl implements PurchaseService{
	private PurchaseDao pod=new PurchaseDaoImpl();
	
	/**
	 * ����Purchase�������ݿ�
	 * @param purchase ������Purchase����
	 * @return true/false
	 */
	public boolean addPurchase(Purchase purchase){
		if(purchase==null){
			return false;
		}
		return pod.addPurchase(purchase);
	}
	
	/**
	 * ���ݲɹ����Ų��Ҳɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return �ɹ�����
	 */
	@Override
	public Purchase qureyPurchaseByPid(int pid) {
		return pod.qureyPurchaseByPid(pid);
	}

	/**
	 * ���ݹ�Ӧ�̱�Ų��Ҳɹ���Ϣ
	 * @param did ��Ӧ�̱��
	 * @return  ���ݿ��иù�Ӧ�̶�Ӧ�����вɹ���Ϣ��list
	 */
	@Override
	public List<Purchase> qureyPurchaseByDid(int did) {
		return pod.qureyPurchaseByDid(did);
	}

	/**
	 * ���ݲɹ�����ɾ���ɹ���Ϣ
	 * @param pid �ɹ�����
	 * @return true/false   
	 */
	@Override
	public boolean deletePurchaseById(int pid) {
		return pod.deletePurchaseById(pid);
	}

	/**
	 * չʾ���вɹ���Ϣ
	 * @return ���ݿ������вɹ���Ϣ��list
	 */
	@Override
	public List<Purchase> queryAll() {
		return pod.queryAll();
	}

	/**
	 * ���ݲɹ����Ÿ������ݿ��вɹ���Ϣ
	 * @param pid   �ɹ����
	 * @param purchaseInfo �ɹ���Ϣ����
	 */
	@Override
	public boolean update(int pid, Purchase purchase) {
		return pod.update(pid, purchase);
	}

	
	
}
