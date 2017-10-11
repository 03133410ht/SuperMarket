package com.service.imp;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.dao.GoodsDao;
import com.dao.imp.GoodsDaoImp;
import com.entity.Goods;
import com.entity.GoodsCategory;
import com.service.GoodsService;

/**
 * ��Ʒ service ʵ����
 * @author Goddard
 *
 */
public class GoodsServiceImp implements GoodsService {
	private GoodsDao goodsDao = new GoodsDaoImp();
	
	/**
	 * ������Ʒid��Dao���ȡGoods
	 * @param gid	��Ʒid
	 * @return	��ѯ�õ���goods Ϊnull��û�в鵽
	 */
	public Goods queryGoodsById(int gid) {
		Goods goods = null;
		List <Goods> list = goodsDao.queryGoodsById(gid);
		if(!list.isEmpty()) {
			goods = list.get(0);
		}
		return goods;
	}

	/**
	 * ������Ʒ���ƴ�Dao���ȡGoods
	 * @param gname	��Ʒ����
	 * @return	��ѯ�õ���goods Ϊnull��û�в鵽
	 */
	public Goods queryGoodsByName(String gname) {
		Goods goods = null;
		List <Goods> list = goodsDao.queryGoodsByName(gname);
		if(!list.isEmpty()) {
			goods = list.get(0);
		}
		return goods;
	}

	/**
	 * ������Ʒ�����Dao�������Ʒ
	 * @param goods 	��Ʒ����
	 * @return		�Ƿ���ӳɹ�
	 */
	public boolean addGoods(Goods goods) {
		return goodsDao.addGoods(goods);
	}

	/**
	 * ��ѯȫ����Ʒ
	 * @return	list ����
	 */
	public List<Goods> queryAll() {
		return goodsDao.queryAll();
	}
	
	
	/**
	 * ����idɾ����Ʒ
	 * @param gid	��Ʒid	
	 * @return   �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteById(int gid) {
		return goodsDao.deleteById(gid);
	}

	/**
	 * ����id�޸���Ʒ
	 * @param gid	��Ʒid	
	 * @return   �Ƿ��޸ĳɹ�
	 */
	public boolean update(int gid, Goods goods) {
		return goodsDao.update(gid, goods);
	}

	
	/**
	 * ģ����ѯ
	 * @param fuzzy	ģ���ؼ���
	 * @param gcategory	����
	 * @param gstock	���
	 * @return	goods����
	 */
	public List<Goods> fuzzyQuery(String fuzzy, String gcategory, String gstock) {
		return goodsDao.fuzzyQuery(fuzzy, gcategory, gstock);
	}

	
	/**
	 * ��ѯ��Ʒ����
	 * @return	���༯��
	 */
	public List<GoodsCategory> queryCategory() {
		return goodsDao.queryCategory();
	}

	/**
	 * ��ȥ���
	 * @param num
	 * @return
	 */
	public boolean minusAccount(int gid, int num) {
		return goodsDao.minusAccount(gid, num);
	}

	/**
	 * ��ѯһ����Ʒ
	 * @param list
	 * @return
	 */
	public List<Goods> query(List<Integer> list) {
		return goodsDao.query(list);
	}

	/**
	 * ��ѯ��Ʒ����ռ��
	 */
	public Map<String, Integer> queryCategoryNum() {
		return goodsDao.queryCategoryNum();
	}

	

}
