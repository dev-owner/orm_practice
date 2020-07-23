package me.devOwner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepository extends MyRepository<Comment, Long> {

    List<Comment> findByCommentContains(String keyword);

    Page<Comment> findByLikeCountGreaterThanAndPost(int likeCount, Post post, Pageable pageable);
}
