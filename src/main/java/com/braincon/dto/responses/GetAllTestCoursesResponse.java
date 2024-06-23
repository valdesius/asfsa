package com.braincon.dto.responses;

import com.braincon.models.Course;
import com.braincon.models.Test;

import java.util.List;

public class GetAllTestCoursesResponse {
    private List<Test> getAllCoursesTests;

    public List<Test> getGetAllTestCourses() {
        return getAllCoursesTests;
    }

    public void setGetAllCourses(List<Test> getAllCourses) {
        this.getAllCoursesTests = getAllCoursesTests;
    }
}
