package com.microservices.comment.Service;

import com.microservices.comment.Entity.Comment;
import com.microservices.comment.Payload.Post;
import com.microservices.comment.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Comment saveComment(Comment comment) {


        Post post = restTemplate.getForObject("http://POST-SERVICE/api/post/"+comment.getPostId(), Post.class);

        if (post != null) {
            String string = UUID.randomUUID().toString();
            comment.setCommentId(string);
            Comment savedComment = commentRepository.save(comment);
            return savedComment;
        } else {
            return null;
        }
    }


    public List<Comment> getAllCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;

    }
}
