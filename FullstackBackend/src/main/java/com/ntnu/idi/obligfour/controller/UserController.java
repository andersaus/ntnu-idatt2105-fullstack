package com.ntnu.idi.obligfour.controller;

import com.ntnu.idi.obligfour.model.User;
import com.ntnu.idi.obligfour.repository.UserRepositoryInterface;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepositoryInterface userRepository;

    @Autowired
    public UserController(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> user = userRepository.findByUsername(username);
        /*return user.map(value -> ResponseEntity.ok(value.getId()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));*/
        if(user.isPresent()) {
         Map<String, Object> responseBody = new HashMap<>();
         responseBody.put("userID", user.get().getId());
         responseBody.put("username", user.get().getUsername());

         return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        return Optional.of(user)
                .map(u -> {
                    userRepository.save(new User(u.getUsername(), u.getPassword()));
                    return ResponseEntity.status(HttpStatus.CREATED).body("User was created successfully.");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User information is not valid."));
    }

    @PostMapping("/check-password")
    public ResponseEntity<?> checkPassword(@RequestBody User user, HttpServletResponse response) {
        boolean isPasswordCorrect = userRepository.checkPassword(user.getUsername(), user.getPassword());
        return isPasswordCorrect ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
