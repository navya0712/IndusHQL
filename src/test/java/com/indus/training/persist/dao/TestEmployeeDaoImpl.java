package com.indus.training.persist.dao;

import java.util.List;

import com.indus.training.persist.entity.Department;
import com.indus.training.persist.entity.Employee;
import com.indus.training.persist.impl.DepartmentDaoImpl;
import com.indus.training.persist.impl.EmployeeDaoImpl;

import junit.framework.TestCase;

public class TestEmployeeDaoImpl extends TestCase {

	private EmployeeDaoImpl empDaoImplObj = null;
	private DepartmentDaoImpl deptDaoImplObj = null;

	protected void setUp() throws Exception {
		empDaoImplObj = new EmployeeDaoImpl();
		deptDaoImplObj = new DepartmentDaoImpl();
	}

	protected void tearDown() throws Exception {
		empDaoImplObj = null;
		deptDaoImplObj = null;
	}

	public void testGetEmployeeCountByDepartment() {
		Department department = new Department();
		department.setName("IT");

		Employee emp1 = new Employee();
		emp1.setFirstName("Navya");
		emp1.setLastName("Bade");

		Employee emp2 = new Employee();
		emp2.setFirstName("Roja");
		emp2.setLastName("Bade");

		try {
			deptDaoImplObj.insertDepartment(department);
			emp1.setDepartment(department);
			emp2.setDepartment(department);
			empDaoImplObj.insertEmployee(emp1);
			empDaoImplObj.insertEmployee(emp2);
			List<Object[]> employeeCountByDepartment = empDaoImplObj.getEmployeeCountByDepartment();
			assertNotNull("The result should not be null", employeeCountByDepartment);

			boolean isDepartmentFound = false;
			for (Object[] row : employeeCountByDepartment) {
				String departmentName = (String) row[0];
				Long count = (Long) row[1];

				if (department.getName().equals(departmentName)) {
					assertEquals("Employee count should be 2 for IT department", 2, count.intValue());
					isDepartmentFound = true;
				}
			}
			assertTrue("Department 'IT' should be found", isDepartmentFound);

		} catch (Exception e) {
			e.printStackTrace();
			fail("An exception occurred during the test: " + e.getMessage());
		} finally {
			try {
				empDaoImplObj.deleteEmployeeById(emp1.getEmployeeId());
				empDaoImplObj.deleteEmployeeById(emp2.getEmployeeId());
				deptDaoImplObj.deleteDepartmentById(department.getDepartmentId());
			} catch (Exception e) {
				e.printStackTrace();
				fail("An exception occurred during cleanup: " + e.getMessage());
			}
		}

	}

	public void testGetDistinctDepartmentNames() {
		 Department department1 = new Department();
		    department1.setName("IT");

		    Department department2 = new Department();
		    department2.setName("Business");
		    
		    Employee emp1 = new Employee();
		    emp1.setFirstName("Navya");
		    emp1.setLastName("Bade");

		    Employee emp2 = new Employee();
		    emp2.setFirstName("Roja");
		    emp2.setLastName("Bade");
		    
		    try {
		        deptDaoImplObj.insertDepartment(department1);
		        deptDaoImplObj.insertDepartment(department2);
		        emp1.setDepartment(department1);
		        emp2.setDepartment(department2);
		        empDaoImplObj.insertEmployee(emp1);
		        empDaoImplObj.insertEmployee(emp2);
		        List<String> departments = empDaoImplObj.getDistinctDepartmentNames();
		        assertNotNull("The result should not be null", departments);
		        assertTrue("Department names should contain 'IT'", departments.contains("IT"));
		        assertTrue("Department names should contain 'Business'", departments.contains("Business"));
		        assertEquals("There should be 2 distinct departments", 2, departments.size());
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        fail("An exception occurred during the test: " + e.getMessage());
		    } finally {
		        try {
		            empDaoImplObj.deleteEmployeeById(emp1.getEmployeeId());
		            empDaoImplObj.deleteEmployeeById(emp2.getEmployeeId());
		            deptDaoImplObj.deleteDepartmentById(department1.getDepartmentId());
		            deptDaoImplObj.deleteDepartmentById(department2.getDepartmentId());
		        } catch (Exception e) {
		            e.printStackTrace();
		            fail("An exception occurred during cleanup: " + e.getMessage());
		        }
		    }
		
		
	}

}
