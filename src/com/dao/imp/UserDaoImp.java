package com.dao.imp;

import java.util.List;


import com.dao.BaseDao;
import com.dao.UserDao;
import com.entity.Employee;
import com.entity.User;

/**
 * �û���½�ж�ʵ����
 * 
 * @author Goddard
 *
 */
public class UserDaoImp extends BaseDao implements UserDao {

	
	
	/**
	 * ��½��֤
	 * @param user		service�л�õ�user;
	 * @return			�����ݿ��еõ���list����;
	 */
	public List<User> login(User user) {
		String sql = "select * from s_user where username = ? and password = ?";
		return query(sql, User.class, user.getUsername(),user.getPassword());
	}

	/**
	 * �޸�����
	 * @param user	��ǰ�û�
	 * @param newPassword	��Ҫ�޸ĵ�����	
	 * @return	�Ƿ��޸ĳɹ�
	 */
	public boolean changePassword(User user, String newPassword) {
		String sql = "update s_user set password = ? where username = ?";
		return update(sql, newPassword,user.getUsername());
	}

	/**
	 *���ݵ�½�û� ����Ա��
	 * @param user ��½�û�
	 * @return Ա������
	 */
	public List<Employee> queryEmployee(User user) {
		String sql = "select * from s_user u ,employees e where u.eid = e.eid  and e.eid = ? ";
		return query(sql, Employee.class, user.getEid());
	}
	/**
	 * ��ѯ����User
	 */
	public List<User> queryUser() {
		String sql ="SELECT * FROM S_USER";
		return query(sql,User.class);
	}

	/**
	 * ����eid��ѯUser
	 */
	public User queryByEid(int eid) {
		String sql ="SELECT * FROM S_USER WHERE EID = ?";
		return query(sql,User.class,eid).get(0);
	}

}
