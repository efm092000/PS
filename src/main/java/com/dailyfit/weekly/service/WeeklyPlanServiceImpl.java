package com.dailyfit.weekly.service;

import com.dailyfit.weekly.WeeklyPlan;
import com.dailyfit.weekly.dao.WeeklyPlanDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class WeeklyPlanServiceImpl implements WeeklyPlanService {
    private final WeeklyPlanDao weeklyPlanDao;

    public WeeklyPlanServiceImpl(WeeklyPlanDao weeklyPlanDao) {
        this.weeklyPlanDao = weeklyPlanDao;
    }

    @Override
    public Optional<WeeklyPlan> getWeeklyPlanByWid(int wid) throws SQLException {
        return weeklyPlanDao.readWeeklyPlan(wid);
    }

    @Override
    public WeeklyPlan createWeeklyPlan(String name, String email) throws SQLException {
        /* TODO Check for existing user
         * 1. Check if user exists
         * 2. Check if password or name is null
         */
        WeeklyPlan weeklyPlan = new WeeklyPlan(name, email);
        int wid = weeklyPlanDao.createWeeklyPlan(weeklyPlan);
        weeklyPlan.wid(wid);
        return weeklyPlan;
    }

    @Override
    public WeeklyPlan updateWeeklyPlan(int wid, String name) throws SQLException {
        /* TODO UserServiceImpl.updateUser
         * 1. Check if user exists
         * 2. Check if password or name is null
         */
        WeeklyPlan weeklyPlan = new WeeklyPlan(wid, name, "");
        weeklyPlanDao.updateWeeklyPlan(weeklyPlan);
        return getWeeklyPlanByWid(wid).orElseThrow();
    }

    @Override
    public void deleteWeeklyPlan(int wid) throws SQLException {
        /* TODO UserServiceImpl.deleteUser
         * 1. Check if user exists
         */
        weeklyPlanDao.deleteWeeklyPlan(wid);
    }
}

