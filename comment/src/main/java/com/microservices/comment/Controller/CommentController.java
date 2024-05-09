package com.microservices.comment.Controller;

import com.microservices.comment.Entity.Comment;
import com.microservices.comment.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
   private CommentService commentService;
    //http://localhost:8082/api/comment
    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment){

        Comment savedComment = commentService.saveComment(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }


    @GetMapping("/{postId}")
    public List<Comment> getAllCommentsByPostId(@PathVariable  String postId) {
         List<Comment> comments= commentService.getAllCommentsByPostId(postId);
         return comments;
    }


}
