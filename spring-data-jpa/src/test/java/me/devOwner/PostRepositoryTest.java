package me.devOwner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void save() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post); // persist

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(savedPost == post);

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");
        Post savedPostUpdate = postRepository.save(postUpdate);// merge

        assertThat(entityManager.contains(savedPostUpdate)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(postUpdate == savedPostUpdate).isFalse();


        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void findByTitleStartsWith() {
        Post post = new Post();
        post.setTitle("Spring Data Jpa");
        Post save = postRepository.save(post);

        List<Post> spring = postRepository.findByTitleStartsWith("Spring");
        assertThat(spring.size()).isEqualTo(1);
    }

    @Test
    public void findByTitle() {
        Post post = new Post();
        post.setTitle("Spring Data Jpa");
        Post save = postRepository.save(post);

        List<Post> spring = postRepository.findByTitle("Spring Data Jpa");
        assertThat(spring.size()).isEqualTo(1);
    }

}