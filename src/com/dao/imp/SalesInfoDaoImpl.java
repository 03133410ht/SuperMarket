package com.dao.imp;

import java.util.List;

import com.dao.BaseDao;
import com.dao.SalesInfoDao;
import com.entity.SalesInfo;

/**
 * ������Ϣ��Ӧ���ݿ�Sales_Info��
 * @author wuhong
 *
 */
public class SalesInfoDaoImpl extends BaseDao implements SalesInfoDao {
	/**
	 * �������۳��ⵥ�Ż�ȡ���ݿ��ж�Ӧ������
	 * @param salesid ���ⵥ��
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid){
		String sql="SELECT * FROM SALES_INFO WHERE SALESID >=0 ";
		return query(sql, SalesInfo.class, salesid);
	}
	
	/**
	 * �����ݿ��л�ȡSalesInfo
	 * @return
	 */
	public List<SalesInfo> queryAll(){
		String sql="SELECT * FROM SALES_INFO ORDER BY SALESID";
		return query(sql,SalesInfo.class);
	}
	
	/**
	 * �����ݿ��а�������ȡ��ҳ����
	 * @param curPage  ��ǰҳ
	 * @param rowsPage ÿҳ��
	 * @param fuzzyid  ģ���ֶ�
	 * @return   list
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid){
		String sql="SELECT * FROM (SELECT ROWNUM R,S.* FROM (SELECT * FROM SALES_INFO WHERE SALESID > 0 AND SALESID LIKE ?"
				+ " ORDER BY SALESID) S) PAGE WHERE  PAGE.R > ? AND PAGE.R <= ? ";
		return query(sql, SalesInfo.class,"%"+fuzzyid+"%",(curPage-1)*rowsPage,curPage*rowsPage); 
	}

	/**
	 * ����salesid�Լ�gid��ѯ���ݿ�
	 * @param1  salesid  ���ⵥ���
	 * @param2  gid      ��Ʒ����
	 * @return SalesInfo����
	 */
	public List<SalesInfo> queryBySalesidAndGid(long salesid, int gid) {
		String sql="SELECT * FROM SALES_INFO WHERE SALESID = ? AND GID = ?";
		return query(sql, SalesInfo.class, salesid,gid);
	}
}
