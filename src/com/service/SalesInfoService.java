package com.service;

import java.util.List;

import com.entity.SalesInfo;

/**
 * 采购信息Service接口
 * @author wuhong
 *
 */
public interface SalesInfoService {
	

	/**
	 * 根据销售出库单号获取数据库中对应出单号
	 * @param salesid 出库单号
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid);
	
	
	/**
	 * 从dao层中按条件获取分页数据
	 * @param curPage
	 * @param rowsPage
	 * @param fuzzyid
	 * @return
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid);
	
	/**
	 * 从dao层获取所有SalesInfo销售出库数据
	 * @return list
	 */
	public List<SalesInfo> queryAll();
	
	/**
	 * 根据salesid以及gid从dao层查询数据
	 * @parm1 salesid    销售出货单
	 * @parm2 gid        商品条码
	 * @return salesinfo 对象
	 */
	public SalesInfo queryBySalesidAndGid(long salesid,int gid);
}

