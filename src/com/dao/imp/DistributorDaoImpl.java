package com.dao.imp;

import java.util.List;

import com.dao.BaseDao;
import com.dao.DistributorDao;
import com.entity.Distributor;

/**
 *供应商对应的dao，提供操作数据库供应商的方法
 *@author wuhong
 *
 */
public class DistributorDaoImpl extends BaseDao implements DistributorDao{
	
	/**
	 * 新增供应商
	 * @param distributor 供应商对象
	 * @return true/false
	 */
	@Override
	public boolean addDistributor(Distributor distributor) {
		String sql="INSERT INTO DISTRIBUTOR VALUES(?,?,?,?) ";
		return update(sql, distributor.getDid(),distributor.getDname(),distributor.getDtel(),distributor.getDemail());
	}

	/**
	 * 根据供应商编号删除供应商
	 * @param did  供应商编号 
	 * @return  true/false
	 */
	@Override
	public boolean deleteDistributor(int did) {
		String sql="DELETE FROM DISTRIBUTOR WHERE DID= ? ";
		return update(sql, did);
	}

	/**
	 * 查询所有供应商
	 * @return list
	 */
	@Override
	public List<Distributor> queryAll() {
		String sql="SELECT * FROM DISTRIBUTOR ORDER BY DID";
		return query(sql,Distributor.class);
	}

	/**
	 * 根据供应商编号查询供应商
	 * @param did
	 * @return Distributor对象
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
	 * 根据供应商名称查询供应商
	 * @param dname  供应商名称
	 * @return Distributor对象
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
	 * 模糊查询供应商
	 * @param fuzzy 模糊字段
	 * @return list
	 */
	public List<Distributor> queryByFuzzy(String fuzzy){
		String sql="SELECT * FROM DISTRIBUTOR WHERE DID like ? OR DNAME like ? OR DTEL like ? OR DEMAIL like ? ORDER BY DID";
		return query(sql, Distributor.class, "%"+fuzzy+"%","%"+fuzzy+"%","%"+fuzzy+"%","%"+fuzzy+"%");
	}
}
