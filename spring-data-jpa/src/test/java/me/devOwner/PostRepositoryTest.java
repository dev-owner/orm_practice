package me.devOwner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

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
        post.setTitle("Spring Data Jpa");
        Post savedPost = postRepository.save(post);

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
        savePost();

        List<Post> spring = postRepository.findByTitleStartsWith("Spring");
        assertThat(spring.size()).isEqualTo(1);
    }

    @Test
    public void findByTitle() {
        savePost();

//        List<Post> spring = postRepository.findByTitle("Spring Data Jpa", Sort.by("title"));
        List<Post> spring = postRepository.findByTitle("Spring Data Jpa", JpaSort.unsafe("LENGTH(title)"));
        assertThat(spring.size()).isEqualTo(1);
    }


    @Test
    public void updateTitle() {
        Post post = savePost();
        int cnt = postRepository.updateTitle("hibernate", post.getId());
        assertThat(cnt).isEqualTo(1);

        // 1차 캐시에 있기 떄문에, db에는 업데이트 되었지만 객체에서는 업데이트가 되지 않는다.
        // @Modifying(clearAutomatically = true) 옵션을 통해 pc를 초기화 하고 find할 때 db 값을 읽어온다.
        // 따라서, update, delete는 이런 방식으로 안하는 것을 추천한다.
        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId.get().getTitle()).isEqualTo("hibernate");

    }

    @Test
    public void updateTitleBySet() {
        Post post = savePost();
        post.setTitle("changed");

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("changed");
    }


    private Post savePost() {
        Post post = new Post();
        post.setTitle("Spring Data Jpa");
        Post save = postRepository.save(post);
        return save;
    }
}