package me.devOwner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {
public interface PostRepository extends MyCustomCommonRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
    // boiler plate code
    /*@PersistenceContext
    EntityManager em;

    public Post add(Post post) {
        em.persist(post);
        return post;
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }*/

    Page<Post> findByTitleContains(String title, Pageable pageable);

    long countByTitleContains(String title);
}
