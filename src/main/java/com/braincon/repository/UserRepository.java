package com.braincon.repository;

import com.braincon.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT email FROM users WHERE email = :email", nativeQuery = true)
    List<String> doesEmailExist(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

    @Query(value = "SELECT user_id FROM users WHERE email = :email ", nativeQuery = true)
    int getUserIdByEmail(@Param("email") String email);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.first_name = :first_name, u.last_name = :last_name, u.email = :email WHERE u.user_id = :user_id")
    void updateUser(@Param("first_name") String first_name,
                    @Param("last_name") String last_name,
                    @Param("email") String email,
                    @Param("user_id") int user_id); // Ensure this parameter is passed when calling the method

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (:first_name, :last_name, :email, :password, :role)", nativeQuery = true)
    int signUpUser(@Param("first_name") String first_name, @Param("last_name") String last_name, @Param("email") String email, @Param("password") String password, @Param("role") String role);
}

