package com.test.plan.Service;

import com.test.plan.Entity.Users;
import com.test.plan.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Lazy
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }



    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedate(LocalDateTime.now());
        return userRepo.save(user);
    }
    public Optional<Users> login(String username, String password){
        Optional<Users> user = userRepo.findByUsername(username);
        if(user.isPresent()){
            if(passwordEncoder.matches(password,user.get().getPassword())){
                return user;
            }
        }
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user1 = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //return new org.springframework.security.core.userdetails.User(user1.getUsername(), user1.getPassword(), List.of()) {
        return User.builder()
                .username(user1.getUsername())
                .password(user1.getPassword())
                .build();
    };


}

