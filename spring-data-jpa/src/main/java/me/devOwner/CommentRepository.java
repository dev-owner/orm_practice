package me.devOwner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface CommentRepository extends MyRepository<Comment, Long> {

    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int number);

    List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);

    Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    Stream<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int number, Pageable pageable);

    @Async
    Future<List<Comment>> findByCommentContainsIgnoreCaseOrderByLikeCountAsc(String keyword, Pageable pageable);

    @EntityGraph(value = "Comment.post")
    //@EntityGraph(attributePaths = "post")
    Optional<Comment> getById(Long id);

}
