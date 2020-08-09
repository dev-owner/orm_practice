package me.devOwner.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WebPostController {

    @Autowired
    WebPostRepository webPostRepository;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id) {
        Optional<WebPost> byId = webPostRepository.findById(id);
        WebPost post = byId.get();
        return post.getTitle();
    }

    @GetMapping("/posts")
    public Page<WebPost> getPosts(Pageable pageable) {
        return webPostRepository.findAll(pageable);
    }
}
