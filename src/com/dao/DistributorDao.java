package com.dao;

import java.util.List;

import com.entity.Distributor;
/**
 *供应商对应的dao，提供操作数据库供应商的方法
 *@author wuhong
 *
 */
public interface DistributorDao {
	
	/**
	 * 新增供应商
	 * @param distributor 供应商对象
	 * @return true/false
	 */
	public boolean addDistributor(Distributor distributor);
	
	/**
	 * 根据供应商编号删除供应商
	 * @param did  供应商编号 
	 * @return  true/false
	 */
	public boolean deleteDistributor(int did);
	
	/**
	 * 查询所有供应商
	 * @return list
	 */
	public List<Distributor> queryAll();
	
	/**
	 * 根据供应商编号查询供应商
	 * @param did
	 * @return Distributor对象
	 */
	public Distributor queryDistributorById(int did);
	
	/**
	 * 根据供应商名称查询供应商
	 * @param dname  供应商名称
	 * @return Distributor对象
	 */
	public Distributor queryDistributorByName(String dname);
	
	/**
	 * 模糊查询供应商
	 * @param fuzzy 模糊字段
	 * @return list
	 */
	public List<Distributor> queryByFuzzy(String fuzzy); 
}
