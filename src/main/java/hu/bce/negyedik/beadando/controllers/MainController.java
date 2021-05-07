package hu.bce.negyedik.beadando.controllers;

import hu.bce.negyedik.beadando.repository.UserRepository;
import hu.bce.negyedik.beadando.models.User;
import hu.bce.negyedik.beadando.utils.AuthResponse;
import hu.bce.negyedik.beadando.utils.SimplePasswordEncoder;
import hu.bce.negyedik.beadando.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MainController {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/register")
    public AuthResponse registerUser(@Valid @RequestBody User newUser) {
        List<User> users = userRepository.findAll();

        if(newUser.getUsername().length() < 6) {
            return new AuthResponse(Status.FAILURE, "Username has to be at least 6 characters long");
        }

        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher m = p.matcher(newUser.getPassword());
        boolean matchRestult = m.matches();
        if(!matchRestult) {
            return new AuthResponse(Status.FAILURE, "Password has to be at least 8 characters long and has to contain letters and digits");
        }

        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                return new AuthResponse(Status.FAILURE, "User with that username already exists");
            }
        }

        try {
            newUser.setPassword(new SimplePasswordEncoder().encodePassword(newUser.getPassword()));
        }
        catch(Exception NoSuchAlgorithmException) {
            return new AuthResponse(Status.FAILURE, "Error during creating the user");
        }
        userRepository.save(newUser);
        return new AuthResponse(Status.SUCCESS, "User successfully created");
    }
    @PostMapping("/login")
    public AuthResponse userLogin(@Valid @RequestBody User loginUser) {
        List<User> users = userRepository.findAll();
        try {
            loginUser.setPassword(new SimplePasswordEncoder().encodePassword(loginUser.getPassword()));
        }
        catch(Exception NoSuchAlgorithmException) {
            return new AuthResponse(Status.FAILURE, "Error during authenticating the user");
        }
        for (User other : users) {
            if (other.equals(loginUser)) {
                return new AuthResponse(Status.SUCCESS, "User successfully logged in");
            }
        }
        return new AuthResponse(Status.FAILURE, "User login attempt was unsuccessful");
    }
}