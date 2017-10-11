package com.service.imp;

import java.util.List;

import com.dao.DistributorDao;
import com.dao.imp.DistributorDaoImpl;
import com.entity.Distributor;
import com.service.DistributorService;


/**
 * ����Dao���ṩ��view�����ݿ�����ķ���
 * @author wuhong
 *
 */
public class DistributorServiceImpl implements DistributorService{
	private DistributorDao ddi=new DistributorDaoImpl();
	/**
	 * ������Ӧ��
	 * @param distributor ��Ӧ�̶���
	 * @return true/false
	 */
	@Override
	public boolean addDistributor(Distributor distributor) {
		return ddi.addDistributor(distributor);
	}

	/**
	 * ���ݹ�Ӧ�̱��ɾ����Ӧ��
	 * @param did  ��Ӧ�̱�� 
	 * @return  true/false
	 */
	@Override
	public boolean deleteDistributor(int did) {
		if(did <= 0){
			return false;
		}
		return ddi.deleteDistributor(did);
	}

	/**
	 * ��ѯ���й�Ӧ��
	 */
	@Override
	public List<Distributor> queryAll() {
		return ddi.queryAll();
	}

	/**
	 * ����dao�㷽����ѯָ��did��Ӧ��
	 * @param did ��Ӧ�̱��
	 * @return Distributor����
	 */
	@Override
	public Distributor queryDistributorById(int did) {
		return ddi.queryDistributorById(did);
	}

	/**
	 * ���ݹ�Ӧ�����Ʋ�ѯ��Ӧ��
	 * @param dname  ��Ӧ������
	 * @return Distributor����
	 */
	public Distributor queryDistributorByName(String dname){
		return ddi.queryDistributorByName(dname);
	}

	/**
	 * ģ����ѯ��Ӧ��
	 * @param fuzzy ģ���ֶ�
	 * @return list
	 */
	@Override
	public List<Distributor> queryByFuzzy(String fuzzy) {
		return ddi.queryByFuzzy(fuzzy);
	}
}
