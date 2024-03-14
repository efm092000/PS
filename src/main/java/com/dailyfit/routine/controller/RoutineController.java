package com.dailyfit.routine.controller;

import com.dailyfit.routine.Routine;
import org.springframework.http.ResponseEntity;

public interface RoutineController {
    ResponseEntity<Routine> getRoutineByRid(int rid);
}
