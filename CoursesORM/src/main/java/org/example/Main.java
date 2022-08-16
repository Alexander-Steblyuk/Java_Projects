package org.example;

import org.example.entity.course.Course;
import org.example.entity.purchase.Purchase;
import org.example.entity.purchase.PurchaseList;
import org.example.entity.purchase.PurchasePK;
import org.example.entity.student.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) throws NoSuchFieldException {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        String purchaseListSql = "FROM " + PurchaseList.class.getSimpleName();

        List<PurchaseList> purchases = session.createQuery(purchaseListSql, PurchaseList.class).getResultList();

        Transaction transaction = session.beginTransaction();

        for (PurchaseList purchase : purchases) {
            String courseSql = "FROM " + Course.class.getSimpleName() + " where name = \"" +
                    purchase.getPurchaseListPK().getCourseName() + "\"";

            String studentSql = "FROM " + Student.class.getSimpleName() + " where name = \"" +
                    purchase.getPurchaseListPK().getStudentName() + "\"";

            Course course = session.createQuery(courseSql, Course.class).getSingleResult();
            Student student = session.createQuery(studentSql, Student.class).getSingleResult();

            Purchase purchaseNew = new Purchase();
            purchaseNew.setPurchasePK(new PurchasePK(student, course));
            session.save(purchaseNew);
        }

        transaction.commit();

       /* Student student = session.get(Student.class, 1);
        System.out.println(student.getCourses().size());*/

        session.close();
    }
}
