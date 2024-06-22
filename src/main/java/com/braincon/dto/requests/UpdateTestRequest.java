package com.braincon.dto.requests;

public class UpdateTestRequest {
    private int test_id;
    private String title;
    private String body;
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int course_id) {
        this.test_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
