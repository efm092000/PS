package com.dailyfit.progress.dao;

import com.dailyfit.progress.ExerciseDone;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ProgressDao {
    Integer getWeeklyFromWeek(String email, Date week);

    List<ExerciseDone> getDoneExercisesByUserAndDay(String email, Date day);

    List<ExerciseDone> getDoneExerciseOfUser(String email, String name);

    Set<String> getDoneExerciseNamesOfUser(String email);

    void setWeeklyToWeek(int wid, String email, Date week);

    void markExerciseAsDone(ExerciseDone exerciseDone) throws SQLException;

    List<ExerciseDone> getDoneExercisesByYearAndMonth(String email, String exerciseName, int year, int month) throws SQLException, ParseException;
}
