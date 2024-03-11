package com.dailyfit.exercise.dao;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class ExerciseDaoImpl implements ExerciseDao {

    private final Connection connection;

    public ExerciseDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createExercise(Exercise exercise) {
        //TODO UserDaoImpl.createUser
    }

    @Override
    public Optional<Exercise> readExercise(String muscle, String type, String name, String difficulty, String material) throws SQLException {
        try (ResultSet resultSet = queryExercise(muscle, type, name, difficulty, material)) {
            String muscleGroup = resultSet.getString("muscleGroup");
            String ename = resultSet.getString("name");
            String etype  = resultSet.getString("type");
            int diff = resultSet.getInt("difficulty");
            boolean ematerial = resultSet.getBoolean("material");

            if (ename != null) {
                return Optional.of(new Exercise(ename, ematerial, muscleGroup, diff, etype));
            }
        }
        return Optional.empty();
    }


    @Override
    public void updateExercise(Exercise exercise) {
        //TODO UserDaoImpl.updateUser
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        //TODO UserDaoImpl.deleteUser
    }

    private ResultSet queryExercise(String muscleGroup, String type, String name, String difficulty, String material) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM A"));
    }
}
