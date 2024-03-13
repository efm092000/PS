package com.dailyfit.user.controller;

import com.dailyfit.routine.service.RoutineService;
import com.dailyfit.routine.Routine;
import com.dailyfit.user.User;
import com.dailyfit.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final RoutineService routineService;

    public UserControllerImpl(UserService userService, RoutineService routineService) {
        this.userService = userService;
        this.routineService = routineService;
    }

    @PostMapping(value = "/{email}")
    public ResponseEntity<User> createUser(@PathVariable String email,
                                           @RequestParam String password,
                                           @RequestParam String name) {
        User user;
        try {
            user = userService.createUser(email, password, name);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> optionalUser = userService.getUserByEmail(email);
            return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = "/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) String name) {
        User user;
        try {
            user = userService.updateUser(email, password, name);
            Optional<User> optionalUser = userService.getUserByEmail(email);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(user);
    }


    @DeleteMapping(value = "/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok("User was deleted successfully");
    }

    @GetMapping(value = "/{email}/routines")
    public ResponseEntity<List<Routine>> getUserRoutines(@PathVariable String email) {
        try {
            List<Routine> routines = routineService.getUserRoutines(email);
            return ResponseEntity.ok(routines);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
