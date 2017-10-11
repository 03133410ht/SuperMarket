package com.service.imp;

import java.util.List;

import com.dao.DistributorDao;
import com.dao.imp.DistributorDaoImpl;
import com.entity.Distributor;
import com.service.DistributorService;


/**
 * 调用Dao层提供给view层数据库操作的方法
 * @author wuhong
 *
 */
public class DistributorServiceImpl implements DistributorService{
	private DistributorDao ddi=new DistributorDaoImpl();
	/**
	 * 新增供应商
	 * @param distributor 供应商对象
	 * @return true/false
	 */
	@Override
	public boolean addDistributor(Distributor distributor) {
		return ddi.addDistributor(distributor);
	}

	/**
	 * 根据供应商编号删除供应商
	 * @param did  供应商编号 
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
	 * 查询所有供应商
	 */
	@Override
	public List<Distributor> queryAll() {
		return ddi.queryAll();
	}

	/**
	 * 调用dao层方法查询指定did供应商
	 * @param did 供应商编号
	 * @return Distributor对象
	 */
	@Override
	public Distributor queryDistributorById(int did) {
		return ddi.queryDistributorById(did);
	}

	/**
	 * 根据供应商名称查询供应商
	 * @param dname  供应商名称
	 * @return Distributor对象
	 */
	public Distributor queryDistributorByName(String dname){
		return ddi.queryDistributorByName(dname);
	}

	/**
	 * 模糊查询供应商
	 * @param fuzzy 模糊字段
	 * @return list
	 */
	@Override
	public List<Distributor> queryByFuzzy(String fuzzy) {
		return ddi.queryByFuzzy(fuzzy);
	}
}
