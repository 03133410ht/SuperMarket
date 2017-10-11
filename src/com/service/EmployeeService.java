package com.service;

import java.util.List;

import com.entity.Employee;
import com.entity.EmployeeUser;
import com.entity.User;

/**
 * 员工service接口
 * 
 * @author Goddard
 *
 */
public interface EmployeeService {
	/**
	 * 查询全部员工
	 * 
	 * @return 员工集合
	 */
	public List<Employee> queryAll();

	/**
	 * 模糊查询员工
	 * 
	 * @param fuzzy
	 * @param esex
	 * @return
	 */
	public List<Employee> fuzzyQueryEmployee(String fuzzy);

	/**
	 * 根据eid调用dao层查询Employee
	 * 
	 * @return Employee对象
	 */
	public Employee queryByEid(int eid);

	/**
	 * 根据eid删除员工
	 * 
	 * @param eid
	 * @return
	 */
	public boolean deleteByEid(int eid);

	public List<Employee> queryByStringid(String eid);

	/**
	 * 添加员工方法
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
	 * 搜索
	 * 
	 * @param fuzzy
	 * @return
	 */
	public List<EmployeeUser> fuzzyQuery(String fuzzy);

	/**
	 * 删除
	 * 
	 * @param employee
	 * @param user
	 * @param curUser
	 * @return
	 */
	public boolean deleteEmployee(int eid);
	
	
	/**
	 * 更新员工和用户
	 * @return
	 */
	public boolean updateEmployeeUser(EmployeeUser employeeUser);
	

}
