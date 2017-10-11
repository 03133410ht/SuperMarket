package com.service.imp;

import java.util.List;

import com.dao.SalesInfoDao;
import com.dao.imp.SalesInfoDaoImpl;
import com.entity.SalesInfo;
import com.service.SalesInfoService;


/**
 * ����Dao���ṩ��view�����ݿ�����ķ���
 * @author wuhong
 *
 */
public class SalesInfoServiceImpl implements SalesInfoService {
	private SalesInfoDao sif=new SalesInfoDaoImpl();

	/**
	 * �������۳��ⵥ�Ż�ȡdao���Ӧ������
	 * @param salesid ���ⵥ��
	 * @return  List<SalesInfo>
	 */
	public List<SalesInfo> queryBySalesId(Long salesid){
		if(salesid<=0){
			return null;
		}
		return sif.queryBySalesId(salesid);
	}
	
	/**
	 * �����ݿ��а�������ȡ��ҳ����
	 * @param curPage  ��ǰҳ
	 * @param rowsPage ÿҳ��
	 * @param fuzzyid  ģ���ֶ�
	 * @return   list
	 */
	public List<SalesInfo> queryPage(int curPage, int rowsPage,long fuzzyid){
		return sif.queryPage(curPage, rowsPage, fuzzyid);
	}
	
	/**
	 * ��dao���ȡ�������۳�����Ϣ
	 */
	public List<SalesInfo> queryAll(){
		return sif.queryAll();
	}
	

	/**
	 * ����salesid�Լ�gid��dao���ѯ����
	 * @parm1 salesid    ���۳�����
	 * @parm2 gid        ��Ʒ����
	 * @return salesinfo ����
	 */
	public SalesInfo queryBySalesidAndGid(long salesid,int gid){
		if(sif.queryBySalesidAndGid(salesid, gid).size() <= 0){
			return null;
		}
		return sif.queryBySalesidAndGid(salesid, gid).get(0);
	}
}
