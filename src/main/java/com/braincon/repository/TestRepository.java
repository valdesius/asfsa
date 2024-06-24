package com.braincon.repository;

import com.braincon.models.Test;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer> {

    @Query(value = "SELECT * FROM tests", nativeQuery = true)
    List<Test> getAllTests();

    @Query(value = "SELECT * FROM tests WHERE user_id = :user_id", nativeQuery = true)
    List<Test> getTestsByUserId(@Param("user_id") int user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tests SET title = :title, body = :body, question = :question, is_updated = 1 WHERE test_id = :test_id AND user_id = :user_id",nativeQuery = true )
    int updateTestByTestIdAndUserId(@Param("title")String title, @Param("body")String body, @Param("question") String question, @Param("test_id") int test_id ,@Param("user_id")int user_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tests WHERE test_id = :test_id ", nativeQuery = true)
    int deleteTestByTestIdAndUserId(@Param("test_id")int test_id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tests WHERE course_id = :course_id", nativeQuery = true)
    void deleteTestsByCourseId(@Param("course_id") int course_id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tests(user_id, title,body, question, answer, course_id) VALUES(:user_id, :title, :body, :question, :answer, :course_id)", nativeQuery = true)
    int createTest(@Param("user_id") int user_id, @Param("title") String title, @Param("body") String body, @Param("question") String question, @Param("answer") String answer, @Param("course_id") int course_id);
    @Query(value = "SELECT * FROM tests WHERE course_id = :course_id", nativeQuery = true)
    List<Test> findByCourseId(@Param("course_id") int course_id);
}