package com.dao;

import java.util.List;

import com.entity.SalesInfo;

/**
 * ������Ϣ��Ӧ���ݿ�Sales_Info��
 * @author wuhong
 *
 */
public interface SalesInfoDao {
	
	/**
	 * �������۳��ⵥ�Ż�ȡ���ݿ��ж�Ӧ������
	 * @param salesid ���ⵥ��
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid);
	
	/**
	 * �����ݿ��л�ȡSalesInfo
	 * @return
	 */
	public List<SalesInfo> queryAll();
	
	/**
	 * �����ݿ��а�������ȡ��ҳ����
	 * @param curPage
	 * @param rowsPage
	 * @param fuzzyid
	 * @return
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid);
	
	/**
	 * ����salesid�Լ�gid��ѯ���ݿ�
	 * @param1  salesid  ���ⵥ���
	 * @param2  gid      ��Ʒ����
	 * @return SalesInfo����
	 */
	public List<SalesInfo> queryBySalesidAndGid(long salesid,int gid);
}
