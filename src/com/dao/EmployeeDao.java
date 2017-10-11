package com.dao;

import java.util.List;

import com.entity.Employee;
import com.entity.EmployeeUser;
import com.entity.User;

/**
 * Ա�����ݿ�����ӿ�
 * 
 * @author Goddard
 *
 */
public interface EmployeeDao {

	/**
	 * ��ѯȫ��Ա��
	 * 
	 * @return Ա������
	 */
	public List<Employee> queryAll();

	/**
	 * ģ����ѯԱ��
	 * 
	 * @param fuzzy
	 * @param esex
	 * @return
	 */
	public List<Employee> fuzzyQueryEmployee(String fuzzy);

	/**
	 * ����eid��ѯEmployee
	 * 
	 * @param eid
	 *            ��ѯ��eid
	 * @return Employee����
	 */
	public Employee queryByEid(int eid);

	/**
	 * ����eidɾ��Ա��
	 * 
	 * @param eid
	 * @return
	 */
	public List<Employee> queryByStringid(String eid);

	public boolean addEmployee(Employee employee, User user);

	public List<EmployeeUser> queryUser();

	public List<EmployeeUser> fuzzyQuery(String fuzzy);

	public boolean deleteEmployee(int eid);
	
	/**
	 * ����Ա�����û�
	 * @return
	 */
	public boolean updateEmployeeUser(EmployeeUser employeeUser);
	

}
