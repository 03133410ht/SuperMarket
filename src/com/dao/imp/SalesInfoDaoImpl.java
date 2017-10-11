package com.dao.imp;

import java.util.List;

import com.dao.BaseDao;
import com.dao.SalesInfoDao;
import com.entity.SalesInfo;

/**
 * 销售信息对应数据库Sales_Info表
 * @author wuhong
 *
 */
public class SalesInfoDaoImpl extends BaseDao implements SalesInfoDao {
	/**
	 * 根据销售出库单号获取数据库中对应出单号
	 * @param salesid 出库单号
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid){
		String sql="SELECT * FROM SALES_INFO WHERE SALESID >=0 ";
		return query(sql, SalesInfo.class, salesid);
	}
	
	/**
	 * 从数据库中获取SalesInfo
	 * @return
	 */
	public List<SalesInfo> queryAll(){
		String sql="SELECT * FROM SALES_INFO ORDER BY SALESID";
		return query(sql,SalesInfo.class);
	}
	
	/**
	 * 从数据库中按条件获取分页数据
	 * @param curPage  当前页
	 * @param rowsPage 每页数
	 * @param fuzzyid  模糊字段
	 * @return   list
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid){
		String sql="SELECT * FROM (SELECT ROWNUM R,S.* FROM (SELECT * FROM SALES_INFO WHERE SALESID > 0 AND SALESID LIKE ?"
				+ " ORDER BY SALESID) S) PAGE WHERE  PAGE.R > ? AND PAGE.R <= ? ";
		return query(sql, SalesInfo.class,"%"+fuzzyid+"%",(curPage-1)*rowsPage,curPage*rowsPage); 
	}

	/**
	 * 根据salesid以及gid查询数据库
	 * @param1  salesid  出库单编号
	 * @param2  gid      商品条码
	 * @return SalesInfo对象
	 */
	public List<SalesInfo> queryBySalesidAndGid(long salesid, int gid) {
		String sql="SELECT * FROM SALES_INFO WHERE SALESID = ? AND GID = ?";
		return query(sql, SalesInfo.class, salesid,gid);
	}
}
