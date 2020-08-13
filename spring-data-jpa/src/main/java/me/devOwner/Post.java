package me.devOwner;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
//@NamedQuery(name = "Post.findByTitle", query = "select p from Post p where p.title = ?1")
public class Post extends AbstractAggregateRoot<Post> {
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @Lob
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.setPost(this);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Post publish() {
        this.registerEvent(new PostPublishedEvent(this));
        return this;
    }
}
