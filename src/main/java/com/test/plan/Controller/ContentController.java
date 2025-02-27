package com.test.plan.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/v1/auth/login")
    public String login(){
        return "login";
    }

    @GetMapping("/v1/auth/register")
    public String signup(){
        return "signup";
    }
    @GetMapping("/welcome")
    public String home(){
        return "welcome";
    }


}
