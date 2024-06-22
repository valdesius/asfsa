package com.braincon.services;

import com.braincon.models.Comment;
import com.braincon.models.User;
import com.braincon.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Object[]> findAllCommentsWithUsernames() {
        return commentRepository.findAllCommentsWithUsernames();
    }

    public int createComment(Integer userId, String content) {
        // Создание нового экземпляра комментария
        Comment newComment = new Comment();
        newComment.setUser(new User(userId)); // Предполагается, что класс User имеет конструктор с ID
        newComment.setContent(content);
        newComment.setCreatedAt(new Date()); // Установка текущей даты и времени создания комментария

        // Сохранение комментария в базе данных
        Comment savedComment = commentRepository.save(newComment);

        // Проверка, был ли комментарий успешно сохранен
        if (savedComment != null && savedComment.getComment_id() > 0) {
            return 1; // Возвращаем 1, если комментарий успешно создан
        } else {
            return 0; // Возвращаем 0, если произошла ошибка
        }
    }
    public Optional<Comment> findCommentById(int id) {
        return commentRepository.findById(id);
    }

    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}