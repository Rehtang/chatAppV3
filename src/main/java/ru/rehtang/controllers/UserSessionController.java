package ru.rehtang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserSessionController {

    private final SimpUserRegistry userRegistry;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public UserSessionController(SimpUserRegistry userRegistry, SimpMessageSendingOperations messagingTemplate) {
        this.userRegistry = userRegistry;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/users")
    public void listUsers() {
        List<String> users = userRegistry.getUsers().stream()
                .map(SimpUser::getName)
                .collect(Collectors.toList());
        messagingTemplate.convertAndSend("/topic/users", users);
    }
}