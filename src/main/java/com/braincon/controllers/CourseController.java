package com.braincon.controllers;

import com.braincon.dto.requests.UpdateCourseRequest;
import com.braincon.dto.responses.CourseCreatedResponse;
import com.braincon.dto.responses.GetAllCoursesResponse;
import com.braincon.helpers.ExtractUserIDFromTokenHelper;
import com.braincon.models.Course;
import com.braincon.models.Test;
import com.braincon.services.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ExtractUserIDFromTokenHelper extractUserIDFromTokenHelper;

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courseList = courseService.getAllCourses();
        if (courseList.isEmpty()) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }


    @GetMapping("/my_courses")
    public ResponseEntity getMyCourses(HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);

        if (user_id == null) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }

        List<Course> courseList = courseService.getAllCourses();

        if (courseList.isEmpty() || courseList == null) {
            return new ResponseEntity("Нет курсов для отображения", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(courseList, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity createCourse(@RequestParam("title") String title,
                                       @RequestParam("body") String body, HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);
        if (user_id == null) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }

        int result = courseService.createCourse(user_id, title, body);

        if (result != 1) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Курс создан успешно", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateCourseById(@RequestBody UpdateCourseRequest updateCourseRequest, HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);

        if (user_id == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
        int result =
                courseService.updateCourseIdByUserId(updateCourseRequest.getTitle(),
                        updateCourseRequest.getBody(),
                        updateCourseRequest.getCourse_id(), user_id);

        if (result != 1) {
            return new ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        CourseCreatedResponse response = new CourseCreatedResponse("Курс обновлен!");
        return new ResponseEntity(response, HttpStatus.CREATED);

    }

    @PostMapping("/delete")
    public ResponseEntity deleteCourseById(@RequestParam("course_id") String course_id, HttpServletRequest request) {

        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);

        if (user_id == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
        int courseId = Integer.parseInt(course_id);

        int result = courseService.deleteCourseByCourseIdAndUserId(courseId, user_id);

        if (result != 1) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity("Курс удален", HttpStatus.OK);

    }

    @GetMapping("/favorites")
    public ResponseEntity getFavoriteCourses(HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);
        if (user_id == null) {
            return new ResponseEntity("User ID не найден", HttpStatus.BAD_REQUEST);
        }
        List<Course> favoriteCourses = courseService.getFavoriteCoursesByUserId(user_id);
        if (favoriteCourses.isEmpty()) {
            return new ResponseEntity("Нет избранных курсов", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(favoriteCourses, HttpStatus.OK);
    }

    @PutMapping("/favorite")
    public ResponseEntity updateFavoriteStatus(@RequestParam("course_id") int course_id,
                                               @RequestParam("is_favorite") boolean isFavorite,
                                               HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);
        if (user_id == null) {
            return new ResponseEntity("User ID not found", HttpStatus.BAD_REQUEST);
        }
        int result = courseService.updateFavoriteStatus(course_id, user_id, isFavorite);
        if (result != 1) {
            return new ResponseEntity("Unable to update favorite status", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Favorite status updated successfully", HttpStatus.OK);
    }


    @GetMapping("/{courseId}/tests")
    public ResponseEntity<List<Test>> getTestsForCourse(@PathVariable int courseId) {
        List<Test> tests = courseService.getAllTestsForCourse(courseId);
        if (tests.isEmpty()) {
            return new ResponseEntity("No tests found for this course", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity   <>(tests, HttpStatus.OK);
    }


}