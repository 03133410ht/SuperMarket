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
import com.dao.MemberDao;
import com.entity.Member;
import com.entity.MemberGrade;
import com.util.DButil;

/**
 * 会员管理数据库操作实现类
 * 
 * @author Goddard
 *
 */
public class MemberDaoImp extends BaseDao implements MemberDao {
	private Logger log = Logger.getLogger(BaseDao.class);
	/**
	 * 根据会员id 进行模糊查询
	 * 
	 * @param mid
	 *            会员id
	 * @return 会员集合
	 */
	public List<Member> queryByid(String mid) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Member> list = new ArrayList<Member>();
		String sql = "select distinct * from s_member where mid = " + mid;
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new Member(rs.getInt("mid"), rs.getString("mname"), rs.getString("msex"),
						rs.getString("mgrade"), rs.getDouble("mmonetary"), rs.getLong("mtel"), rs.getDouble("maccount"),
						rs.getString("mpassword")));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return list;
	}

	/**
	 * 根据会员姓名 进行模糊查询
	 * 
	 * @param mname
	 *            会员姓名
	 * @return 会员集合
	 */
	public List<Member> queryByName(String mname) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Member> list = new ArrayList<Member>();
		String sql = "select distinct * from s_member where mname " + mname;
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new Member(rs.getInt("mid"), rs.getString("mname"), rs.getString("msex"),
						rs.getString("mgrade"), rs.getDouble("mmonetary"), rs.getLong("mtel"), rs.getDouble("maccount"),
						rs.getString("mpassword")));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return list;
	}

	/**
	 * 根据性别 会员等级 和模糊关键词进行模糊查询
	 * 
	 * @param fuzzy
	 *            模糊关键词
	 * @param msex
	 *            会员性别
	 * @param mgrade
	 *            会员等级
	 * @return 会员集合
	 */

	public List<Member> fuzzyQuery(String fuzzy, String msex, String mgrade) {
		String sql = "select distinct * from (select * from (select * from s_member where mgrade like ?)where msex like ?)"
				+ " where mid like ? or mname like ? or  mtel like ? order by mid";
		return query(sql, Member.class, "%" + mgrade + "%", "%" + msex + "%", "%" + fuzzy + "%", "%" + fuzzy + "%",
				"%" + fuzzy + "%");
	}

	/**
	 * 根据会员手机 进行模糊查询
	 * 
	 * @param mtel
	 *            会员手机
	 * @return 会员集合
	 */
	public List<Member> queryByTel(String mtel) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Member> list = new ArrayList<Member>();
		String sql = "select distinct * from s_member where mtel " + mtel;
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new Member(rs.getInt("mid"), rs.getString("mname"), rs.getString("msex"),
						rs.getString("mgrade"), rs.getDouble("mmonetary"), rs.getLong("mtel"), rs.getDouble("maccount"),
						rs.getString("mpassword")));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return list;
	}

	/**
	 * 添加会员
	 */
	public boolean addMember(Member member) {
		String sql = "insert into s_member values(?,?,?,?,?,?,?,?)";
		return update(sql, member.getMid(), member.getMname(), member.getMsex(), member.getMgrade(),
				member.getMmonetary(), member.getMtel(), member.getMaccount(), member.getMpassword());
	}

	/**
	 * 删除会员
	 */
	public boolean deleteById(int mid) {
		String sql = "delete from s_member where mid = ?";
		return update(sql, mid);
	}

	/**
	 * 更新会员信息
	 */
	public boolean updateMember(int mid, Member member) {
		String sql = "update s_member set mid = ?,mname = ?,msex = ?,mgrade = ?,mmonetary = ?,mtel = ?,"
				+ " maccount = ?,mpassword =?where mid = ?";
		return update(sql, member.getMid(), member.getMname(), member.getMsex(), member.getMgrade(),
				member.getMmonetary(), member.getMtel(), member.getMaccount(), member.getMpassword(), mid);
	}

	/**
	 * 会员登陆
	 * 
	 * @param member
	 * @return
	 */
	public List<Member>  queryMember(Member member) {
		String sql = "select * from s_member where mid = ? and  mpassword = ?";
		return query(sql, Member.class, member.getMid(), member.getMpassword());
	}
	/**
	 * 根据会员等级查找会员折扣率
	 * @param grade
	 * @return
	 */
	public List<String> queryDiscount(String grade) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		String sql = "select to_char(mdiscount,'fm999990.99999') c from member_grade where mgrade = "+"'"+grade+"'";
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("c"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return list;
	}
	/**
	 * 会员消费金额 在后台减去余额
	 * @param mid
	 * @param price
	 * @return
	 */
	public boolean consume(int mid, double price,boolean isAccount) {
		boolean flag = true;
		String sql = "";
		if(isAccount) {
			sql ="update s_member set maccount = (maccount -?) where mid = ?";
			if(!update(sql, price,mid)) {
				flag = false;
			}
		}
		sql = "  update s_member set mmonetary = (mmonetary +?) where mid = ?";
		if(!update(sql, price,mid)) {
			flag = false;
		}
		return flag;
		
	}

	/**
	 * 查询所有会员等级
	 */
	public List<MemberGrade> queryAllGrade() {
		String sql = "select * from MEMBER_GRADE ";
		return query(sql, MemberGrade.class);
	}

	/**
	 * 查询会员等级数量
	 * @return
	 */
	public Map<String, Integer> queryGradeNum() {
		String sql = "select count(mgrade) a,mgrade from s_member group by mgrade order by a desc";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String,Integer> gradeMap = new LinkedHashMap<String,Integer>();
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				gradeMap.put(rs.getString("mgrade"), rs.getInt("a"));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return gradeMap;
	}

	

}
