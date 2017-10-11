package com.service;

import java.util.List;

import com.entity.Employee;
import com.entity.User;

/**
 * user service�ӿ�
 * @author Goddard
 *
 */
public interface UserService {
	/**
	 * ��½��֤
	 * 
	 * @param user
	 *            ǰ̨��õ�user;
	 * @return service�����user;
	 */
	public User login(User user);
	
	/**
	 * �޸�����
	 * @param user	��ǰ�û�
	 * @param newPassword	��Ҫ�޸ĵ�����	
	 * @return	�Ƿ��޸ĳɹ�
	 */
	public boolean changePassword(User user,String newPassword);
	
	/**
	 *���ݵ�½�û� ����Ա��
	 * @param user ��½�û�
	 * @return Ա��
	 */
	public Employee queryEmployee(User user);
	/**
	 * �����ݿ��в�ѯUser
	 * @return	User����
	 */
	public List<User> queryUser();
	
	/**
	 * ����eid��ѯ
	 * @param eid ��ѯ��eid
	 * @return  User����
	 */
	public User queryByEid(int eid);
}
