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
 * ��Ա�������ݿ����ʵ����
 * 
 * @author Goddard
 *
 */
public class MemberDaoImp extends BaseDao implements MemberDao {
	private Logger log = Logger.getLogger(BaseDao.class);
	/**
	 * ���ݻ�Աid ����ģ����ѯ
	 * 
	 * @param mid
	 *            ��Աid
	 * @return ��Ա����
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
	 * ���ݻ�Ա���� ����ģ����ѯ
	 * 
	 * @param mname
	 *            ��Ա����
	 * @return ��Ա����
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
	 * �����Ա� ��Ա�ȼ� ��ģ���ؼ��ʽ���ģ����ѯ
	 * 
	 * @param fuzzy
	 *            ģ���ؼ���
	 * @param msex
	 *            ��Ա�Ա�
	 * @param mgrade
	 *            ��Ա�ȼ�
	 * @return ��Ա����
	 */

	public List<Member> fuzzyQuery(String fuzzy, String msex, String mgrade) {
		String sql = "select distinct * from (select * from (select * from s_member where mgrade like ?)where msex like ?)"
				+ " where mid like ? or mname like ? or  mtel like ? order by mid";
		return query(sql, Member.class, "%" + mgrade + "%", "%" + msex + "%", "%" + fuzzy + "%", "%" + fuzzy + "%",
				"%" + fuzzy + "%");
	}

	/**
	 * ���ݻ�Ա�ֻ� ����ģ����ѯ
	 * 
	 * @param mtel
	 *            ��Ա�ֻ�
	 * @return ��Ա����
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
	 * ��ӻ�Ա
	 */
	public boolean addMember(Member member) {
		String sql = "insert into s_member values(?,?,?,?,?,?,?,?)";
		return update(sql, member.getMid(), member.getMname(), member.getMsex(), member.getMgrade(),
				member.getMmonetary(), member.getMtel(), member.getMaccount(), member.getMpassword());
	}

	/**
	 * ɾ����Ա
	 */
	public boolean deleteById(int mid) {
		String sql = "delete from s_member where mid = ?";
		return update(sql, mid);
	}

	/**
	 * ���»�Ա��Ϣ
	 */
	public boolean updateMember(int mid, Member member) {
		String sql = "update s_member set mid = ?,mname = ?,msex = ?,mgrade = ?,mmonetary = ?,mtel = ?,"
				+ " maccount = ?,mpassword =?where mid = ?";
		return update(sql, member.getMid(), member.getMname(), member.getMsex(), member.getMgrade(),
				member.getMmonetary(), member.getMtel(), member.getMaccount(), member.getMpassword(), mid);
	}

	/**
	 * ��Ա��½
	 * 
	 * @param member
	 * @return
	 */
	public List<Member>  queryMember(Member member) {
		String sql = "select * from s_member where mid = ? and  mpassword = ?";
		return query(sql, Member.class, member.getMid(), member.getMpassword());
	}
	/**
	 * ���ݻ�Ա�ȼ����һ�Ա�ۿ���
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
	 * ��Ա���ѽ�� �ں�̨��ȥ���
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
	 * ��ѯ���л�Ա�ȼ�
	 */
	public List<MemberGrade> queryAllGrade() {
		String sql = "select * from MEMBER_GRADE ";
		return query(sql, MemberGrade.class);
	}

	/**
	 * ��ѯ��Ա�ȼ�����
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
