package com.test.plan.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TaskViewController {

    @GetMapping({"", "/tasks"})
    public String tasksPage() {
        return "tasks";
    }
} 