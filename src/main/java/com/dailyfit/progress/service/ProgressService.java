package com.dailyfit.progress.service;

import com.dailyfit.progress.ExerciseDone;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ProgressService {
    List<ExerciseDone> getDoneExercisesByUserAndDay(String email, Date day);

    Integer getWeeklyFromWeek(String email, Date week);

    List<ExerciseDone> getDoneExerciseOfUser(String email, String name);

    Set<String> getDoneExerciseNamesOfUser(String email);

    void setWeeklyToWeek(int wid, String email, Date week);

    void markExerciseAsDone(ExerciseDone exerciseDone) throws SQLException;

    List<ExerciseDone> getDoneExercisesByYearAndMonth(String email, String exerciseName, int year, int month) throws SQLException, ParseException;
}
