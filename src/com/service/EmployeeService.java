package com.service;

import java.util.List;

import com.entity.Employee;
import com.entity.EmployeeUser;
import com.entity.User;

/**
 * Ա��service�ӿ�
 * 
 * @author Goddard
 *
 */
public interface EmployeeService {
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
	 * ����eid����dao���ѯEmployee
	 * 
	 * @return Employee����
	 */
	public Employee queryByEid(int eid);

	/**
	 * ����eidɾ��Ա��
	 * 
	 * @param eid
	 * @return
	 */
	public boolean deleteByEid(int eid);

	public List<Employee> queryByStringid(String eid);

	/**
	 * ���Ա������
	 * 
	 * @param employee
	 * @param user
	 * @return
	 */
	public boolean addEmployee(Employee employee, User user);

	/**
	 * 
	 * @return
	 */
	public List<EmployeeUser> queryUser();

	/**
	 * ����
	 * 
	 * @param fuzzy
	 * @return
	 */
	public List<EmployeeUser> fuzzyQuery(String fuzzy);

	/**
	 * ɾ��
	 * 
	 * @param employee
	 * @param user
	 * @param curUser
	 * @return
	 */
	public boolean deleteEmployee(int eid);
	
	
	/**
	 * ����Ա�����û�
	 * @return
	 */
	public boolean updateEmployeeUser(EmployeeUser employeeUser);
	

}
