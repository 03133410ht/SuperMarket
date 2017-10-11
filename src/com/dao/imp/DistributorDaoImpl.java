package com.dao.imp;

import java.util.List;

import com.dao.BaseDao;
import com.dao.DistributorDao;
import com.entity.Distributor;

/**
 *��Ӧ�̶�Ӧ��dao���ṩ�������ݿ⹩Ӧ�̵ķ���
 *@author wuhong
 *
 */
public class DistributorDaoImpl extends BaseDao implements DistributorDao{
	
	/**
	 * ������Ӧ��
	 * @param distributor ��Ӧ�̶���
	 * @return true/false
	 */
	@Override
	public boolean addDistributor(Distributor distributor) {
		String sql="INSERT INTO DISTRIBUTOR VALUES(?,?,?,?) ";
		return update(sql, distributor.getDid(),distributor.getDname(),distributor.getDtel(),distributor.getDemail());
	}

	/**
	 * ���ݹ�Ӧ�̱��ɾ����Ӧ��
	 * @param did  ��Ӧ�̱�� 
	 * @return  true/false
	 */
	@Override
	public boolean deleteDistributor(int did) {
		String sql="DELETE FROM DISTRIBUTOR WHERE DID= ? ";
		return update(sql, did);
	}

	/**
	 * ��ѯ���й�Ӧ��
	 * @return list
	 */
	@Override
	public List<Distributor> queryAll() {
		String sql="SELECT * FROM DISTRIBUTOR ORDER BY DID";
		return query(sql,Distributor.class);
	}

	/**
	 * ���ݹ�Ӧ�̱�Ų�ѯ��Ӧ��
	 * @param did
	 * @return Distributor����
	 */
	public Distributor queryDistributorById(int did){
		String sql="SELECT * FROM DISTRIBUTOR WHERE DID = ?";
		Distributor distributor=null;
		if(!query(sql,Distributor.class,did).isEmpty()){
			distributor=query(sql,Distributor.class,did).get(0);
		}
		return distributor;
	}
	
	/**
	 * ���ݹ�Ӧ�����Ʋ�ѯ��Ӧ��
	 * @param dname  ��Ӧ������
	 * @return Distributor����
	 */
	public Distributor queryDistributorByName(String dname){
		String sql="SELECT * FROM DISTRIBUTOR WHERE DNAME = ?";
		Distributor distributor=null;
		if(!query(sql,Distributor.class,dname).isEmpty()){
			distributor=query(sql,Distributor.class,dname).get(0);
		}
		return distributor;
	}
	
	/**
	 * ģ����ѯ��Ӧ��
	 * @param fuzzy ģ���ֶ�
	 * @return list
	 */
	public List<Distributor> queryByFuzzy(String fuzzy){
		String sql="SELECT * FROM DISTRIBUTOR WHERE DID like ? OR DNAME like ? OR DTEL like ? OR DEMAIL like ? ORDER BY DID";
		return query(sql, Distributor.class, "%"+fuzzy+"%","%"+fuzzy+"%","%"+fuzzy+"%","%"+fuzzy+"%");
	}
}
