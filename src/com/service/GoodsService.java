package com.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.entity.Goods;
import com.entity.GoodsCategory;

/**
 * 商品Service 接口
 * 
 * @author Goddard
 *
 */
public interface GoodsService {

	/**
	 * 根据商品id从Dao层获取Goods
	 * 
	 * @param gid
	 *            商品id
	 * @return 查询得到的goods 为null则没有查到
	 */
	public Goods queryGoodsById(int gid);

	/**
	 * 根据商品名称从Dao层获取Goods
	 * 
	 * @param gname
	 *            商品名称
	 * @return 查询得到的goods 为null则没有查到
	 */
	public Goods queryGoodsByName(String gname);

	/**
	 * 传入商品对象给Dao层添加商品
	 * 
	 * @param goods
	 *            商品对象
	 * @return 是否添加成功
	 */
	public boolean addGoods(Goods goods);

	/**
	 * 查询全部商品
	 * 
	 * @return list 集合
	 */
	public List<Goods> queryAll();

	/**
	 * 根据id删除商品
	 * @param gid	商品id	
	 * @return   是否删除成功
	 */
	public boolean deleteById(int gid);
	
	
	/**
	 * 根据id更新商品数据
	 * @param gid		商品id	
	 * @param goods		商品对象
	 * @return		是否更新成功
	 */
	public boolean update(int gid,Goods goods);
	
	
	
	/**
	 * 模糊查询
	 * @param fuzzy	模糊关键词
	 * @param gcategory	类型
	 * @param gstock	库存
	 * @return goods集合
	 */
	public List<Goods> fuzzyQuery(String fuzzy,String gcategory,String gstock);
		
	
	/**
	 * 查询商品种类
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
	 * 查询商品种类占比
	 * @return
	 */
	public Map<String,Integer> queryCategoryNum();
	
	

}
