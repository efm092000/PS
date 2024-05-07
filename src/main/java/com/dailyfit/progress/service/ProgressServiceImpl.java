package com.dailyfit.progress.service;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.progress.ExerciseDone;
import com.dailyfit.progress.dao.ProgressDao;
import com.dailyfit.weekly.WeeklyPlan;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressDao progressDao;

    public ProgressServiceImpl(ProgressDao progressDao) {
        this.progressDao = progressDao;
    }

    @Override
    public List<ExerciseDone> getDoneExercisesByUserAndDay(String email, Date day) {
        return progressDao.getDoneExercisesByUserAndDay(email, day);
    }

    @Override
    public Integer getWeeklyFromWeek(String email, Date week) {
        return progressDao.getWeeklyFromWeek(email, week);
    }

    @Override
    public List<ExerciseDone> getDoneExerciseOfUser(String email, String name) {
        return progressDao.getDoneExerciseOfUser(email,name);
    }

    @Override
    public List<String> getDoneExerciseNamesOfUser(String email) {
        return progressDao.getDoneExerciseNamesOfUser(email);
    }

    @Override
    public void setWeeklyToWeek(int wid, String email, Date week) {
        progressDao.setWeeklyToWeek(wid,email,week);
    }

    @Override
    public void markExerciseAsDone(ExerciseDone exerciseDone) throws SQLException {
        progressDao.markExerciseAsDone(exerciseDone);
    }
}
