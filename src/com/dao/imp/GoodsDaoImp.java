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
 * ��Ʒ�������ݿ�ʵ����
 * 
 * @author Goddard
 *
 */
public class GoodsDaoImp extends BaseDao implements GoodsDao {
	private Logger log = Logger.getLogger(GoodsDaoImp.class);

	/**
	 * �����ݿ���и�����Ʒid��ȡ��Ʒ
	 * 
	 * @param gid
	 *            ��Ʒid
	 * @return ��ѯ������Ʒ Ϊ����û�в鵽
	 */
	public List<Goods> queryGoodsById(int gid) {
		String sql = "select * from goods where gid = ?";
		return query(sql, Goods.class, gid);
	}

	/**
	 * �����ݿ��и�����Ʒ���ƻ�ȡ��Ʒ
	 * 
	 * @param gname
	 *            ��Ʒ����
	 * @return ��ѯ������Ʒ Ϊ����û�в鵽
	 */
	public List<Goods> queryGoodsByName(String gname) {
		String sql = "select * from goods where gname = ?";
		return query(sql, Goods.class, gname);
	}

	/**
	 * ��service���еõ���goods ��ӵ����ݿ���
	 * 
	 * @param goods
	 *            good����
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addGoods(Goods goods) {
		String sql = "insert into goods values(?,?,?,?,?,?,?,?,?,?)";
		return update(sql, goods.getGid(), goods.getGname(), goods.getGprice(), goods.getGcost_price(),
				goods.getGstock(), goods.getGdiscount(), goods.getGcategory(), goods.getGpoint(), goods.getGunit(),
				goods.getGremark());
	}

	/**
	 * �����ݿ���������Ʒ
	 * 
	 * @return lsit����
	 */
	public List<Goods> queryAll() {
		String sql = "select * from goods order by gid";
		return query(sql, Goods.class);
	}

	/**
	 * ����id�����ݿ���ɾ����Ʒ
	 * 
	 * @param gid
	 *            ��Ʒid
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteById(int gid) {
		String sql = "delete from goods where gid  = ? ";
		return update(sql, gid);
	}

	/**
	 * ����id�����ݿ��и�����Ʒ����
	 * 
	 * @param gid
	 *            ��Ʒid
	 * @param goods
	 *            ��Ʒ����
	 * @return �Ƿ���³ɹ�
	 */
	public boolean update(int gid, Goods goods) {
		String sql = "update goods set gid = ?,gname = ?,gprice = ?,gcost_price = ?,gstock = ?,"
				+ "gdiscount = ?,gcategory =?,gpoint = ?,gunit = ?,gremark = ?where gid = ?";
		return update(sql, goods.getGid(), goods.getGname(), goods.getGprice(), goods.getGcost_price(),
				goods.getGstock(), goods.getGdiscount(), goods.getGcategory(), goods.getGpoint(), goods.getGunit(),
				goods.getGremark(), gid);
	}

	/**
	 * �����ݿ����ģ����ѯ
	 * 
	 * @param fuzzy
	 *            ģ���ؼ���
	 * @param gcategory
	 *            ����
	 * @param gstock
	 *            ���
	 * @return goods����
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
	 * �����ݿ��в�ѯ��Ʒ����
	 * 
	 * @return ���༯��
	 */
	public List<GoodsCategory> queryCategory() {
		String sql = "select distinct gcategory from goods";
		return query(sql, GoodsCategory.class);
	}

	/**
	 * ��ȥ���
	 * 
	 * @param num
	 * @return
	 */
	public boolean minusAccount(int gid, int num) {
		String sql = "update goods set gstock =  (gstock-?) where gid = ?";
		return update(sql, num, gid);
	}

	/**
	 * ��ѯһ����Ʒ
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
	 * ��ѯ��Ʒ����ռ��
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
