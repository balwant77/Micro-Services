package com.microservices.post.post.Controller;

import com.microservices.post.post.Entity.Post;
import com.microservices.post.post.Payload.PostDto;
import com.microservices.post.post.Repository.PostRepository;
import com.microservices.post.post.Service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    //http://localhost:8081/api/v1/post
    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody Post post) {

        Post savedPost = postService.savePost(post);

        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }
    //http://localhost:8081/api/post/postId
    @GetMapping("/{postId}")//here we are passing path variable
    public Post getPostById(@PathVariable String postId) {
        Post postById = postService.getPostById(postId);


        return postById;
    }
    //http://localhost:8081/api/post/postId/comments
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
        PostDto postDto = postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex) {
        //While creating fallback method we need to give the variable name
        // and return type same as the method which is calling the rest template.
        System.out.println("Fallback is executed because service is down : "+ ex.getMessage());

        ex.printStackTrace();

        PostDto dto = new PostDto();
        dto.setId("1234");
        dto.setTitle("Service Down");
        dto.setContent("Service Down");
        dto.setDescription("Service Down");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }


}

    //When the method will make the call to rest template and comment service will be down Fallback method will execute.


