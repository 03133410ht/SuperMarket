package com.service.imp;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.dao.SalesDao;
import com.dao.imp.SalesDaoImp;
import com.entity.Goods;
import com.entity.Member;
import com.entity.Sales;
import com.entity.SalesInfo;
import com.service.SalesService;
/**
 * 商品销售信息记录Service实现类
 * @author Goddard
 *
 */
public class SalesServiceImp implements SalesService {
	private SalesDao salesDao = new	SalesDaoImp();
	/**
	 * 从dao层获得最大单号查询记录
	 * @return	sales对象 为空没有找到
	 */
	public Sales querySalesID(long gid) {
		Sales sales = null;
		List <Sales> list = salesDao.querySalesID(gid);
		if(list.size() > 0) {
			sales = list.get(list.size() - 1);
		}
		return sales;
	}
	/**
	 * 添加一条销售记录
	 * @param sales	销售记录
	 * @param mapGoods	商品集合
	 * @return	是否添加成功
	 */
	public boolean addSales(Sales sales, Map<Integer, Integer> mapGoods) {
		return salesDao.addSales(sales, mapGoods);
	}
	/**
	 * 获取全部页面
	 * @param 
	 * @return
	 */
	public Map<Sales, List<SalesInfo>>  getALLByMid(List<? extends Object> list,Date date1,Date date2) {
		return salesDao.getALLByMid(list, date1, date2);
	}
	
	
	/**
	 * 根据时间查询记录
	 * @return
	 */
	public Map<Sales, List<SalesInfo>> getALLByDate(Date date) {
		return salesDao.getALLByDate(date);
	}
	/**
	 * 查询前10销售的商品id和数量
	 * @return
	 */
	public Map<Integer, Integer> queryGid(Date date1 ,Date date2) {
		return salesDao.queryGid(date1,date2);
	}

	/**
	 * 从数据库中获取所有销售记录
	 * @return List<Sales>
	 */
	public List<Sales> queryAlls(){
		return salesDao.queryAlls();
	}
	
	/**
	 * 根据模糊salesid字段从数据控中获取Sales
	 * @param salesid 模糊字段 	
	 * @return List<Sales>
	 */
	public List<Sales> queryByFuzzyId(long salesid){
		return salesDao.queryByFuzzyId(salesid);
	}
	
	
	public boolean addSales(Sales sales, List<SalesInfo> salesInfos) {
		return salesDao.addSales(sales, salesInfos);
	}
	
}
