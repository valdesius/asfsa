package com.braincon.repository;

import com.braincon.models.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
    @Query(value = "SELECT * FROM courses", nativeQuery = true)
    List<Course> getAllCourses();

    @Query(value = "SELECT * FROM courses WHERE user_id = :user_id", nativeQuery = true)
    List<Course> getCoursesByUserId(@Param("user_id") int user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE courses SET title = :title, body = :body, is_updated = 1 WHERE course_id = :course_id AND user_id = :user_id",nativeQuery = true )
    int updateCourseByCourseIdAndUserId(@Param("title")String title, @Param("body")String body,@Param("course_id") int course_id ,@Param("user_id")int user_id);

    @Transactional
    @Modifying

    @Query(value = "DELETE FROM courses WHERE course_id = :course_id AND user_id = :user_id", nativeQuery = true)
    int deleteCourseByCourseIdAndUserId(@Param("course_id")int course_id, @Param("user_id")int user_id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO courses(user_id, title, body, is_favorite) VALUES(:user_id, :title, :body, FALSE)", nativeQuery = true)
    int createCourse(@Param("user_id") int user_id, @Param("title") String tile, @Param("body") String body);


    @Modifying
    @Transactional
    @Query(value = "UPDATE courses SET is_favorite = :isFavorite WHERE course_id = :course_id AND user_id = :user_id", nativeQuery = true)
    int updateFavoriteStatus(@Param("isFavorite") boolean isFavorite, @Param("course_id") int course_id, @Param("user_id") int user_id);

    @Query(value = "SELECT * FROM courses WHERE user_id = :user_id AND is_favorite = true", nativeQuery = true)
    List<Course> getFavoriteCoursesByUserId(@Param("user_id") int user_id);
}