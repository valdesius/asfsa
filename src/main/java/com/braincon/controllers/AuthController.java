package com.braincon.controllers;

import com.braincon.dto.requests.LoginRequest;
import com.braincon.dto.responses.AuthResponse;
import com.braincon.services.JwtTokenService;
import com.braincon.services.auth.MyCustomUserDetailService;
import com.braincon.services.auth.MyCustomUserDetails;
import com.braincon.services.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private MyCustomUserDetailService myCustomUserDetailService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        MyCustomUserDetails userDetails =
                (MyCustomUserDetails) myCustomUserDetailService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtTokenService.generateToken(userDetails);
        System.out.println(token);
        AuthResponse response = new AuthResponse(token, userDetails);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }



    @PostMapping("/register")
    public ResponseEntity signUp(@RequestParam("first_name") String firstName,
                                 @RequestParam("last_name") String lastName,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("role") String role) {

        // TODO: VALIDATE IF EMAIL IS NOT ALREADY TAKEN.
        String hashed_password = passwordEncoder.encode(password);


        int result = userService.signUpUser(firstName, lastName, email, hashed_password, role);

        if (result != 1) {
            return new ResponseEntity("Что-то пошло не так", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Пользователь зарегистрирован успешно!", HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    public ResponseEntity updateUser(@RequestParam("first_name") String firstName,
                                     @RequestParam("last_name") String lastName,
                                     @RequestParam("email") String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        int userId = userService.getUserIdByEmail(currentUsername);

        userService.updateUser(firstName, lastName, email, userId); // Pass the user_id here

        return new ResponseEntity("Данные пользователя обновлены успешно!", HttpStatus.OK);
    }


}




