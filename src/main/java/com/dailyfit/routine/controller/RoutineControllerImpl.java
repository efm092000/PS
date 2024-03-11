package com.dailyfit.routine.controller;

import com.dailyfit.routine.Routine;
import com.dailyfit.routine.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class RoutineControllerImpl implements RoutineController{
    private final RoutineService routineService;

    public RoutineControllerImpl(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping(value = "/api/routine/{rid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Routine> getRoutineByRid(@PathVariable int rid) {
        try {
            Optional<Routine> routine = routineService.getRoutineByRid(rid);
            return routine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
