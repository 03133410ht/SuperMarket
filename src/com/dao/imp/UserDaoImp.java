package com.dao.imp;

import java.util.List;


import com.dao.BaseDao;
import com.dao.UserDao;
import com.entity.Employee;
import com.entity.User;

/**
 * 用户登陆判断实现类
 * 
 * @author Goddard
 *
 */
public class UserDaoImp extends BaseDao implements UserDao {

	
	
	/**
	 * 登陆验证
	 * @param user		service中获得的user;
	 * @return			从数据库中得到的list集合;
	 */
	public List<User> login(User user) {
		String sql = "select * from s_user where username = ? and password = ?";
		return query(sql, User.class, user.getUsername(),user.getPassword());
	}

	/**
	 * 修改密码
	 * @param user	当前用户
	 * @param newPassword	需要修改的密码	
	 * @return	是否修改成功
	 */
	public boolean changePassword(User user, String newPassword) {
		String sql = "update s_user set password = ? where username = ?";
		return update(sql, newPassword,user.getUsername());
	}

	/**
	 *根据登陆用户 查找员工
	 * @param user 登陆用户
	 * @return 员工集合
	 */
	public List<Employee> queryEmployee(User user) {
		String sql = "select * from s_user u ,employees e where u.eid = e.eid  and e.eid = ? ";
		return query(sql, Employee.class, user.getEid());
	}
	/**
	 * 查询所有User
	 */
	public List<User> queryUser() {
		String sql ="SELECT * FROM S_USER";
		return query(sql,User.class);
	}

	/**
	 * 根据eid查询User
	 */
	public User queryByEid(int eid) {
		String sql ="SELECT * FROM S_USER WHERE EID = ?";
		return query(sql,User.class,eid).get(0);
	}

}
