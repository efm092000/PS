package com.dailyfit.weekly.dao;

import com.dailyfit.weekly.WeeklyPlan;
import com.dailyfit.weekly.WeeklyRoutineDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface WeeklyPlanDao {
    int createWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException;
    Optional<WeeklyPlan> readWeeklyPlan(int wid) throws SQLException;
    void updateWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException;
    void deleteWeeklyPlan(int wid) throws SQLException;
    List<WeeklyPlan> getWeeklyPlansByEmail(String email) throws SQLException;
    List<WeeklyRoutineDTO> getWeeklyPlanRoutines(int wid) throws SQLException;
    WeeklyRoutineDTO createWeeklyRoutine(int wid, int rid, int day) throws SQLException;
}
