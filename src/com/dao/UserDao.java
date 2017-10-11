package com.dao;

import java.util.List;

import com.entity.Employee;
import com.entity.User;
/**
 * 用户登陆判断接口
 * @author Goddard
 *
 */
public interface UserDao {
	
	/**
	 * 登陆验证
	 * @param user		service中获得的user;
	 * @return			从数据库中得到的list集合;
	 */
	public List<User> login(User user);
	
	/**
	 * 修改密码
	 * @param user	当前用户
	 * @param newPassword	需要修改的密码	
	 * @return	是否修改成功
	 */
	public boolean changePassword(User user,String newPassword);
	
	/**
	 *根据登陆用户 查找员工
	 * @param user 登陆用户
	 * @return 员工集合
	 */
	public List<Employee> queryEmployee(User user);

	/**
	 * 从数据库中查询User
	 * @return	User集合
	 */
	public List<User> queryUser();
	
	/**
	 * 根据eid查询
	 * @param eid 查询的用户id
	 * @return  User对象
	 */
	public User queryByEid(int eid);
}
  