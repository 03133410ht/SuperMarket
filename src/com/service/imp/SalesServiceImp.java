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
 * ��Ʒ������Ϣ��¼Serviceʵ����
 * @author Goddard
 *
 */
public class SalesServiceImp implements SalesService {
	private SalesDao salesDao = new	SalesDaoImp();
	/**
	 * ��dao������󵥺Ų�ѯ��¼
	 * @return	sales���� Ϊ��û���ҵ�
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
	 * ���һ�����ۼ�¼
	 * @param sales	���ۼ�¼
	 * @param mapGoods	��Ʒ����
	 * @return	�Ƿ���ӳɹ�
	 */
	public boolean addSales(Sales sales, Map<Integer, Integer> mapGoods) {
		return salesDao.addSales(sales, mapGoods);
	}
	/**
	 * ��ȡȫ��ҳ��
	 * @param 
	 * @return
	 */
	public Map<Sales, List<SalesInfo>>  getALLByMid(List<? extends Object> list,Date date1,Date date2) {
		return salesDao.getALLByMid(list, date1, date2);
	}
	
	
	/**
	 * ����ʱ���ѯ��¼
	 * @return
	 */
	public Map<Sales, List<SalesInfo>> getALLByDate(Date date) {
		return salesDao.getALLByDate(date);
	}
	/**
	 * ��ѯǰ10���۵���Ʒid������
	 * @return
	 */
	public Map<Integer, Integer> queryGid(Date date1 ,Date date2) {
		return salesDao.queryGid(date1,date2);
	}

	/**
	 * �����ݿ��л�ȡ�������ۼ�¼
	 * @return List<Sales>
	 */
	public List<Sales> queryAlls(){
		return salesDao.queryAlls();
	}
	
	/**
	 * ����ģ��salesid�ֶδ����ݿ��л�ȡSales
	 * @param salesid ģ���ֶ� 	
	 * @return List<Sales>
	 */
	public List<Sales> queryByFuzzyId(long salesid){
		return salesDao.queryByFuzzyId(salesid);
	}
	
	
	public boolean addSales(Sales sales, List<SalesInfo> salesInfos) {
		return salesDao.addSales(sales, salesInfos);
	}
	
}
