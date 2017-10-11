package com.dao.imp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dao.BaseDao;
import com.dao.SalesDao;
import com.entity.Employee;
import com.entity.Goods;
import com.entity.Member;
import com.entity.Sales;
import com.entity.SalesInfo;
import com.service.GoodsService;
import com.service.imp.GoodsServiceImp;
import com.util.DButil;

/**
 * 商品销售记录数据库操作类
 * 
 * @author Goddard
 *
 */
public class SalesDaoImp extends BaseDao implements SalesDao {
	private Logger log = Logger.getLogger(SalesDaoImp.class);
	private GoodsService goodsService = new GoodsServiceImp();

	/**
	 * 在数据库根据单号查询记录
	 * 
	 * @return sales对象集合
	 */
	public List<Sales> querySalesID(long gid) {
		String sql = "select  * from sales where salesid like ? order by salesid";
		return query(sql, Sales.class, gid + "%");
	}

	/**
	 * 添加一条销售记录
	 * 
	 * @param sales
	 *            销售记录
	 * @param mapGoods
	 *            商品集合
	 * @return 是否添加成功
	 */
	public boolean addSales(Sales sales, Map<Integer, Integer> mapGoods) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			conn = DButil.getConnection();
			String sql = "insert into sales values(?,?,?,?,?,?)";
			pstmt = DButil.getPrePareStatement(conn, sql);
			DButil.bindParam(pstmt, sales.getSalesid(), sales.getEid(), sales.getMid(), sales.getSdate(),
					sales.getMgathering(), sales.getRemark());
			int result = pstmt.executeUpdate();
			for (Map.Entry<Integer, Integer> entry : mapGoods.entrySet()) {
				sql = "insert into sales_info values(?,?,?)";
				pstmt = DButil.getPrePareStatement(conn, sql);
				DButil.bindParam(pstmt, sales.getSalesid(), entry.getKey(), entry.getValue());
				;
				result = pstmt.executeUpdate();
			}
			if (result > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			flag = false;
		} finally {
			DButil.closeAll(conn, null, pstmt);
		}
		return flag;
	}

	public Map<Sales, List<SalesInfo>> getALLByMid(List<? extends Object> list, Date date1, Date date2) {
		Map<Sales, List<SalesInfo>> mapSales = new LinkedHashMap<Sales, List<SalesInfo>>();
		List<SalesInfo> salesList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			conn = DButil.getConnection();
			String sql = "select * from (select rownum rownumber,s.* from SALES s  order by salesid) where mid like ?"
					+ " and sdate between ? and ?";
			pstmt = DButil.getPrePareStatement(conn, sql);
			if (list == null) {
				pstmt = DButil.getPrePareStatement(conn, sql);
				DButil.bindParam(pstmt, "%%", date1, date2);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					mapSales.put(new Sales(rs.getLong("salesid"), rs.getInt("eid"), rs.getInt("mid"),
							rs.getDate("sdate"), rs.getInt("mgathering"), rs.getString("remark")), null);
				}
				sql = "select * from sales_info where salesid = ?";
				pstmt = DButil.getPrePareStatement(conn, sql);
				for (Map.Entry<Sales, List<SalesInfo>> entry : mapSales.entrySet()) {
					salesList = new ArrayList<SalesInfo>();
					DButil.bindParam(pstmt, entry.getKey().getSalesid());
					rs = pstmt.executeQuery();
					while (rs.next()) {
						salesList.add(new SalesInfo(rs.getLong("salesid"), rs.getInt("gid"), rs.getInt("snum")));
					}
					entry.setValue(salesList);
				}
			} else if (list.get(0) instanceof Member) {
				List<Member> memberList = Arrays.asList(list.toArray(new Member[0]));
				sql = "select * from (select rownum rownumber,s.* from SALES s  order by salesid) where mid ="
						+ memberList.get(0).getMid() + " and sdate between to_date('" + date1 + "','yyyy-mm-dd')"
						+ " and to_date('" + date2 + "','yyyy-mm-dd')";
				stmt = DButil.getStatement(conn);
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					mapSales.put(new Sales(rs.getLong("salesid"), rs.getInt("eid"), rs.getInt("mid"),
							rs.getDate("sdate"), rs.getInt("mgathering"), rs.getString("remark")), null);
				}
				sql = "select * from sales_info where salesid = ?";
				pstmt = DButil.getPrePareStatement(conn, sql);
				for (Map.Entry<Sales, List<SalesInfo>> entry : mapSales.entrySet()) {
					salesList = new ArrayList<SalesInfo>();
					DButil.bindParam(pstmt, entry.getKey().getSalesid());
					rs = pstmt.executeQuery();
					while (rs.next()) {
						salesList.add(new SalesInfo(rs.getLong("salesid"), rs.getInt("gid"), rs.getInt("snum")));
					}
					entry.setValue(salesList);
				}
			} else if (list.get(0) instanceof Employee) {
				sql = "select * from (select rownum rownumber,s.* from SALES s  order by salesid) where eid = ?"
						+ " and sdate between ? and ?";
				pstmt = DButil.getPrePareStatement(conn, sql);
				List<Employee> employeeList = Arrays.asList(list.toArray(new Employee[0]));
				DButil.bindParam(pstmt, employeeList.get(0).getEid(), date1, date2);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					mapSales.put(new Sales(rs.getLong("salesid"), rs.getInt("eid"), rs.getInt("mid"),
							rs.getDate("sdate"), rs.getInt("mgathering"), rs.getString("remark")), null);
				}
				sql = "select * from sales_info where salesid = ?";
				pstmt = DButil.getPrePareStatement(conn, sql);
				for (Map.Entry<Sales, List<SalesInfo>> entry : mapSales.entrySet()) {
					salesList = new ArrayList<SalesInfo>();
					DButil.bindParam(pstmt, entry.getKey().getSalesid());
					rs = pstmt.executeQuery();
					while (rs.next()) {
						salesList.add(new SalesInfo(rs.getLong("salesid"), rs.getInt("gid"), rs.getInt("snum")));
					}
					entry.setValue(salesList);
				}
			} else if (list.get(0) instanceof Goods) {
				List<Goods> GoodsList = Arrays.asList(list.toArray(new Goods[0]));
				sql = "select * from sales_info where gid = ? order by salesid";
				pstmt = DButil.getPrePareStatement(conn, sql);
				DButil.bindParam(pstmt, GoodsList.get(0).getGid());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					salesList = new ArrayList<SalesInfo>();
					salesList.add(new SalesInfo(rs.getLong("salesid"), rs.getInt("gid"), rs.getInt("snum")));
					sql = "select * from (select rownum rownumber,s.* from SALES s  order by salesid) where salesid = ? "
							+ " and sdate between ? and ?";
					pstmt = DButil.getPrePareStatement(conn, sql);
					DButil.bindParam(pstmt, rs.getLong("salesid"), date1, date2);
					rs2 = pstmt.executeQuery();
					while (rs2.next()) {
						mapSales.put(
								new Sales(rs2.getLong("salesid"), rs2.getInt("eid"), rs2.getInt("mid"),
										rs2.getDate("sdate"), rs2.getInt("mgathering"), rs2.getString("remark")),
								salesList);
					}
				}

			} else if (list.get(0) instanceof String) {
				sql = "select * from (select rownum rownumber,s.* from SALES s  order by salesid) where salesid =  ?"
						+ " and sdate between ? and ?";
				pstmt = DButil.getPrePareStatement(conn, sql);
				List<String> StringList = Arrays.asList(list.toArray(new String[0]));
				for (String str : StringList) {
					DButil.bindParam(pstmt, str, date1, date2);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						mapSales.put(new Sales(rs.getLong("salesid"), rs.getInt("eid"), rs.getInt("mid"),
								rs.getDate("sdate"), rs.getInt("mgathering"), rs.getString("remark")), null);
					}
					sql = "select * from sales_info where salesid = ?";
					pstmt = DButil.getPrePareStatement(conn, sql);
					for (Map.Entry<Sales, List<SalesInfo>> entry : mapSales.entrySet()) {
						salesList = new ArrayList<SalesInfo>();
						DButil.bindParam(pstmt, StringList.get(0));
						rs = pstmt.executeQuery();
						while (rs.next()) {
							salesList.add(new SalesInfo(rs.getLong("salesid"), rs.getInt("gid"), rs.getInt("snum")));
						}
						entry.setValue(salesList);
					}
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, pstmt);
		}
		return mapSales;
	}

	/**
	 * 根据时间查询记录
	 * 
	 * @return
	 */
	public Map<Sales, List<SalesInfo>> getALLByDate(Date date) {
		Map<Sales, List<SalesInfo>> mapSales = new LinkedHashMap<Sales, List<SalesInfo>>();
		List<SalesInfo> salesList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from SALES where sdate = ?";
		try {
			conn = DButil.getConnection();
			pstmt = DButil.getPrePareStatement(conn, sql);
			DButil.bindParam(pstmt, date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mapSales.put(new Sales(rs.getLong("salesid"), rs.getInt("eid"), rs.getInt("mid"), rs.getDate("sdate"),
						rs.getInt("mgathering"), rs.getString("remark")), null);
			}
			sql = "select * from sales_info where salesid = ?";
			pstmt = DButil.getPrePareStatement(conn, sql);
			for (Map.Entry<Sales, List<SalesInfo>> entry : mapSales.entrySet()) {
				salesList = new ArrayList<SalesInfo>();
				DButil.bindParam(pstmt, entry.getKey().getSalesid());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					salesList.add(new SalesInfo(rs.getLong("salesid"), rs.getInt("gid"), rs.getInt("snum")));
				}
				entry.setValue(salesList);
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, pstmt);
		}
		return mapSales;

	}

	/**
	 * 查询前10销售的商品id和数量
	 * 
	 * @return
	 */
	public Map<Integer, Integer> queryGid(Date date1, Date date2) {
		Map<Integer, Integer> mapGid = new LinkedHashMap<Integer, Integer>();
		String sql = "select count(gid) a,gid from Sales_Info si ,sales s  where si.salesid = s.salesid "
				+ " and s.sdate between ? and ? group by gid order by a desc ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DButil.getConnection();
			pstmt = DButil.getPrePareStatement(conn, sql);
			DButil.bindParam(pstmt, date1, date2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mapGid.put(rs.getInt("gid"), rs.getInt("a"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, pstmt);
		}
		return mapGid;
	}

	/**
	 * 从数据库中获取所有销售记录
	 * @return List<Sales>
	 */
	public List<Sales> queryAlls(){
		String sql="SELECT * FROM SALES WHERE SALESID>=0";
		return query(sql, Sales.class);
	}
	
	/**
	 * 根据模糊salesid字段从数据控中获取Sales
	 * @param salesid 模糊字段 	
	 * @return List<Sales>
	 */
	public List<Sales> queryByFuzzyId(long salesid){
		String sql="SELECT * FROM SALES WHERE SALESID LIKE ? ORDER BY SALESID ";
		return query(sql, Sales.class, "%"+salesid+"%");
	}
	/**
	 * 添加Sales退货单到数据库
	 * @return true/false
	 */
	public boolean addSales(Sales sales,List<SalesInfo> salesInfos){
		boolean flag=false;
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=DButil.getConnection();
			conn.setAutoCommit(false);
			if(querySalesID(sales.getSalesid()).isEmpty()){
				String sql="INSERT INTO SALES VALUES(?,?,?,?,?,?)";
				ps=DButil.getPrePareStatement(conn, sql);
				DButil.bindParam(ps, sales.getSalesid(),sales.getEid(),sales.getMid(),sales.getSdate(),sales.getMgathering(),sales.getRemark());
				int result=ps.executeUpdate();
				if(result>0){
					flag=true;
				}else{
					conn.rollback();
					return flag;
				}
			}
			String sql="INSERT INTO SALES_INFO VALUES(?,?,?)";
			for (SalesInfo salesInfo : salesInfos) {
				ps=DButil.getPrePareStatement(conn, sql);
				DButil.bindParam(ps,salesInfo.getSalesid(),salesInfo.getGid(),salesInfo.getSnum());
				int result=ps.executeUpdate();
				if(result>0){
					flag=true;
				}else{
					conn.rollback();
					return flag;
				}
			}
			sql = "UPDATE GOODS SET GID = ?,GNAME = ?,GPRICE = ?,GCOST_PRICE = ?,GSTOCK = ?,"
					+ "GDISCOUNT = ?,GCATEGORY =?,GPOINT = ?,GUNIT = ?,GREMARK = ?WHERE GID = ?";
			for (SalesInfo salesInfo : salesInfos) {
				int num=salesInfo.getSnum();
				int gid=salesInfo.getGid();
				Goods good=goodsService.queryGoodsById(gid);
				good.setGstock(good.getGstock()+num);
				ps=DButil.getPrePareStatement(conn, sql);
				DButil.bindParam(ps, good.getGid(), good.getGname(), good.getGprice(), good.getGcost_price(),
					good.getGstock(), good.getGdiscount(), good.getGcategory(), good.getGpoint(), good.getGunit(),
					good.getGremark(), gid);
				int result=ps.executeUpdate();
				if(result>0){
					flag=true;
				}else{
					conn.rollback();
					return flag;
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e.getMessage(),e);
			}
		}finally{
			try {
				conn.commit();
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
			DButil.closeAll(conn, null, ps);
		}
		return flag;
	}
}
