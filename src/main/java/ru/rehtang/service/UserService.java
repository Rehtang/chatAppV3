package ru.rehtang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rehtang.model.entity.User;
import ru.rehtang.model.repository.UserRepository;

import javax.annotation.PostConstruct;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("user1") == null) {
            User user1 = new User();
            user1.setUsername("user1");
            user1.setPassword(passwordEncoder.encode("password1"));
            userRepository.save(user1);
        }

        if (userRepository.findByUsername("user2") == null) {
            User user2 = new User();
            user2.setUsername("user2");
            user2.setPassword(passwordEncoder.encode("password2"));
            userRepository.save(user2);
        }
    }
}
