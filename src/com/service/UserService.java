package com.service;

import java.util.List;

import com.entity.Employee;
import com.entity.User;

/**
 * user service接口
 * @author Goddard
 *
 */
public interface UserService {
	/**
	 * 登陆验证
	 * 
	 * @param user
	 *            前台获得的user;
	 * @return service处理的user;
	 */
	public User login(User user);
	
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
	 * @return 员工
	 */
	public Employee queryEmployee(User user);
	/**
	 * 从数据库中查询User
	 * @return	User集合
	 */
	public List<User> queryUser();
	
	/**
	 * 根据eid查询
	 * @param eid 查询的eid
	 * @return  User对象
	 */
	public User queryByEid(int eid);
}
