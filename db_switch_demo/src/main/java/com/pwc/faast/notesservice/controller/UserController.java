package com.pwc.faast.notesservice.controller;


import com.pwc.faast.notesservice.config.DynamicDataSourceContextHolder;
import com.pwc.faast.notesservice.config.DynamicDataSourceCreator;
import com.pwc.faast.notesservice.entity.User;
import com.pwc.faast.notesservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired EntityManager em;

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
        if(!DynamicDataSourceContextHolder.containsDataSource(database)) {
            LinkedHashMap<String, String> config = new LinkedHashMap();
            config.put("key", database); //need to set pguid in future
            config.put("url", "jdbc:postgresql://localhost:5432/"+database);
            config.put("username", "postgres");
            config.put("password", "admin");
            DynamicDataSourceCreator.addNewDataSource(config);

        }
        // This returns a JSON or XML with the users
        DynamicDataSourceContextHolder.setDataSourceRouterKey(database);

        return userRepository.findAll();
    }
}
