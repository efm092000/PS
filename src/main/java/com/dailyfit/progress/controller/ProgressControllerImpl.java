package com.dailyfit.progress.controller;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.progress.ExerciseDone;
import com.dailyfit.progress.service.ProgressService;
import com.dailyfit.weekly.WeeklyPlan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
public class ProgressControllerImpl implements ProgressController {

    private final ProgressService progressService;

    public ProgressControllerImpl(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping(value = "/api/progress/weekly/get")
    public ResponseEntity<Integer> getWeeklyFromWeek(
            @RequestParam String email,
            @RequestParam Date week) {
        return ResponseEntity.ok(progressService.getWeeklyFromWeek(email, week));
    }

    @GetMapping(value = "/api/progress/get_exercises_of_day")
    public ResponseEntity<List<ExerciseDone>> getDoneExercisesByUserAndDay(
            @RequestParam String email,
            @RequestParam Date day) {
        return ResponseEntity.ok(progressService.getDoneExercisesByUserAndDay(email, day));
    }

    @GetMapping(value = "/api/progress/get_done_exercises")
    public ResponseEntity<List<ExerciseDone>> getDoneExerciseOfUser(
            @RequestParam String email,
            @RequestParam String name) {
        return ResponseEntity.ok(progressService.getDoneExerciseOfUser(email, name));
    }

    @GetMapping(value = "/api/progress/get_done_exercises_names")
    public ResponseEntity<Set<String>> getDoneExerciseNamesOfUser(
            @RequestParam String email) {
        return ResponseEntity.ok(progressService.getDoneExerciseNamesOfUser(email));
    }

    @PostMapping(value = "/api/progress/weekly/set")
    public void setWeeklyToWeek(
            @RequestParam int wid,
            @RequestParam String email,
            @RequestParam Date week) {
        progressService.setWeeklyToWeek(wid, email, week);
    }

    @PostMapping(value = "/api/progress/exercise/mark")
    public void markExerciseAsDone(
            @RequestParam String exerciseName,
            @RequestParam String email,
            @RequestParam Date date,
            @RequestParam int weight,
            @RequestParam int sets,
            @RequestParam int reps,
            @RequestParam int rid) throws SQLException {
        progressService.markExerciseAsDone(
                new ExerciseDone(exerciseName,date,email,rid,weight,sets,reps)
        );
    }

    @GetMapping(value = "/api/progress/done_exercises")
    public ResponseEntity<List<ExerciseDone>> getDoneExercisesByYearAndMonth(
            @RequestParam String email,
            @RequestParam String exerciseName,
            @RequestParam int year,
            @RequestParam int month) {
        try {
            return ResponseEntity.ok(progressService.getDoneExercisesByYearAndMonth(email, exerciseName, year, month));
        } catch (ParseException | SQLException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
