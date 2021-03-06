package me.devOwner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface CommentRepository extends MyRepository<Comment, Long>, JpaSpecificationExecutor<Comment>, QueryByExampleExecutor<Comment> {

    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int number);

    List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);

    Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    Stream<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int number, Pageable pageable);

    @Async
    Future<List<Comment>> findByCommentContainsIgnoreCaseOrderByLikeCountAsc(String keyword, Pageable pageable);

    @EntityGraph(value = "Comment.post")
    //@EntityGraph(attributePaths = "post")
    Optional<Comment> getById(Long id);

    @Transactional(readOnly = true)
    <T> List<T> findByPost_Id(Long id, Class<T> type);

}
