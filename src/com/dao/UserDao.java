package com.dao;

import java.util.List;

import com.entity.Employee;
import com.entity.User;
/**
 * �û���½�жϽӿ�
 * @author Goddard
 *
 */
public interface UserDao {
	
	/**
	 * ��½��֤
	 * @param user		service�л�õ�user;
	 * @return			�����ݿ��еõ���list����;
	 */
	public List<User> login(User user);
	
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
	 * @return Ա������
	 */
	public List<Employee> queryEmployee(User user);

	/**
	 * �����ݿ��в�ѯUser
	 * @return	User����
	 */
	public List<User> queryUser();
	
	/**
	 * ����eid��ѯ
	 * @param eid ��ѯ���û�id
	 * @return  User����
	 */
	public User queryByEid(int eid);
}
  