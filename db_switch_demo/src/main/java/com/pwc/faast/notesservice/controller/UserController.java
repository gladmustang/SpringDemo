package com.pwc.faast.notesservice.controller;


import com.pwc.faast.notesservice.config.DynamicDataSourceContextHolder;
import com.pwc.faast.notesservice.entity.User;
import com.pwc.faast.notesservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @PostMapping(path="/add/{database}") // Map ONLY POST Requests
    public String addNewUser2 (@RequestParam String name
            , @RequestParam String email, @PathVariable String database) {
        DynamicDataSourceContextHolder.setDataSourceRouterKey(database);
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all/{database}")
    public Iterable<User> getAllUsers2(@PathVariable String database) {
        // This returns a JSON or XML with the users
        DynamicDataSourceContextHolder.setDataSourceRouterKey(database);
        return userRepository.findAll();
    }
}
