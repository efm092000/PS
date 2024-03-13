package com.dailyfit.exercise.service;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.exercise.dao.ExerciseDao;
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
    public Optional<String> getExerciseByQuery(String name) throws SQLException {
        return exerciseDao.readExercise(name);
    }

    @Override
    public Optional<String> getExercisesByQuery(String muscleGroup, String type, String difficulty, String material) throws SQLException {
        return exerciseDao.readExercises(muscleGroup, type, difficulty, material);
    }

    @Override
    public void createExercise(Exercise exercise) throws SQLException {
         exerciseDao.createExercise(exercise);
    }

    @Override
    public void updateExercise(Exercise exercise) throws SQLException {
        exerciseDao.updateExercise(exercise);
    }

    @Override
    public void deleteExercise(String name) throws SQLException {
        exerciseDao.deleteExercise(name);
    }
}
