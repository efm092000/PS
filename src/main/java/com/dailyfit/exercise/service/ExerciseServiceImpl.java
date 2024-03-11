package com.dailyfit.exercise.service;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.exercise.dao.ExerciseDao;
import com.dailyfit.user.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseDao exerciseDao;

    public ExerciseServiceImpl(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @Override
    public Optional<Exercise> getExerciseByQuery(String muscleGroup, String type, String name, String difficulty, String material) throws SQLException {
        return exerciseDao.readExercise(muscleGroup, type, name, difficulty, material);
    }

    @Override
    public User createExercise(Exercise exercise) {
        return null;
    }

    @Override
    public User updateExercise(Exercise exercise) {
        return null;
    }

    @Override
    public void deleteExercise(String name) {

    }
}
