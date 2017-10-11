package com.service;

import java.util.List;

import com.entity.SalesInfo;

/**
 * �ɹ���ϢService�ӿ�
 * @author wuhong
 *
 */
public interface SalesInfoService {
	

	/**
	 * �������۳��ⵥ�Ż�ȡ���ݿ��ж�Ӧ������
	 * @param salesid ���ⵥ��
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid);
	
	
	/**
	 * ��dao���а�������ȡ��ҳ����
	 * @param curPage
	 * @param rowsPage
	 * @param fuzzyid
	 * @return
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid);
	
	/**
	 * ��dao���ȡ����SalesInfo���۳�������
	 * @return list
	 */
	public List<SalesInfo> queryAll();
	
	/**
	 * ����salesid�Լ�gid��dao���ѯ����
	 * @parm1 salesid    ���۳�����
	 * @parm2 gid        ��Ʒ����
	 * @return salesinfo ����
	 */
	public SalesInfo queryBySalesidAndGid(long salesid,int gid);
}

