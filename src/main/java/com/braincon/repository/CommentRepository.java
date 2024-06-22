package com.braincon.repository;

import com.braincon.models.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    @Query("SELECT c, u.first_name FROM Comment c JOIN c.user u")
    List<Object[]> findAllCommentsWithUsernames();

}
