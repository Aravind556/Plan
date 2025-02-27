package com.test.plan.Controller;

import com.test.plan.Entity.Users;
import com.test.plan.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
    public String login(@RequestBody Map<String, String> credentials, HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Optional<Users> user = userservice.login(username, password);

        if (user.isPresent()) {
            session.setAttribute("user", user.get()); // Stores user in session
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clears session
        return "Logged out successfully";
    }
}
