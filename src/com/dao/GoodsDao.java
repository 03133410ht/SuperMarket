package com.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.entity.Goods;
import com.entity.GoodsCategory;

/**
 * ��Ʒ�������ݿ�ӿ�
 * @author Goddard
 *
 */
public interface GoodsDao {
	
	/**
	 * �����ݿ���и�����Ʒid��ȡ��Ʒ
	 * @param gid	��Ʒid	
	 * @return		��ѯ������Ʒ Ϊ����û�в鵽
	 */
	public List<Goods> queryGoodsById(int gid);
	
	
	/**
	 * �����ݿ��и�����Ʒ���ƻ�ȡ��Ʒ
	 * @param gname	��Ʒ����
	 * @return	��ѯ������Ʒ Ϊ����û�в鵽
	 */
	public List<Goods> queryGoodsByName(String gname);
	
	/**
	 * ��service���еõ���goods ��ӵ����ݿ���
	 * @param goods	good����
	 * @return		�Ƿ���ӳɹ�
	 */
	public boolean addGoods(Goods goods);
	
	/**
	 * �����ݿ���������Ʒ
	 * @return	lsit����
	 */
	public List<Goods> queryAll();
	
	
	/**
	 * ����id�����ݿ���ɾ����Ʒ
	 * @param gid	��Ʒid	
	 * @return   �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteById(int gid);
	
	
	/**
	 * ����id�����ݿ��и�����Ʒ����
	 * @param gid		��Ʒid	
	 * @param goods		��Ʒ����
	 * @return		�Ƿ���³ɹ�
	 */
	public boolean update(int gid,Goods goods);
	
	
	/**
	 * �����ݿ����ģ����ѯ
	 * @param fuzzy	ģ���ؼ���
	 * @param gcategory	����
	 * @param gstock	���
	 * @return goods����
	 */
	public List<Goods> fuzzyQuery(String fuzzy,String gcategory,String gstock);
	
	
	/**
	 * �����ݿ��в�ѯ��Ʒ����
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
	 * ��ѯһ����Ʒ
	 * @param list
	 * @return
	 */
	public List<Goods> query(List<Integer> list);
	
	/**
	 * ��ѯ��Ʒ����ռ��
	 * @return
	 */
	public Map<String,Integer> queryCategoryNum();
	
	
}
