package org.ahad.hibernate;

import org.ahad.hibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public static void main(String[] args) {
        saveEmployee();
        loadSupervisor(4L);
        loadSubordinate(1L);
    }

    private static void saveEmployee() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Ceo
        Employee ceo = new Employee();
        ceo.setName("Elon");

        // Director reporting to Ceo
        Employee director = new Employee();
        director.setName("Sarah");

        // Employee reporting to Director
        Employee employee1 = new Employee();
        employee1.setName("David");

        Employee employee2 = new Employee();
        employee2.setName("Emily");

        // Set relationships
        ceo.addSubordinate(director);  // Elon supervises Sarah
        director.addSubordinate(employee1);  // Sarah supervises David
        director.addSubordinate(employee2);  // Sarah supervises Emily

        // Save entities (Hibernate will cascade the save operations)
        session.persist(ceo);  // This saves Ceo, Director, and Employees due to cascade

        session.getTransaction().commit();
        session.close();
    }

    private static void loadSupervisor(Long employeeId) {
        Session session = sessionFactory.openSession();
        Employee employee = session.get(Employee.class, employeeId);

        System.out.println("Employee: " + employee.getName());
        System.out.println("Supervisor: " + (employee.getSupervisor() != null ? employee.getSupervisor().getName() : "No Supervisor"));

        session.close();
    }

    private static void loadSubordinate(Long supervisorId) {
        Session session = sessionFactory.openSession();
        Employee supervisor = session.get(Employee.class, supervisorId);

        System.out.println("Supervisor: " + supervisor.getName());
        System.out.println("Subordinates:");
        for (Employee subordinate : supervisor.getSubordinates()) {
            System.out.println("- " + subordinate.getName());
        }

        session.close();
    }

}
