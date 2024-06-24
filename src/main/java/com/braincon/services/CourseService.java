package com.braincon.services;

import com.braincon.models.Course;
import com.braincon.models.Test;
import com.braincon.repository.CourseRepository;
import com.braincon.repository.TestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestRepository testRepository;

    public List<Course> getAllCourses(){
        return courseRepository.getAllCourses();
    }

    public List<Course> getCoursesByUserId(Integer user_id){
        return courseRepository.getCoursesByUserId(user_id);
    }
    public int createCourse(int user_id, String title, String body){
        return courseRepository.createCourse(user_id, title, body);
    }

    public int updateCourseIdByUserId(String title, String body, int course_id, int user_id){
        return courseRepository.updateCourseByCourseIdAndUserId(title, body, course_id, user_id);
    }

    @Transactional
    public int deleteCourseByCourseIdAndUserId(int course_id, int user_id){
        testRepository.deleteTestsByCourseId(course_id); // Удаление всех тестов связанных с курсом
        return courseRepository.deleteCourseByCourseIdAndUserId(course_id, user_id); // Удаление курса
    }
    public List<Course> getFavoriteCoursesByUserId(int user_id) {
        return courseRepository.getFavoriteCoursesByUserId(user_id);
    }

    public int updateFavoriteStatus(int course_id, int user_id, boolean isFavorite) {
        return courseRepository.updateFavoriteStatus(isFavorite, course_id, user_id);
    }

    public List<Test> getAllTestsForCourse(int courseId) {
        return testRepository.findByCourseId(courseId);
    }



}