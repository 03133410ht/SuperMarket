package com.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.entity.Member;
import com.entity.Sales;
import com.entity.SalesInfo;

/**
 * ��Ʒ������Ϣ��¼�ӿ�
 * @author Goddard
 *
 */
public interface SalesDao {
	/**
	 * �����ݿ���ݵ��Ų�ѯ��¼
	 * @return sales���󼯺�
	 */
	public List<Sales> querySalesID(long gid) ; 
	
	/**
	 * ���һ�����ۼ�¼
	 * @param sales	���ۼ�¼
	 * @param mapGoods	��Ʒ����
	 * @return	�Ƿ���ӳɹ�
	 */
	public boolean addSales(Sales sales,Map<Integer,Integer> mapGoods); 
	/**
	 * ��ȡȫ��ҳ��
	 * @param curPage ��ǰҳ
	 * @return
	 */
	public Map<Sales,List<SalesInfo>> getALLByMid(List<? extends Object> list,Date date1,Date date2) ;
	
	
	/**
	 * ����ʱ���ѯ��¼
	 * @return
	 */
	public Map<Sales, List<SalesInfo>> getALLByDate(Date date);
	
	

	/**
	 * ��ѯǰ10���۵���Ʒid������
	 * @return
	 */
	public Map<Integer, Integer> queryGid(Date date1 ,Date date2);
	/**
	 * �����ݿ��л�ȡ�������ۼ�¼
	 * @return List<Sales>
	 */
	public List<Sales> queryAlls();
	
	/**
	 * ����ģ��salesid�ֶδ����ݿ��л�ȡSales
	 * @param salesid ģ���ֶ� 	
	 * @return List<Sales>
	 */
	public List<Sales> queryByFuzzyId(long salesid);

	public boolean addSales(Sales sales,List<SalesInfo> salesInfos);
}
