package com.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.entity.Goods;
import com.entity.GoodsCategory;

/**
 * ��ƷService �ӿ�
 * 
 * @author Goddard
 *
 */
public interface GoodsService {

	/**
	 * ������Ʒid��Dao���ȡGoods
	 * 
	 * @param gid
	 *            ��Ʒid
	 * @return ��ѯ�õ���goods Ϊnull��û�в鵽
	 */
	public Goods queryGoodsById(int gid);

	/**
	 * ������Ʒ���ƴ�Dao���ȡGoods
	 * 
	 * @param gname
	 *            ��Ʒ����
	 * @return ��ѯ�õ���goods Ϊnull��û�в鵽
	 */
	public Goods queryGoodsByName(String gname);

	/**
	 * ������Ʒ�����Dao�������Ʒ
	 * 
	 * @param goods
	 *            ��Ʒ����
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addGoods(Goods goods);

	/**
	 * ��ѯȫ����Ʒ
	 * 
	 * @return list ����
	 */
	public List<Goods> queryAll();

	/**
	 * ����idɾ����Ʒ
	 * @param gid	��Ʒid	
	 * @return   �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteById(int gid);
	
	
	/**
	 * ����id������Ʒ����
	 * @param gid		��Ʒid	
	 * @param goods		��Ʒ����
	 * @return		�Ƿ���³ɹ�
	 */
	public boolean update(int gid,Goods goods);
	
	
	
	/**
	 * ģ����ѯ
	 * @param fuzzy	ģ���ؼ���
	 * @param gcategory	����
	 * @param gstock	���
	 * @return goods����
	 */
	public List<Goods> fuzzyQuery(String fuzzy,String gcategory,String gstock);
		
	
	/**
	 * ��ѯ��Ʒ����
	 * @return	���༯��
	 */
	public List<GoodsCategory> queryCategory();
	
	
	/**
	 * ��ȥ���
	 * @param num
	 * @return
	 */
	public boolean minusAccount(int gid,int num);
	
	
	/**
	 * ��ѯ��Ʒ����ռ��
	 * @return
	 */
	public Map<String,Integer> queryCategoryNum();
	
	

}
