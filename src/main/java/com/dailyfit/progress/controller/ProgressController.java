package com.dailyfit.progress.controller;

import com.dailyfit.progress.ExerciseDone;
import com.dailyfit.weekly.WeeklyPlan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ProgressController {
    ResponseEntity<Integer> getWeeklyFromWeek(String email,
                                                 Date week);
    ResponseEntity<List<ExerciseDone>> getDoneExercisesByUserAndDay(String email,
                                                                    Date day);
    ResponseEntity<List<ExerciseDone>> getDoneExerciseOfUser(String email,
                                                             String name);
    void setWeeklyToWeek(int wid,
                         String email,
                         Date week);
    void markExerciseAsDone(String exerciseName,
                            String email,
                            Date date,
                            int weight,
                            int rid) throws SQLException;

    ResponseEntity<List<String>> getDoneExerciseNamesOfUser(String email);
}
