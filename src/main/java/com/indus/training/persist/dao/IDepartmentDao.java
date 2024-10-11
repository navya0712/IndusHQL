package com.indus.training.persist.dao;

import com.indus.training.persist.entity.Department;
import com.indus.training.persist.exceptions.DepartmentDaoException;

public interface IDepartmentDao {

    /**
     * Inserts a new department into the database.
     * 
     * @param department The Department object to be inserted.
     * @return true if the department was successfully inserted, false otherwise.
     * @throws DepartmentDaoException If there is an issue during the insertion.
     */
    public Boolean insertDepartment(Department department) throws DepartmentDaoException;

    /**
     * Fetches a department by its ID.
     * 
     * @param departmentId The ID of the department to fetch.
     * @return The Department object corresponding to the given ID.
     * @throws DepartmentDaoException If there is an issue during the fetch.
     */
    public Department fetchDepartmentById(Integer departmentId) throws DepartmentDaoException;

    /**
     * Updates the details of a department by its ID.
     * 
     * @param departmentId The ID of the department to update.
     * @param department   The department details to be updated.
     * @return true if the update was successful, false otherwise.
     * @throws DepartmentDaoException If there is an issue during the update.
     */
    public Boolean updateDepartmentById(Integer departmentId, Department department) throws DepartmentDaoException;

    /**
     * Deletes a department by its ID.
     * 
     * @param departmentId The ID of the department to delete.
     * @return true if the deletion was successful, false otherwise.
     * @throws DepartmentDaoException If there is an issue during the deletion.
     */
    public Boolean deleteDepartmentById(Integer departmentId) throws DepartmentDaoException;
}
