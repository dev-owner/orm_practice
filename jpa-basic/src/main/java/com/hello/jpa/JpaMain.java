package com.hello.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        //singleton
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        //Unit : Transaction / connection
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        try {
            Parent parent = new Parent();

            Child c1 = new Child();
            Child c2 = new Child();

            parent.addChild(c1);
            parent.addChild(c2);

            entityManager.persist(parent);

            entityManager.flush();
            entityManager.clear();

            Parent parent1 = entityManager.find(Parent.class, parent.getId());
            parent1.getChildren().remove(0);


            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();

    }
}
