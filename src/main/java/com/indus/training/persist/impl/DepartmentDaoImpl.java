package com.indus.training.persist.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.indus.training.persist.dao.IDepartmentDao;
import com.indus.training.persist.entity.Department;
import com.indus.training.persist.exceptions.DepartmentDaoException;
import com.indus.training.persist.util.HibernateUtil;

public class DepartmentDaoImpl implements IDepartmentDao {

    /**
     * Inserts a new department into the database.
     * 
     * @param department The Department object to be inserted.
     * @return true if the department was successfully inserted, false otherwise.
     * @throws DepartmentDaoException If there is an issue during the insertion.
     */
    @Override
    public Boolean insertDepartment(Department department) throws DepartmentDaoException {
        if (department == null) {
            throw new DepartmentDaoException("Department Object Cannot be null");
        }
        Session session = null;
        Boolean isInserted = false;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.getTransaction().begin();
            session.save(department);
            session.getTransaction().commit();
            isInserted = true;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
           e.printStackTrace();
            throw new DepartmentDaoException("An error occurred while saving Department Object to database");
        }
        return isInserted;
    }

    /**
     * Fetches a department by its ID.
     * 
     * @param departmentId The ID of the department to fetch.
     * @return The Department object corresponding to the given ID.
     * @throws DepartmentDaoException If there is an issue during the fetch.
     */
    @Override
    public Department fetchDepartmentById(Integer departmentId) throws DepartmentDaoException {
        Session session = null;
        Department department = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.getTransaction().begin();
            department = (Department) session.get(Department.class, departmentId);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new DepartmentDaoException("An error occurred while fetching Department Details");
        }
        return department;
    }

    /**
     * Updates the details of a department by its ID.
     * 
     * @param departmentId The ID of the department to update.
     * @param department   The details of the department.
     * @return true if the update was successful, false otherwise.
     * @throws DepartmentDaoException If there is an issue during the update.
     */
    @Override
    public Boolean updateDepartmentById(Integer departmentId, Department department) throws DepartmentDaoException {
        Session session = null;
        Boolean isUpdated = false;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.getTransaction().begin();
            Department existingDepartment = (Department) session.get(Department.class, departmentId);
            if (existingDepartment != null) {
                existingDepartment.setName(department.getName());
                session.update(existingDepartment);
                isUpdated = true;
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new DepartmentDaoException("An error occurred while updating Department Details");
        }
        return isUpdated;
    }

    /**
     * Deletes a department by its ID.
     * 
     * @param departmentId The ID of the department to delete.
     * @return true if the deletion was successful, false otherwise.
     * @throws DepartmentDaoException If there is an issue during the deletion.
     */
    @Override
    public Boolean deleteDepartmentById(Integer departmentId) throws DepartmentDaoException {
        Session session = null;
        Boolean isDeleted = false;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.getTransaction().begin();
            Department existingDepartment = (Department) session.get(Department.class, departmentId);
            if (existingDepartment != null) {
                session.delete(existingDepartment);
                isDeleted = true;
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new DepartmentDaoException("An error occurred while deleting the Department Object");
        }
        return isDeleted;
    }
    
    
    
    
}
