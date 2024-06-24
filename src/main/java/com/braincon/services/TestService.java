package com.braincon.services;

import com.braincon.models.Test;

import com.braincon.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        return testRepository.getAllTests();
    }

    public List<Test> getTestsByUserId(Integer user_id) {
        return testRepository.getTestsByUserId(user_id);
    }

    public int createTest(int user_id, String title, String body, String question, String answer, int course_id) {
        return testRepository.createTest(user_id, title, body, question, answer, course_id);
    }

    public int updateTestIdByUserId(String title, String body, String question, int test_id, int user_id) {
        return testRepository.updateTestByTestIdAndUserId(title, body, question, test_id, user_id);
    }

    public int deleteTestByTestIdAndUserId(int test_id) {
        return testRepository.deleteTestByTestIdAndUserId(test_id);
    }

    public void deleteTestByCourseId(int course_id) {
        testRepository.deleteTestsByCourseId(course_id);
    }

}
