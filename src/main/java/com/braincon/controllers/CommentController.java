package com.braincon.controllers;

import com.braincon.helpers.ExtractUserIDFromTokenHelper;
import com.braincon.models.Comment;
import com.braincon.services.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final ExtractUserIDFromTokenHelper extractUserIDFromTokenHelper;

    @Autowired
    public CommentController(CommentService commentService, ExtractUserIDFromTokenHelper extractUserIDFromTokenHelper) {
        this.commentService = commentService;
        this.extractUserIDFromTokenHelper = extractUserIDFromTokenHelper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Object[]>> getAllComments() {
        List<Object[]> commentsWithUsernames = commentService.findAllCommentsWithUsernames();
        return ResponseEntity.ok(commentsWithUsernames);
    }

    @PostMapping("/createComment")
    public ResponseEntity<String> createComment(@RequestParam("content") String content,
                                                HttpServletRequest request) {
        Integer userId = extractUserIDFromTokenHelper.getUserIdFromToken(request);

        if (userId == null) {
            // Возврат ответа об ошибке:
            return new ResponseEntity<>("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }

        int result = commentService.createComment(userId, content);

        if (result != 1) {
            return new ResponseEntity<>("Не удалось создать комментарий", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Комментарий создан успешно", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int id) {
        Optional<Comment> comment = commentService.findCommentById(id);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}