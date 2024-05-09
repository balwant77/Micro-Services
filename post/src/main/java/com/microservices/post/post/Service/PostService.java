package com.microservices.post.post.Service;

import com.microservices.post.post.Entity.Post;
import com.microservices.post.post.Payload.Comment;
import com.microservices.post.post.Payload.PostDto;
import com.microservices.post.post.Repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;



@Service
public class PostService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PostRepository postRepository;
    public Post savePost(Post post){
        String postId = UUID.randomUUID().toString();
        post.setId(postId);
        Post save = postRepository.save(post);
        return save;


    }


    public Post getPostById(String postId) {
        Post postById = postRepository.findById(postId).get();
        return postById;
    }

    public PostDto getPostWithComments(String postId) {
        Post post = postRepository.findById(postId).get();

        ArrayList comments = restTemplate.getForObject("http://COMMENT-SERVICE/api/comment/"+postId, ArrayList.class);

        PostDto postDto = new PostDto();
        ModelMapper mapper = new ModelMapper();
        mapper.map(post, postDto);
        postDto.setComments(comments);
        return postDto;

    }
}

