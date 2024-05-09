package com.dailyfit.progress.service;

import com.dailyfit.progress.ExerciseDone;
import com.dailyfit.progress.ProgressRecommendationDTO;
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
        if (month == 0) {
            return progressDao.getDoneExercisesByYear(email, exerciseName, year);
        }
        return progressDao.getDoneExercisesByYearAndMonth(email, exerciseName, year, month);
    }

    @Override
    public ProgressRecommendationDTO getRecommendation(int reps, float weight, String goal) {
        float oneRepMax = calculateORM(reps, weight);
        return switch (goal) {
            case "strength" -> new ProgressRecommendationDTO(oneRepMax, 2, 1, 5, oneRepMax * 0.9f);
            case "hypertrophy" -> new ProgressRecommendationDTO(oneRepMax, 2, 6, 10, oneRepMax * 0.75f);
            case "endurance" -> new ProgressRecommendationDTO(oneRepMax, 3, 15, 30, oneRepMax * 0.5f);
            default -> new ProgressRecommendationDTO(oneRepMax, 3, 8, 15, oneRepMax * 0.65f);
        };
    }

    private float calculateORM(int reps, float weight) {
        return weight * (1 + reps / 30f);
    }
}
