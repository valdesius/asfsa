package com.braincon.services.auth;

import com.braincon.models.User;
import com.braincon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public int getUserIdByEmail(String email) {
        return userRepository.getUserIdByEmail(email);
    }
    public User loadUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public List<String> doesEmailExist(String email){
        return userRepository.doesEmailExist(email);
    }


    public int signUpUser(String first_name, String last_name, String email, String password, String role){
        return userRepository.signUpUser(first_name, last_name, email, password, role);
    }

    public void updateUser(String first_name, String last_name, String email, int user_id) {
        userRepository.updateUser(first_name, last_name, email, user_id );
    }

}
