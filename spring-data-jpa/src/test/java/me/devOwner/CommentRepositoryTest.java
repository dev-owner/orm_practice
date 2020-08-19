package me.devOwner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static me.devOwner.CommentSpecs.isBest;
import static me.devOwner.CommentSpecs.isGood;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void crud() throws IllegalAccessException {

        List<Comment> all1 = commentRepository.findAll();
        assertThat(all1).isEmpty();

        Comment comment = new Comment();
        comment.setComment("hello jpa");
        commentRepository.save(comment);

        List<Comment> all = commentRepository.findAll();

        assertThat(all.size()).isEqualTo(1);

        long count = commentRepository.count();
        assertThat(count).isEqualTo(1);

        Optional<Comment> byId = commentRepository.findById(100l);
        assertThat(byId).isEmpty();
    }

    @Test
    public void test_query() throws ExecutionException, InterruptedException {
        this.createComment("Spring data jpa", 100);
        this.createComment("Spring data v", 200);

        List<Comment> comments = commentRepository.
                findByCommentContainsIgnoreCaseAndLikeCountGreaterThan("Spring", 10);
        assertThat(comments.size()).isEqualTo(2);

        List<Comment> spring = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("Spring");
        assertThat(spring.size()).isEqualTo(2);
        assertThat(spring).first().hasFieldOrPropertyWithValue("likeCount", 200);

        PageRequest likeCount = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "LikeCount"));
        Page<Comment> spring1 = commentRepository.findByCommentContainsIgnoreCase("Spring", likeCount);
        assertThat(spring1.getNumberOfElements()).isEqualTo(2);
        assertThat(spring1).first().hasFieldOrPropertyWithValue("likeCount", 200);


        try (Stream<Comment> spring2 = commentRepository.
                findByCommentContainsIgnoreCaseAndLikeCountGreaterThan("spring", 10, likeCount)) {
            Comment firstComment = spring2.findFirst().get();
            assertThat(firstComment.getLikeCount()).isEqualTo(200);
        }

        Future<List<Comment>> spring2 =
                commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountAsc("spring", likeCount);
        System.out.println("is done? : " + spring2.isDone());

        List<Comment> comments1 = spring2.get();
        comments1.forEach(System.out::println);

    }

    @Test
    public void getComment() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("jpa");
        comment.setPost(savedPost);
        comment.setUp(10);
        comment.setDown(1);
        commentRepository.save(comment);

        commentRepository.findByPost_Id(savedPost.getId(), CommentOnly.class).forEach(c -> {
            System.out.println(c.getComment());
        });

        Optional<Comment> byId = commentRepository.findById(1l);
        commentRepository.getById(1l);

        //closed projection
        commentRepository.findByPost_Id(1l, CommentSummary.class);
    }

    @Test
    public void specs() {
        Page<Comment> all = commentRepository.
                findAll(isBest().or(isGood()),
                        PageRequest.of(0, 10));
    }

    private void createComment(String text, int likeCount) {
        Comment comment = new Comment();
        comment.setComment(text);
        comment.setLikeCount(likeCount);
        commentRepository.save(comment);
    }

}