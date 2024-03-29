package com.dailyfit.weekly.controller;

import com.dailyfit.weekly.WeeklyPlan;
import com.dailyfit.weekly.WeeklyRoutineDTO;
import com.dailyfit.weekly.service.WeeklyPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weekly")

public class WeeklyPlanControllerImpl implements WeeklyPlanController {
    private final WeeklyPlanService weeklyPlanService;

    public WeeklyPlanControllerImpl(WeeklyPlanService weeklyPlanService) {
        this.weeklyPlanService = weeklyPlanService;
    }

    @PostMapping(value = "/{email}")
    public ResponseEntity<WeeklyPlan> createWeeklyPlan(@PathVariable String email,
                                           @RequestParam String name) {
        WeeklyPlan weeklyPlan;
        try {
            weeklyPlan = weeklyPlanService.createWeeklyPlan(name, email);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(weeklyPlan);
    }

    @GetMapping(value = "/{wid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeeklyPlan> getWeeklyPlanByWid(@PathVariable int wid) {
        try {
            Optional<WeeklyPlan> optionalWeeklyPlan = weeklyPlanService.getWeeklyPlanByWid(wid);
            return optionalWeeklyPlan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = "/{wid}")
    public ResponseEntity<WeeklyPlan> updateUser(@PathVariable int wid,
                                           @RequestParam(required = false) String name) {
        WeeklyPlan weeklyPlan;
        try {
            weeklyPlan = weeklyPlanService.updateWeeklyPlan(wid, name);
            Optional<WeeklyPlan> optionalWeeklyPlan = weeklyPlanService.getWeeklyPlanByWid(wid);
            if (optionalWeeklyPlan.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(weeklyPlan);
    }


    @DeleteMapping(value = "/{wid}")
    public ResponseEntity<String> deleteWeeklyPlan(@PathVariable int wid) {
        try {
            weeklyPlanService.deleteWeeklyPlan(wid);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok("WeeklyPlan was deleted successfully");
    }

    @GetMapping(value = "/{wid}/routines")
    public ResponseEntity<List<WeeklyRoutineDTO>> getRoutinesByWid(@PathVariable int wid) {
        try {
            return ResponseEntity.ok(weeklyPlanService.getWeeklyPlanRoutines(wid));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/{wid}/routine")
    public ResponseEntity<WeeklyRoutineDTO> createWeeklyRoutine(@PathVariable int wid,
                                                               @RequestParam int rid,
                                                               @RequestParam int day) {
        WeeklyRoutineDTO weeklyRoutineDTO;
        try {
            weeklyRoutineDTO = weeklyPlanService.createWeeklyRoutine(wid, rid, day);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(weeklyRoutineDTO);
    }
}
