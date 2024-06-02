package ru.rehtang.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user")
    public Map<String, String> user(@AuthenticationPrincipal User user) {
        if (user != null) {
            logger.info("Authenticated user: " + user.getUsername());
            return Collections.singletonMap("name", user.getUsername());
        } else {
            logger.warn("No authenticated user found.");
            return Collections.singletonMap("name", "Anonymous");
        }
    }
}