package com.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.entity.Member;
import com.entity.Sales;
import com.entity.SalesInfo;

/**
 * ��Ʒ������Ϣ��¼Service�ӿ�
 * @author Goddard
 *
 */
public interface SalesService {
	/**
	 * ��dao������󵥺Ų�ѯ��¼
	 * @return	sales���� Ϊ��û���ҵ�
	 */
	public Sales querySalesID(long gid) ; 
	
	
	/**
	 * ���һ�����ۼ�¼
	 * @param sales	���ۼ�¼
	 * @param mapGoods	��Ʒ����
	 * @return	�Ƿ���ӳɹ�
	 */
	public boolean addSales(Sales sales,Map<Integer,Integer> mapGoods); 
	
	/**
	 * ��ȡȫ��ҳ��
	 * @param 
	 * @return
	 */
	public Map<Sales,List<SalesInfo>>  getALLByMid(List<? extends Object> list,Date date1,Date date2);
	
	
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
	 * ��dao���ȡ�������ۼ�¼
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
