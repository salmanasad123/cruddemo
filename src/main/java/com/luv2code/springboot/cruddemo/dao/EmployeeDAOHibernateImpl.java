package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    // define fields for EntityManger
    EntityManager entityManager;

    // setup constructor injection
    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {

        // get the current hibernate session
        // this will give us the current hibernate session, we get the current hibernate session from entity manager
        // using the function called unwrap
        Session currentSession = entityManager.unwrap(Session.class);

        // create query
        Query<Employee> employeeQuery = currentSession.createQuery("from Employee", Employee.class);

        // execute query and get the results
        List<Employee> employees = employeeQuery.getResultList();

        // return the results
        return employees;
    }

    @Override
    public Employee findOne(int employeeId) {
        // get the current hibernate session
        Session session = entityManager.unwrap(Session.class);

        Employee employee = session.get(Employee.class, employeeId);

        return employee;
    }

    @Override
    @Transactional
    public void save(Employee employee) {

        // get the current hibernate session
        Session session = entityManager.unwrap(Session.class);

        session.saveOrUpdate(employee);
    }

    @Override
    public void delete(int employeeId) {

        // get the current hibernate session
        Session session = entityManager.unwrap(Session.class);

        Query<Employee> employeeQuery = session.createQuery("delete from Employee where id=:employeeId");
        employeeQuery.setParameter("employeeId", employeeId);

        employeeQuery.executeUpdate();
    }
}
