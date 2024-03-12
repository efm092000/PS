package com.dailyfit.weekly.dao;

import com.dailyfit.weekly.WeeklyPlan;

import java.sql.SQLException;
import java.util.Optional;

public interface WeeklyPlanDao {
    void createWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException;
    Optional<WeeklyPlan> readWeeklyPlan(int wid) throws SQLException;
    void updateWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException;
    void deleteWeeklyPlan(int wid) throws SQLException;
}
