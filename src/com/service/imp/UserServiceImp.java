package com.service.imp;

import java.util.List;

import com.dao.UserDao;
import com.dao.imp.UserDaoImp;
import com.entity.Employee;
import com.entity.User;
import com.service.UserService;

/**
 * user serviceʵ����
 * 
 * @author Goddard
 *
 */
public class UserServiceImp implements UserService {
	private UserDao userDao = new UserDaoImp();

	/**
	 * ��½��֤
	 * 
	 * @param user
	 *            ǰ̨��õ�user;
	 * @return service�����user;
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
	 * �޸�����
	 * @param user	��ǰ�û�
	 * @param newPassword	��Ҫ�޸ĵ�����	
	 * @return	�Ƿ��޸ĳɹ�
	 */
	public boolean changePassword(User user, String newPassword) {
		return userDao.changePassword(user, newPassword);
	}

	/**
	 *���ݵ�½�û� ����Ա��
	 * @param user ��½�û�
	 * @return Ա������
	 */
	public Employee queryEmployee(User user) {
		return userDao.queryEmployee(user).get(0);
	}

	/**
	 * ����dao���ѯ���ݿ���User
	 */
	public List<User> queryUser() {
		return userDao.queryUser();
	}

	/**
	 * ����dao�����eid��ѯ���ݿ���User
	 */
	public User queryByEid(int eid) {
		return userDao.queryByEid(eid);
	}

}
