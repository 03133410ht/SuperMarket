package com.dao.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dao.BaseDao;
import com.dao.GoodsDao;
import com.entity.Goods;
import com.entity.GoodsCategory;
import com.util.DButil;

/**
 * 商品操作数据库实现类
 * 
 * @author Goddard
 *
 */
public class GoodsDaoImp extends BaseDao implements GoodsDao {
	private Logger log = Logger.getLogger(GoodsDaoImp.class);

	/**
	 * 从数据库库中根据商品id获取商品
	 * 
	 * @param gid
	 *            商品id
	 * @return 查询到的商品 为空则没有查到
	 */
	public List<Goods> queryGoodsById(int gid) {
		String sql = "select * from goods where gid = ?";
		return query(sql, Goods.class, gid);
	}

	/**
	 * 从数据库中根据商品名称获取商品
	 * 
	 * @param gname
	 *            商品名称
	 * @return 查询到的商品 为空则没有查到
	 */
	public List<Goods> queryGoodsByName(String gname) {
		String sql = "select * from goods where gname = ?";
		return query(sql, Goods.class, gname);
	}

	/**
	 * 从service层中得到的goods 添加到数据库中
	 * 
	 * @param goods
	 *            good对象
	 * @return 是否添加成功
	 */
	public boolean addGoods(Goods goods) {
		String sql = "insert into goods values(?,?,?,?,?,?,?,?,?,?)";
		return update(sql, goods.getGid(), goods.getGname(), goods.getGprice(), goods.getGcost_price(),
				goods.getGstock(), goods.getGdiscount(), goods.getGcategory(), goods.getGpoint(), goods.getGunit(),
				goods.getGremark());
	}

	/**
	 * 从数据库获得所有商品
	 * 
	 * @return lsit集合
	 */
	public List<Goods> queryAll() {
		String sql = "select * from goods order by gid";
		return query(sql, Goods.class);
	}

	/**
	 * 根据id在数据库中删除商品
	 * 
	 * @param gid
	 *            商品id
	 * @return 是否删除成功
	 */
	public boolean deleteById(int gid) {
		String sql = "delete from goods where gid  = ? ";
		return update(sql, gid);
	}

	/**
	 * 根据id在数据库中更新商品数据
	 * 
	 * @param gid
	 *            商品id
	 * @param goods
	 *            商品对象
	 * @return 是否更新成功
	 */
	public boolean update(int gid, Goods goods) {
		String sql = "update goods set gid = ?,gname = ?,gprice = ?,gcost_price = ?,gstock = ?,"
				+ "gdiscount = ?,gcategory =?,gpoint = ?,gunit = ?,gremark = ?where gid = ?";
		return update(sql, goods.getGid(), goods.getGname(), goods.getGprice(), goods.getGcost_price(),
				goods.getGstock(), goods.getGdiscount(), goods.getGcategory(), goods.getGpoint(), goods.getGunit(),
				goods.getGremark(), gid);
	}

	/**
	 * 从数据库进行模糊查询
	 * 
	 * @param fuzzy
	 *            模糊关键词
	 * @param gcategory
	 *            类型
	 * @param gstock
	 *            库存
	 * @return goods集合
	 */
	public List<Goods> fuzzyQuery(String fuzzy, String gcategory, String gstock) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Goods> list = new ArrayList<Goods>();
		String sql = "select distinct * from (select * from (select * from goods where gcategory like '%" + gcategory
				+ "%') where gstock " + gstock + ")" + " where gid like '%" + fuzzy + "%'  or  gname like '%" + fuzzy
				+ "%' or gprice like '%" + fuzzy + "%' or gcost_price like '%" + fuzzy + "%'  or "
				+ " gdiscount like '%" + fuzzy + "%' or gpoint like '%" + fuzzy + "%' or gunit like '%" + fuzzy
				+ "%' or gremark like '%" + fuzzy + "%'";
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new Goods(rs.getInt("gid"), rs.getString("gname"), rs.getDouble("gprice"),
						rs.getDouble("gcost_price"), rs.getInt("gstock"), rs.getDouble("gdiscount"),
						rs.getString("gcategory"), rs.getInt("gpoint"), rs.getString("gunit"),
						rs.getString("gremark")));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return list;

	}

	/**
	 * 从数据库中查询商品种类
	 * 
	 * @return 种类集合
	 */
	public List<GoodsCategory> queryCategory() {
		String sql = "select distinct gcategory from goods";
		return query(sql, GoodsCategory.class);
	}

	/**
	 * 减去库存
	 * 
	 * @param num
	 * @return
	 */
	public boolean minusAccount(int gid, int num) {
		String sql = "update goods set gstock =  (gstock-?) where gid = ?";
		return update(sql, num, gid);
	}

	/**
	 * 查询一批商品
	 * 
	 * @param list
	 * @return
	 */
	public List<Goods> query(List<Integer> list) {
		String sql = "select * from goods where gid  = ? ";
		Goods goods = null;
		List<Goods> goodsList = new ArrayList<Goods>();
		for (Integer in : list) {
			goodsList.add(query(sql, Goods.class, in).get(0));
		}
		return goodsList;
	}

	/**
	 * 查询商品种类占比
	 */
	public Map<String, Integer> queryCategoryNum() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String,Integer> categoryMap = new LinkedHashMap();
		String sql = "select count(gid) a,gcategory from goods group by gcategory order by a desc";
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				categoryMap.put(rs.getString("gcategory"), rs.getInt("a"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return categoryMap;
	}

	

}
