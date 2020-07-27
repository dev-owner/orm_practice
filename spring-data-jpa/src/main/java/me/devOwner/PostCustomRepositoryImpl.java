package me.devOwner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class PostCustomRepositoryImpl implements PostCustomRepository<Post>{

    @Autowired
    EntityManager em;

    @Override
    public List<Post> findMyPost() {
        System.out.println("custom findMyPost");

        return em.createQuery("select p from Post p").getResultList();
    }

    @Override
    public void delete(Post entity) {
        System.out.println("custom delete");
        em.remove(entity);
    }
}
