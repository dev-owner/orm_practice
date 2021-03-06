package me.devOwner;

import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PostRepositoryTestConfig.class)
public class WebPostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void event() {
        Post post = new Post();
        post.setTitle("event");
//        PostPublishedEvent postPublishedEvent = new PostPublishedEvent(post);
//        applicationContext.publishEvent(postPublishedEvent);
        postRepository.save(post.publish());
    }

    @Test
    @Rollback(false)
    public void crudRepository() {

        // Given
        Post post = new Post();
        post.setTitle("spring boot common");
        assertThat(post.getId()).isNull();

        // When
        Post newPost = postRepository.save(post);

        // Then
        assertThat(newPost.getId()).isNotNull();

        // When
        List<Post> posts = postRepository.findAll();

        // Then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts).contains(newPost);

        //When
        Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        // When
        Page<Post> spring = postRepository.findByTitleContains("spring", PageRequest.of(0, 10));

        // Then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        // When
        long spring1 = postRepository.countByTitleContains("spring");

        //Then
        assertThat(spring1).isEqualTo(1);
    }

    @Test
    public void crud2() {
        Post post = new Post();
        post.setTitle("hibernate");
        postRepository.save(post);
        //postRepository.findMyPost();
        postRepository.delete(post);
        postRepository.flush();
    }

    @Test
    public void crud3() {
        Post post = new Post();
        post.setTitle("hi");

        assertThat(postRepository.contains(post)).isFalse();

        postRepository.save(post);
        assertThat(postRepository.contains(post)).isTrue();
    }

    @Test
    public void query_dsl_test() {
        Post post = new Post();
        post.setTitle("hibernate");
        postRepository.save(post.publish());

        Predicate predicate = QPost.post.title.containsIgnoreCase("Hibernate");
        Optional<Post> one = postRepository.findOne(predicate);
        assertThat(one).isNotEmpty();

    }
}