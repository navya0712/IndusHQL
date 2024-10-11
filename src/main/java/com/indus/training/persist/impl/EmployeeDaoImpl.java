package com.indus.training.persist.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import com.indus.training.persist.dao.IEmployeeDao;
import com.indus.training.persist.entity.Employee;
import com.indus.training.persist.exceptions.EmployeeDaoException;
import com.indus.training.persist.util.HibernateUtil;

public class EmployeeDaoImpl implements IEmployeeDao {

	/**
	 * Inserts a new employee into the database.
	 * 
	 * @param empObj The Employee object to be inserted.
	 * @return true if the employee was successfully inserted, false otherwise.
	 * @throws EmployeeDaoException If there is an issue during the insertion.
	 */
	@Override
	public Boolean insertEmployee(Employee employee) throws EmployeeDaoException {
		if (employee == null) {
			throw new EmployeeDaoException("Employee Object Cannot be null");
		}
		Session session = null;
		Boolean isInserted = false;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			session.save(employee);
			isInserted = true;
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			System.out.println(e);
			throw new EmployeeDaoException("An error Occured while Saving Employee Object to database");
		}
		return isInserted;
	}

	/**
	 * Fetches an employee by their ID.
	 * 
	 * @param empId The ID of the employee to fetch.
	 * @return The Employee object corresponding to the given ID.
	 * @throws EmployeeDaoException If there is an issue during the fetch.
	 */
	@Override
	public Employee fetchEmployeeById(Integer employeeId) throws EmployeeDaoException {
		Session session = null;
		Employee empObj = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			empObj = (Employee) session.get(Employee.class, employeeId);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			throw new EmployeeDaoException("An error occured while fetching Employee Details");
		}
		return empObj;
	}

	/**
	 * Updates the details of an employee by their ID.
	 * 
	 * @param empId    The ID of the employee to update.
	 * @param employee The details of the employee.
	 * @return true if the update was successful, false otherwise.
	 * @throws EmployeeDaoException If there is an issue during the update.
	 */
	@Override
	public Boolean updateEmployeeById(Integer employeeId, Employee employee) throws EmployeeDaoException {
		Session session = null;
		Boolean isUpdated = false;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Employee existingEmployee = (Employee) session.get(Employee.class, employeeId);
			if (existingEmployee != null) {
				existingEmployee.setFirstName(employee.getFirstName());
				existingEmployee.setLastName(employee.getLastName());
				session.update(existingEmployee);
				isUpdated = true;
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			throw new EmployeeDaoException("An error occured while Updating Employee Details");

		}
		return isUpdated;
	}

	/**
	 * Deletes an employee by their ID.
	 * 
	 * @param empId The ID of the employee to delete.
	 * @return true if the deletion was successful, false otherwise.
	 * @throws EmployeeDaoException If there is an issue during the deletion.
	 */
	@Override
	public Boolean deleteEmployeeById(Integer employeeId) throws EmployeeDaoException {
		Session session = null;
		Boolean isDeleted = false;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Employee existingEmployee = (Employee) session.get(Employee.class, employeeId);
			if (existingEmployee != null) {
				session.delete(existingEmployee);
				isDeleted = true;
				session.getTransaction().commit();
			}
		} catch (HibernateException e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			throw new EmployeeDaoException("An error occurred while deleting the Employee Object");

		}

		return isDeleted;
	}

	public List<Object[]> getEmployeeCountByDepartment() throws EmployeeDaoException{
		Session session = null;
		List<Object[]> employees = null;

		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "select dept.name, count(emp.employeeId) " + "from Employee emp "
					+ "join emp.department dept " + "group by dept.name";

			// Create the query and execute it
			Query query = session.createQuery(hql);
			employees = (List<Object[]>) query.list();

			// Commit the transaction
			session.getTransaction().commit();

		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback(); 
			}
			e.printStackTrace();
			throw new EmployeeDaoException("An error occured while getting employee count by department");
		}

		return employees;
	}
	public List<String> getDistinctDepartmentNames() throws EmployeeDaoException {
		
		Session session=null;
		List<String> departments=null;
		try {
			session=HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql="Select distinct dept.name from Employee emp join emp.department dept ";
			Query query=session.createQuery(hql);
			departments=(List<String>)query.list();
			session.getTransaction().commit();
		}
		catch(Exception e)
		{
			if(session!=null)
			{
				session.getTransaction().rollback();
			}
			throw new EmployeeDaoException("An error occured while getting distinct department names");
			
		}
		
		return departments != null ? departments : new ArrayList<>();
	}
	
	
	
	
	
	
	

}
