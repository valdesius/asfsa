package com.braincon.dto.responses;

public class CourseCreatedResponse {

    private String response;

    public CourseCreatedResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
