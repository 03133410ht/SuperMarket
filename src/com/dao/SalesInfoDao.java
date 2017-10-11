package com.dao;

import java.util.List;

import com.entity.SalesInfo;

/**
 * 销售信息对应数据库Sales_Info表
 * @author wuhong
 *
 */
public interface SalesInfoDao {
	
	/**
	 * 根据销售出库单号获取数据库中对应出单号
	 * @param salesid 出库单号
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid);
	
	/**
	 * 从数据库中获取SalesInfo
	 * @return
	 */
	public List<SalesInfo> queryAll();
	
	/**
	 * 从数据库中按条件获取分页数据
	 * @param curPage
	 * @param rowsPage
	 * @param fuzzyid
	 * @return
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid);
	
	/**
	 * 根据salesid以及gid查询数据库
	 * @param1  salesid  出库单编号
	 * @param2  gid      商品条码
	 * @return SalesInfo对象
	 */
	public List<SalesInfo> queryBySalesidAndGid(long salesid,int gid);
}
