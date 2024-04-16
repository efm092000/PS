package com.dailyfit.exercise.dao;

import com.dailyfit.exercise.Exercise;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class ExerciseDaoImpl implements ExerciseDao {

    private final Connection connection;

    public ExerciseDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createExercise(Exercise exercise) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO exercise (name, muscleGroup, type, difficulty, material, gif, description) VALUES ('%s','%s','%s','%d','%b','%s','%s')",
                exercise.getName(),
                exercise.getMuscleGroup(),
                exercise.getType(),
                exercise.getDifficulty(),
                exercise.getMaterial(),
                exercise.getGif(),
                exercise.getDescription()));
    }

    @Override
    public List<Exercise> readExercise(String name) throws SQLException {
        try (ResultSet resultSet = queryExercise(name)) {
            if (!resultSet.next()) return new ArrayList<>();
            String muscleGroup = resultSet.getString("muscleGroup");
            String type  = resultSet.getString("type");
            String gif  = resultSet.getString("gif");
            String description  = resultSet.getString("description");
            int difficulty = resultSet.getInt("difficulty");
            boolean material = resultSet.getBoolean("material");
            return List.of(new Exercise(name, material, muscleGroup, difficulty, type, gif, description));
        }
    }


    @Override
    public void updateExercise(Exercise exercise) throws SQLException {
        String query = "UPDATE exercise SET ";
        if (exercise.getMuscleGroup() != null) query += String.format("muscleGroup='%s', ",exercise.getMuscleGroup());
        if (exercise.getMaterial() != null) query += String.format("material='%b', ",exercise.getMaterial());
        if (exercise.getType() != null) query += String.format("type='%s', ",exercise.getType());
        if (exercise.getGif() != null) query += String.format("gif='%s', ",exercise.getGif());
        if (exercise.getDescription() != null) query += String.format("description='%s', ",exercise.getDescription());
        if (exercise.getDifficulty() != null) query += String.format("difficulty='%d', ",exercise.getDifficulty());
        query = query.substring(0,query.length()-2);
        query += String.format("WHERE name='%s'", exercise.getName());
        connection.createStatement().execute(query);
    }

    @Override
    public void deleteExercise(String name) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM exercise where name='%s'", name));
    }

    private ResultSet queryExercise(String name) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM exercise WHERE name='%s'", name));
    }

    public List<Exercise> readExercises(String muscle, String type, Integer difficulty, Boolean material) throws SQLException {
        try (ResultSet resultSet = queryExercises(muscle, type, difficulty, material)) {
            List<Exercise> exercises = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                if (name != null) {
                    Exercise exercise = new Exercise(name,
                            resultSet.getBoolean("material"),
                            resultSet.getString("muscleGroup"),
                            resultSet.getInt("difficulty"),
                            resultSet.getString("type"),
                            resultSet.getString("gif"),
                            resultSet.getString("description"));
                    exercises.add(exercise);
                }
            }
            return exercises;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    private ResultSet queryExercises(String muscle, String type, Integer difficulty, Boolean material) throws SQLException {
        String query = "SELECT * FROM exercise WHERE TRUE";
        if (muscle != null) query += String.format(" AND muscleGroup='%s'", muscle);
        if (type != null) query += String.format(" AND type='%s'", type);
        if (difficulty != null) query += String.format(" AND difficulty='%d'", difficulty);
        if (material != null) query += String.format(" AND material='%b'", material);

        return connection.createStatement().executeQuery(query);
    }
}
