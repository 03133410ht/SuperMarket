package com.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.entity.Goods;
import com.entity.GoodsCategory;

/**
 * 商品操作数据库接口
 * @author Goddard
 *
 */
public interface GoodsDao {
	
	/**
	 * 从数据库库中根据商品id获取商品
	 * @param gid	商品id	
	 * @return		查询到的商品 为空则没有查到
	 */
	public List<Goods> queryGoodsById(int gid);
	
	
	/**
	 * 从数据库中根据商品名称获取商品
	 * @param gname	商品名称
	 * @return	查询到的商品 为空则没有查到
	 */
	public List<Goods> queryGoodsByName(String gname);
	
	/**
	 * 从service层中得到的goods 添加到数据库中
	 * @param goods	good对象
	 * @return		是否添加成功
	 */
	public boolean addGoods(Goods goods);
	
	/**
	 * 从数据库获得所有商品
	 * @return	lsit集合
	 */
	public List<Goods> queryAll();
	
	
	/**
	 * 根据id在数据库中删除商品
	 * @param gid	商品id	
	 * @return   是否删除成功
	 */
	public boolean deleteById(int gid);
	
	
	/**
	 * 根据id在数据库中更新商品数据
	 * @param gid		商品id	
	 * @param goods		商品对象
	 * @return		是否更新成功
	 */
	public boolean update(int gid,Goods goods);
	
	
	/**
	 * 从数据库进行模糊查询
	 * @param fuzzy	模糊关键词
	 * @param gcategory	类型
	 * @param gstock	库存
	 * @return goods集合
	 */
	public List<Goods> fuzzyQuery(String fuzzy,String gcategory,String gstock);
	
	
	/**
	 * 从数据库中查询商品种类
	 * @return	种类集合
	 */
	public List<GoodsCategory> queryCategory();
	
	/**
	 * 减去库存
	 * @param num
	 * @return
	 */
	public boolean minusAccount(int gid,int num);
	
	
	/**
	 * 查询一批商品
	 * @param list
	 * @return
	 */
	public List<Goods> query(List<Integer> list);
	
	/**
	 * 查询商品种类占比
	 * @return
	 */
	public Map<String,Integer> queryCategoryNum();
	
	
}
