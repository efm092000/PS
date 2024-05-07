package com.dailyfit.progress.dao;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.progress.ExerciseDone;
import com.dailyfit.weekly.WeeklyPlan;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
public interface ProgressDao {
    Integer getWeeklyFromWeek(String email, Date week);

    List<ExerciseDone> getDoneExercisesByUserAndDay(String email, Date day);

    List<ExerciseDone> getDoneExerciseOfUser(String email, String name);

    List<String> getDoneExerciseNamesOfUser(String email);

    void setWeeklyToWeek(int wid, String email, Date week);

    void markExerciseAsDone(ExerciseDone exerciseDone) throws SQLException;
}
