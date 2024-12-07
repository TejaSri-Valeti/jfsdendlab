package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ClientDemo {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        // Insert Operation
        insertDepartment("CSE", "Block A", "Dr. Smith");
        insertDepartment("ECE", "Block B", "Dr. Johnson");

        // Delete Operation
        deleteDepartmentById(1);
    }

    public static void insertDepartment(String name, String location, String hodName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Department department = new Department();
            department.setName(name);
            department.setLocation(location);
            department.setHodName(hodName);
            session.save(department);
            transaction.commit();
            System.out.println("Department inserted successfully!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void deleteDepartmentById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = "delete from Department where id = :departmentId";
            Query query = session.createQuery(hql);
            query.setParameter("departmentId", id);
            int result = query.executeUpdate();
            transaction.commit();
            if (result > 0) {
                System.out.println("Department deleted successfully!");
            } else {
                System.out.println("Department with ID " + id + " not found!");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
