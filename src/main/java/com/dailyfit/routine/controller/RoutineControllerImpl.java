package com.dailyfit.routine.controller;

import com.dailyfit.routine.Routine;
import com.dailyfit.routine.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/api/routine")
public class RoutineControllerImpl implements RoutineController{
    private final RoutineService routineService;

    public RoutineControllerImpl(RoutineService routineService) {
        this.routineService = routineService;
    }


    @PostMapping(value = "/{rid}")
    public ResponseEntity<Routine> createRoutine(@PathVariable int rid,
                                                 @RequestParam String name) {
        Routine routine;

        try {
            routine = routineService.createRoutine(rid, name);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(routine);
    }

    @GetMapping(value = "/{rid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Routine> getRoutineByRid(@PathVariable int rid) {
        try {
            Optional<Routine> routine = routineService.getRoutineByRid(rid);
            return routine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
