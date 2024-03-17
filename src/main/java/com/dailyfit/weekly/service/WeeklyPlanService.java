package com.dailyfit.weekly.service;

import com.dailyfit.weekly.WeeklyPlan;
import com.dailyfit.weekly.WeeklyRoutineDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface WeeklyPlanService {
    Optional<WeeklyPlan> getWeeklyPlanByWid(int wid) throws SQLException;
    WeeklyPlan createWeeklyPlan(String name, String email) throws SQLException;
    WeeklyPlan updateWeeklyPlan(int wid, String name) throws SQLException;
    void deleteWeeklyPlan(int wid) throws SQLException;
    List<WeeklyPlan> getUserWeeklyPlans(String email) throws SQLException;
    List<WeeklyRoutineDTO> getWeeklyPlanRoutines(int wid) throws SQLException;
    WeeklyRoutineDTO createWeeklyRoutine(int wid, int rid, int day) throws SQLException;
}
