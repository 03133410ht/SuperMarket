package com.service.imp;

import java.util.List;

import com.dao.UserDao;
import com.dao.imp.UserDaoImp;
import com.entity.Employee;
import com.entity.User;
import com.service.UserService;

/**
 * user service实现类
 * 
 * @author Goddard
 *
 */
public class UserServiceImp implements UserService {
	private UserDao userDao = new UserDaoImp();

	/**
	 * 登陆验证
	 * 
	 * @param user
	 *            前台获得的user;
	 * @return service处理的user;
	 */
	public User login(User user) {
		User currentUser = null;
		List<User> list = userDao.login(user);
		if (!list.isEmpty()) {
			currentUser = list.get(0);
		}
		return currentUser;
	}

	/**
	 * 修改密码
	 * @param user	当前用户
	 * @param newPassword	需要修改的密码	
	 * @return	是否修改成功
	 */
	public boolean changePassword(User user, String newPassword) {
		return userDao.changePassword(user, newPassword);
	}

	/**
	 *根据登陆用户 查找员工
	 * @param user 登陆用户
	 * @return 员工集合
	 */
	public Employee queryEmployee(User user) {
		return userDao.queryEmployee(user).get(0);
	}

	/**
	 * 调用dao层查询数据库中User
	 */
	public List<User> queryUser() {
		return userDao.queryUser();
	}

	/**
	 * 调用dao层根据eid查询数据库中User
	 */
	public User queryByEid(int eid) {
		return userDao.queryByEid(eid);
	}

}
