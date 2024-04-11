package com.dailyfit.user.controller;

import com.dailyfit.routine.service.RoutineService;
import com.dailyfit.routine.Routine;
import com.dailyfit.user.User;
import com.dailyfit.user.UserDTO;
import com.dailyfit.user.exception.UserAlreadyExistsException;
import com.dailyfit.user.service.UserService;
import com.dailyfit.weekly.WeeklyPlan;
import com.dailyfit.weekly.service.WeeklyPlanService;
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
    private final WeeklyPlanService weeklyPlanService;

    public UserControllerImpl(UserService userService, RoutineService routineService, WeeklyPlanService weeklyPlanService) {
        this.userService = userService;
        this.routineService = routineService;
        this.weeklyPlanService = weeklyPlanService;
    }

    @PostMapping(value = "/{email}")
    public ResponseEntity<?> createUser(@PathVariable String email,
                                           @RequestParam String password,
                                           @RequestParam String name) {
        try {
            User user = userService.createUser(email, password, name);
            return ResponseEntity.ok(new UserDTO(user.email(), user.name()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody User user) {
        try {
            Optional<User> optionalUser = userService.authenticateUser(user.email(), user.password());
            return optionalUser.map(value -> ResponseEntity.ok(new UserDTO(value.email(), value.name()))).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> optionalUser = userService.getUserByEmail(email);
            return optionalUser.map(user -> ResponseEntity.ok(new UserDTO(user.email(), user.name()))).orElseGet(() -> ResponseEntity.notFound().build());
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

    @GetMapping(value = "/{email}/weeklies")
    public ResponseEntity<List<WeeklyPlan>> getUserWeeklyPlans(@PathVariable String email) {
        try {
            return ResponseEntity.ok(weeklyPlanService.getUserWeeklyPlans(email));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
