package com.service.imp;

import java.util.List;

import com.dao.SalesInfoDao;
import com.dao.imp.SalesInfoDaoImpl;
import com.entity.SalesInfo;
import com.service.SalesInfoService;


/**
 * 调用Dao层提供给view层数据库操作的方法
 * @author wuhong
 *
 */
public class SalesInfoServiceImpl implements SalesInfoService {
	private SalesInfoDao sif=new SalesInfoDaoImpl();

	/**
	 * 根据销售出库单号获取dao层对应出单号
	 * @param salesid 出库单号
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid){
		if(salesid<=0){
			return null;
		}
		return sif.queryBySalesId(salesid);
	}
	
	/**
	 * 从数据库中按条件获取分页数据
	 * @param curPage  当前页
	 * @param rowsPage 每页数
	 * @param fuzzyid  模糊字段
	 * @return   list
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid){
		return sif.queryPage(curPage, rowsPage, fuzzyid);
	}
	
	/**
	 * 从dao层获取所有销售出库信息
	 */
	public List<SalesInfo> queryAll(){
		return sif.queryAll();
	}
	

	/**
	 * 根据salesid以及gid从dao层查询数据
	 * @parm1 salesid    销售出货单
	 * @parm2 gid        商品条码
	 * @return salesinfo 对象
	 */
	public SalesInfo queryBySalesidAndGid(long salesid,int gid){
		if(sif.queryBySalesidAndGid(salesid, gid).size() <= 0){
			return null;
		}
		return sif.queryBySalesidAndGid(salesid, gid).get(0);
	}
}
