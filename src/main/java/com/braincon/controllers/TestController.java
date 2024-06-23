package com.braincon.controllers;

import com.braincon.dto.requests.UpdateTestRequest;
import com.braincon.dto.responses.GetAllTestsResponse;
import com.braincon.dto.responses.TestCreatedResponse;
import com.braincon.helpers.ExtractUserIDFromTokenHelper;
import com.braincon.models.Test;
import com.braincon.services.TestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    @Autowired
    private TestService service;
    @Autowired
    private ExtractUserIDFromTokenHelper extractUserIDFromTokenHelper;

    @GetMapping("/all")
    public ResponseEntity<List<Test>> getAllTests() {
        System.out.println("In Tests Controller All Method.");
        // TODO: FOR TESTING:
        List<Test> testList = service.getAllTests();
        if (testList.isEmpty()) {
            return new ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST);
        }


        GetAllTestsResponse getAllTestsResponse = new GetAllTestsResponse();
        getAllTestsResponse.setGetAllTests(testList);

        return new ResponseEntity(getAllTestsResponse, HttpStatus.OK);
    }

    @GetMapping("/my_tests")
    public ResponseEntity getMyTests(HttpServletRequest request) {
        // EXTRACT USER ID FROM TOKEN:
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);

        if (user_id == null) {
            return new ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST);
        }

        List<Test> testList = service.getTestsByUserId(user_id);
        if (testList.isEmpty() || testList == null) {
            return new ResponseEntity("No tests to display", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(testList, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity createTest(@RequestParam("title") String title,
                                     @RequestParam("body") String body,
                                     @RequestParam("question") String question,
                                     @RequestParam("answer") String answer,
                                     @RequestParam("course_id") String course_id,
                                     HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);


        if (user_id == null) {
            // RETURN ERROR RESPONSE:
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }


        int result = service.createTest(user_id, title, body, question, answer, Integer.parseInt(course_id));

        if (result != 1) {
            // RETURN ERROR RESPONSE:
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Тест создан успешно", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateTestById(@RequestBody UpdateTestRequest updateTestRequest, HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);
        if (user_id == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        int result =
                service.updateTestIdByUserId(updateTestRequest.getTitle(),
                        updateTestRequest.getBody(),
                        updateTestRequest.getQuestion(),
                        updateTestRequest.getTest_id(), user_id);

        if (result != 1) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }

        TestCreatedResponse response = new TestCreatedResponse("Тест обновлен успешно");
        return new ResponseEntity(response, HttpStatus.CREATED);

    }

    @PostMapping("/delete")
    public ResponseEntity deleteTestById(@RequestParam("test_id") String test_id, HttpServletRequest request) {
        Integer user_id = extractUserIDFromTokenHelper.getUserIdFromToken(request);

        if (user_id == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }

        int testId = Integer.parseInt(test_id);

        int result = service.deleteTestByTestIdAndUserId(testId);

        if (result != 1) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Тест удален успешно", HttpStatus.OK);

    }


}