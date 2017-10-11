package com.dao;

import java.util.List;

import com.entity.Employee;
import com.entity.EmployeeUser;
import com.entity.User;

/**
 * 员工数据库操作接口
 * 
 * @author Goddard
 *
 */
public interface EmployeeDao {

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
	 * 根据eid查询Employee
	 * 
	 * @param eid
	 *            查询的eid
	 * @return Employee对象
	 */
	public Employee queryByEid(int eid);

	/**
	 * 根据eid删除员工
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
	 * 更新员工和用户
	 * @return
	 */
	public boolean updateEmployeeUser(EmployeeUser employeeUser);
	

}
