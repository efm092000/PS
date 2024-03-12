package com.dailyfit.exercise.dao;

import com.dailyfit.exercise.Exercise;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExerciseDaoImpl implements ExerciseDao {

    private final Connection connection;

    public ExerciseDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createExercise(Exercise exercise) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO A (name, muscleGroup, type, difficulty, material) VALUES ('%s','%s','%s','%s','%s')",
                exercise.getName(),exercise.getMuscleGroup(), exercise.getType(), exercise.getDifficulty(), exercise.getMaterial()));
    }

    @Override
    public Optional<String> readExercise(String name) throws SQLException {
        try (ResultSet resultSet = queryExercise(name)) {
            String muscleGroup = resultSet.getString("muscleGroup");
            String ename = resultSet.getString("name");
            String etype  = resultSet.getString("type");
            String diff = resultSet.getString("difficulty");
            String ematerial = resultSet.getString("material");

            if (ename != null) {
                return Optional.of(new ObjectMapper().writeValueAsString(new Exercise(ename, ematerial, muscleGroup, diff, etype)));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public void updateExercise(Exercise exercise) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE A SET muscleGroup='%s', type='%s', difficulty='%s', material='%s' WHERE name='%s'",
                exercise.getMuscleGroup(), exercise.getType(), exercise.getDifficulty(), exercise.getMaterial(),exercise.getName()));
    }

    @Override
    public void deleteExercise(String name) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM A where name='%s'", name));
    }

    private ResultSet queryExercise(String name) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM A"));
    }

    public Optional<String> readExercises(String muscle, String type, String difficulty, String material) throws SQLException {
        try (ResultSet resultSet = queryExercises(muscle, type, difficulty, material)) {
            List<String> exercises = new ArrayList<>();
            while (resultSet.next()) {
                String muscleGroup = resultSet.getString("muscleGroup");
                String ename = resultSet.getString("name");
                String etype = resultSet.getString("type");
                String diff = resultSet.getString("difficulty");
                String ematerial = resultSet.getString("material");

                if (ename != null) {
                    Exercise exercise = new Exercise(ename, ematerial, muscleGroup, diff, etype);
                    exercises.add(new ObjectMapper().writeValueAsString(exercise));
                }
            }

            // Filtra ejercicios nulos y luego los une con ","
            String jsonExercises = exercises.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(","));

            return Optional.of("[" + jsonExercises + "]");
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo apropiado del error
            return Optional.empty();
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Manejo apropiado del error
            return Optional.empty();
        }
    }
    private ResultSet queryExercises(String muscle, String type, String difficulty, String material) throws SQLException {
        String query = "SELECT * FROM A WHERE TRUE";
        if (!muscle.equals("all")) query += String.format(" AND muscleGroup='%s'", muscle);
        if (!type.equals("all")) query += String.format(" AND type='%s'", type);
        if (!difficulty.equals("all")) query += String.format(" AND difficulty='%s'", difficulty);
        if (!material.equals("all")) query += String.format(" AND material='%s'", material);

        return connection.createStatement().executeQuery(query);
    }
}
