package me.devOwner;

import org.springframework.context.ApplicationListener;

public class PostListener implements ApplicationListener<PostPublishedEvent> {

    @Override
    public void onApplicationEvent(PostPublishedEvent postPublishedEvent) {
        System.out.println("=====================");
        System.out.println("Post published.");
        System.out.println("=====================");
    }
}
