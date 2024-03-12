package com.dailyfit.weekly.controller;

import com.dailyfit.weekly.WeeklyPlan;
import org.springframework.http.ResponseEntity;

public interface WeeklyPlanController {
    ResponseEntity<WeeklyPlan> getWeeklyPlanByWid(int wid);
}
