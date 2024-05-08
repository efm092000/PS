package com.dailyfit.progress.service;

import com.dailyfit.progress.ExerciseDone;
import com.dailyfit.progress.dao.ProgressDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public Set<String> getDoneExerciseNamesOfUser(String email) {
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

    @Override
    public List<ExerciseDone> getDoneExercisesByYearAndMonth(String email, String exerciseName, int year, int month) throws SQLException, ParseException {
        return progressDao.getDoneExercisesByYearAndMonth(email, exerciseName, year, month);
    }
}
