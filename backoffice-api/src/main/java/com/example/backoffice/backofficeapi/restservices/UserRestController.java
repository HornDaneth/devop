package com.example.backoffice.backofficeapi.restservices;

import com.example.backoffice.backofficeapi.restservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(UserRestController.ROOT_URL)
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    protected static final String ROOT_URL = "/api/v1";

    private  final UserRepository userRepository;

    @Inject
    public UserRestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/users")
    void addUser(@RequestBody User user) {
        userRepository.save(user);
    }


}
