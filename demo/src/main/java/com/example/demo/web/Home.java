package com.example.demo.web;

import com.example.demo.object.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
    @GetMapping("/")
    public String home(){
        return "hello world";
    }

    @GetMapping("/notes/get")
    public String getNotes(){
        return "get nodes page";
    }

    @GetMapping("/user/get")
    public String getUser(){
        return "get user page";
    }

    @GetMapping("/user/delay")
    public User getDelay(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User("gg", 16);
    }


}
