package me.devOwner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    List<Post> findByTitleStartsWith(String title);

    @Query("select p from #{#entityName} p where p.title = :title")
    List<Post> findByTitle(@Param("title") String keyword, Sort sort);

    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.title = ?1 where p.id = ?2")
    int updateTitle(String title, Long id);
}
