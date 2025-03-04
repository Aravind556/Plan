package com.test.plan.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.plan.Entity.Users;
import com.test.plan.Service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private  UserService userservice;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody Users user){
        return ResponseEntity.ok(userservice.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Optional<Users> user = userservice.login(username, password);

        if (user.isPresent()) {
            session.setAttribute("user", user.get()); // Stores user in session
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body("not present");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clears session
        return "Logged out successfully";
    }

}
