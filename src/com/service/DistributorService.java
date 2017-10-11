package com.service;

import java.util.List;

import com.entity.Distributor;

/**
 * ��Ӧ��Service�ӿ�
 * @author wuhong
 *
 */
public interface DistributorService {
	
	/**
	 * ������Ӧ��
	 * @param distributor ��Ӧ�̶���
	 * @return true/false
	 */
	public boolean addDistributor(Distributor distributor);
	
	/**
	 * ���ݹ�Ӧ�̱��ɾ����Ӧ��
	 * @param did  ��Ӧ�̱�� 
	 * @return  true/false
	 */
	public boolean deleteDistributor(int did);
	
	/**
	 * ��ѯ���й�Ӧ��
	 * @return
	 */
	public List<Distributor> queryAll();

	/**
	 * ���ݹ�Ӧ�̱�Ų�ѯ��Ӧ��
	 * @param did ��Ӧ�̱��
	 * @return Distributor����
	 */
	public Distributor queryDistributorById(int did);

	/**
	 * ���ݹ�Ӧ�����Ʋ�ѯ��Ӧ��
	 * @param dname  ��Ӧ������
	 * @return Distributor����
	 */
	public Distributor queryDistributorByName(String dname);
	
	/**
	 * ģ����ѯ��Ӧ��
	 * @param fuzzy ģ���ֶ�
	 * @return list
	 */
	public List<Distributor> queryByFuzzy(String fuzzy); 
}
