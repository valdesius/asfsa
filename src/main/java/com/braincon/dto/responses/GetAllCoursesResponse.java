package com.braincon.dto.responses;

import com.braincon.models.Course;

import java.util.List;

public class GetAllCoursesResponse {

    private List<Course> getAllCourses;

    public List<Course> getGetAllCourses() {
        return getAllCourses;
    }

    public void setGetAllCourses(List<Course> getAllCourses) {
        this.getAllCourses = getAllCourses;
    }
}
