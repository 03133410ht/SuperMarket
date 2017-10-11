package com.service.imp;

import java.util.List;

import com.dao.EmployeeDao;
import com.dao.imp.EmployeeDaoImp;
import com.entity.Employee;
import com.entity.EmployeeUser;
import com.entity.User;
import com.service.EmployeeService;

/**
 * 员工service实现类
 * @author Goddard
 *
 */
public class EmployeeServiceImp implements EmployeeService{
	private EmployeeDao employeeDao = new EmployeeDaoImp();
	
	public List<Employee> queryAll() {
		return employeeDao.queryAll();
	}

	/**
	 * 模糊查询员工
	 * @param fuzzy
	 * @param esex
	 * @return
	 */
	public List<Employee> fuzzyQueryEmployee(String fuzzy) {
		return employeeDao.fuzzyQueryEmployee(fuzzy);
	}
	/**
	 * 根据eid调用dao层查询Employee
	 * @return Employee对象
	 */
	public Employee queryByEid(int eid){
		if(eid <= 0){
			return null;
		}
		return employeeDao.queryByEid(eid);
	}


	/**
	 * 根据eid删除会员
	 */
	public boolean deleteByEid(int eid) {
		
		return employeeDao.deleteEmployee(eid);
	}
	
	
	@Override
	public List<Employee> queryByStringid(String eid) {
		return employeeDao.queryByStringid(eid);
	}

	@Override
	public boolean addEmployee(Employee employee,User user) {
		return employeeDao.addEmployee(employee,user);
	}
	
	public List<EmployeeUser> queryUser(){
		return employeeDao.queryUser();
	}

	@Override
	public List<EmployeeUser> fuzzyQuery(String fuzzy) {
		return employeeDao.fuzzyQuery(fuzzy);
	}

	@Override
	public boolean deleteEmployee(int eid) {
		return employeeDao.deleteEmployee(eid);
	}

	/**
	 * 更新员工和用户
	 */
	public boolean updateEmployeeUser(EmployeeUser employeeUser) {
		return employeeDao.updateEmployeeUser(employeeUser);
	}
	
	

}
