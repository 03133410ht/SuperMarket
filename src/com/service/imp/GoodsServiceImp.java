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
 * 商品 service 实现类
 * @author Goddard
 *
 */
public class GoodsServiceImp implements GoodsService {
	private GoodsDao goodsDao = new GoodsDaoImp();
	
	/**
	 * 根据商品id从Dao层获取Goods
	 * @param gid	商品id
	 * @return	查询得到的goods 为null则没有查到
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
	 * 根据商品名称从Dao层获取Goods
	 * @param gname	商品名称
	 * @return	查询得到的goods 为null则没有查到
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
	 * 传入商品对象给Dao层添加商品
	 * @param goods 	商品对象
	 * @return		是否添加成功
	 */
	public boolean addGoods(Goods goods) {
		return goodsDao.addGoods(goods);
	}

	/**
	 * 查询全部商品
	 * @return	list 集合
	 */
	public List<Goods> queryAll() {
		return goodsDao.queryAll();
	}
	
	
	/**
	 * 根据id删除商品
	 * @param gid	商品id	
	 * @return   是否删除成功
	 */
	public boolean deleteById(int gid) {
		return goodsDao.deleteById(gid);
	}

	/**
	 * 根据id修改商品
	 * @param gid	商品id	
	 * @return   是否修改成功
	 */
	public boolean update(int gid, Goods goods) {
		return goodsDao.update(gid, goods);
	}

	
	/**
	 * 模糊查询
	 * @param fuzzy	模糊关键词
	 * @param gcategory	类型
	 * @param gstock	库存
	 * @return	goods集合
	 */
	public List<Goods> fuzzyQuery(String fuzzy, String gcategory, String gstock) {
		return goodsDao.fuzzyQuery(fuzzy, gcategory, gstock);
	}

	
	/**
	 * 查询商品种类
	 * @return	种类集合
	 */
	public List<GoodsCategory> queryCategory() {
		return goodsDao.queryCategory();
	}

	/**
	 * 减去库存
	 * @param num
	 * @return
	 */
	public boolean minusAccount(int gid, int num) {
		return goodsDao.minusAccount(gid, num);
	}

	/**
	 * 查询一批商品
	 * @param list
	 * @return
	 */
	public List<Goods> query(List<Integer> list) {
		return goodsDao.query(list);
	}

	/**
	 * 查询商品种类占比
	 */
	public Map<String, Integer> queryCategoryNum() {
		return goodsDao.queryCategoryNum();
	}

	

}
