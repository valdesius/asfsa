package com.braincon.dto.responses;

import com.braincon.models.Test;

import java.util.List;

public class GetAllTestsResponse {
    private List<Test> getAllTests;

    public List<Test> getGetAllTests() {
        return getAllTests;
    }

    public void setGetAllTests(List<Test> getAllTests) {
        this.getAllTests = getAllTests;
    }
}
