package com.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dao.BaseDao;
import com.dao.EmployeeDao;
import com.entity.Employee;
import com.entity.EmployeeUser;
import com.entity.User;
import com.util.DButil;

/**
 * 员工数据库操作实现类
 * 
 * @author Goddard
 *
 */

public class EmployeeDaoImp extends BaseDao implements EmployeeDao {
	private Logger log = Logger.getLogger(EmployeeDaoImp.class);

	/**
	 * 从数据库查询全部员工
	 */
	public List<Employee> queryAll() {
		String sql = "select * from employees ";
		return query(sql, Employee.class);
	}

	/**
	 * 模糊查询员工
	 * 
	 * @param fuzzy
	 * @param esex
	 * @return
	 */
	public List<Employee> fuzzyQueryEmployee(String fuzzy) {
		String sql = "select distinct  * from EMPLOYEES where eid like ? or ename like ? or eadress like ? or etel like ? "
				+ "	or email like ? or esex like ? or eid in (select eid from s_user where username  like ? )";
		return query(sql, Employee.class, "%" + fuzzy + "%", "%" + fuzzy + "%", "%" + fuzzy + "%", "%" + fuzzy + "%",
				"%" + fuzzy + "%", "%" + fuzzy + "%", "%" + fuzzy + "%");
	}

	/**
	 * 根据eid查询Employee
	 * 
	 * @return Employee对象
	 */
	public Employee queryByEid(int eid) {
		String sql = "SELECT * FROM EMPLOYEES WHERE EID = ?";
		return query(sql, Employee.class, eid).get(0);
	}

	@Override
	public List<Employee> queryByStringid(String eid) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Employee> list = new ArrayList<Employee>();
		String sql = "select distinct * from employees where eid = " + eid;
		try {
			conn = DButil.getConnection();
			stmt = DButil.getStatement(conn);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new Employee(rs.getInt("eid"), rs.getString("ename"), rs.getString("esex"),
						rs.getString("eadress"), rs.getLong("etel"), rs.getString("email")));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			DButil.closeAll(conn, rs, stmt);
		}
		return list;
	}

	@Override
	public boolean addEmployee(Employee employee, User user) {
		String sql = "insert into employees values(?,?,?,?,?,?)";
		String sql1 = "insert into s_user values(s_user_seq.nextval,?,?,?,?)";
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		try {
			conn = DButil.getConnection();
			conn.setAutoCommit(false);
			pstmt = DButil.getPrePareStatement(conn, sql);
			pstmt1 = DButil.getPrePareStatement(conn, sql1);
			DButil.bindParam(pstmt, employee.getEid(), employee.getEname(), employee.getEsex(), employee.getEadress(),
					employee.getEtel(), employee.getEmail());
			DButil.bindParam(pstmt1, employee.getEid(), user.getUsername(), user.getPassword(), user.getUsertype());
			if (pstmt.executeUpdate() > 0 && pstmt1.executeUpdate() > 0) {
				conn.commit();
				flag = true;
			} else {
				conn.rollback();
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e.getMessage(), e1);
			}
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
			DButil.closeAll(conn, null, pstmt);
		}
		return flag;

		/*
		 * boolean flag = update(sql,employee.getEid(), employee.getEname(),
		 * employee.getEsex(), employee.getEadress(), employee.getEtel(),
		 * employee.getEamil()); boolean flag1 =
		 * update(sql1,employee.getEid(),user.getUsername(),user.getPassword(),curUser.
		 * getUsertype()); if(flag && flag1){ return true; } return false;
		 */
	}

	/**
	 * 查找添加员工表格(含用户名称和密码)
	 */
	public List<EmployeeUser> queryUser() {
		String sql = "select s.username,s.password,e.* " + " from s_user s ,employees e " + " where s.eid=e.eid";
		return query(sql, EmployeeUser.class);
	}

	/**
	 * 搜索语句
	 */
	@Override
	public List<EmployeeUser> fuzzyQuery(String fuzzy) {
		String sql = "select * from(select s.username,s.password,s.usertype, e.*  from s_user s ,employees e"
				+ " where s.eid =e.eid )where  username like ? or eid like ? or ename like ? or esex like ? or eadress like ? or etel like ? or email like ? or usertype like ?";
		return query(sql, EmployeeUser.class, "%" + fuzzy + "%", "%" + fuzzy + "%", "%" + fuzzy + "%",
				"%" + fuzzy + "%", "%" + fuzzy + "%", "%" + fuzzy + "%", "%" + fuzzy + "%", "%" + fuzzy + "%");
	}

	public boolean deleteEmployee(int eid) {
		String sql = "delete from employees where eid=?";
		return update(sql, eid);
	}

	/**
	 * 更新会员和用户
	 */
	public boolean updateEmployeeUser(EmployeeUser employeeUser) {
		boolean falg = true;
		String sql = "update employees set ename  = ? , esex = ?, eadress = ?, etel = ? , email = ? where eid = ? ";
		boolean falg1 = update(sql, employeeUser.getEname(), employeeUser.getEsex(), employeeUser.getEadress(),employeeUser.getEtel(), employeeUser.getEmail(),
				employeeUser.getEid());
		sql = "update s_user set password = ?,username = ?,usertype = ? where eid = ? ";
		boolean falg2 = update(sql, employeeUser.getPassword(),employeeUser.getUsername(),employeeUser.getUsertype(),employeeUser.getEid());
		if(!falg1) {
			falg = false;
		}
		if(!falg2) {
			falg = false;
		}
		return falg;
	}
}
