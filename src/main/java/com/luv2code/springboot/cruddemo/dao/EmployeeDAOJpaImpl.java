package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {

        // create a query
        Query theQuery = entityManager.createQuery("from Employee");

        // execute query and get results
        List<Employee> employees = theQuery.getResultList();

        // send the results
        return employees;
    }

    @Override
    public Employee findOne(int employeeId) {

        // get the employee
        Employee employee = entityManager.find(Employee.class, employeeId);

        // return the employee
        return employee;
    }

    @Override
    public void save(Employee employee) {
        Employee emp = entityManager.merge(employee);

        emp.setId(0);

    }

    @Override
    public void delete(int employeeId) {

        // employeeId is a parameter
        Query query = entityManager.createQuery("delete from Employee where id=:employeeId");

        query.setParameter("employeeId", employeeId);

        // execute query
        query.executeUpdate();
    }
}
