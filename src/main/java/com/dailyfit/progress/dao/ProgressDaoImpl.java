package com.dailyfit.progress.dao;

import com.dailyfit.progress.ExerciseDone;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ProgressDaoImpl implements ProgressDao {

    private final Connection connection;

    public ProgressDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getWeeklyFromWeek(String email, Date week) {
        try (ResultSet resultSet = getWeeklyByWeek(email,week)) {
            if (resultSet.next()) {
                return resultSet.getInt("wid");
            }
            return 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public List<ExerciseDone> getDoneExercisesByUserAndDay(String email, Date day) {
        return getDoneExercises(email, day, null);
    }

    private List<ExerciseDone> getDoneExercises(String email, Date day, String exerciseName) {
        List<ExerciseDone> exercises = new ArrayList<>();
        try (ResultSet resultSet = queryDoneExercises(email, day, exerciseName)) {
            while (resultSet.next()) {
                String name = resultSet.getString("exercise");
                if (name != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    ExerciseDone exercise = new ExerciseDone(
                            resultSet.getString("exercise"),
                            sdf.parse(resultSet.getString("day")),
                            resultSet.getString("email"),
                            resultSet.getInt("rid"),
                            resultSet.getInt("weight"),
                            resultSet.getInt("sets"),
                            resultSet.getInt("reps"));
                    exercises.add(exercise);
                }
            }
            return exercises;
        } catch (Exception e) {
            return exercises;
        }
    }

    @Override
    public List<ExerciseDone> getDoneExerciseOfUser(String email, String name) {
        return getDoneExercises(email, null, name);    }

    @Override
    public Set<String> getDoneExerciseNamesOfUser(String email) {
        try (ResultSet resultSet = queryDoneExercises(email, null, null)) {
            Set<String> exercises = new HashSet<>();
            while (resultSet.next()) {
                exercises.add(resultSet.getString("exercise"));
            }
            return exercises;
        } catch (SQLException e) {
            return new HashSet<>();
        }
    }

    @Override
    public void setWeeklyToWeek(int wid, String email, Date week) {
        try {
            connection.createStatement().execute(
                    String.format(
                            "INSERT OR REPLACE INTO weeklyToWeek (wid,week,email) VALUES (%d,'%s','%s')",
                    wid,
                    formatDay(week),
                    email));
        } catch (SQLException e) {
            return;
        }
    }

    private static String formatDay(Date week) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(week);
        return formattedDate;
    }

    @Override
    public void markExerciseAsDone(ExerciseDone exerciseDone) throws SQLException {

        String query = String.format("INSERT INTO exercise_done (exercise, email, day, rid, weight, sets, reps) VALUES ('%s', '%s', '%s', %d, %d, %d, %d)",
                exerciseDone.exercise(),
                exerciseDone.email(),
                formatDay(exerciseDone.day()),
                exerciseDone.rid(),
                exerciseDone.weight(),
                exerciseDone.sets(),
                exerciseDone.reps());

        connection.createStatement().execute(query);

    }

    @Override
    public List<ExerciseDone> getDoneExercisesByYearAndMonth(String email, String exerciseName, int year, int month) throws SQLException, ParseException {
        try (ResultSet resultSet = queryExerciseByYearAndMonth(email, exerciseName, year, month)) {
            return collectExercisesDone(resultSet);
        }
    }

    @Override
    public List<ExerciseDone> getDoneExercisesByYear(String email, String exerciseName, int year) {
        try (ResultSet resultSet = queryExerciseByYear(email, exerciseName, year)){
            return collectExercisesDone(resultSet);
        } catch (SQLException | ParseException e) {
            return new ArrayList<>();
        }
    }

    private List<ExerciseDone> collectExercisesDone(ResultSet resultSet) throws SQLException, ParseException {
        List<ExerciseDone> exercises = new ArrayList<>();
        while (resultSet.next()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ExerciseDone exercise = new ExerciseDone(
                    resultSet.getString("exercise"),
                    sdf.parse(resultSet.getString("day")),
                    resultSet.getString("email"),
                    resultSet.getInt("rid"),
                    resultSet.getInt("weight"),
                    resultSet.getInt("sets"),
                    resultSet.getInt("reps"));
            exercises.add(exercise);
        }
        return exercises;
    }

    private ResultSet queryDoneExercises(String email, Date day, String name) throws SQLException {
        String query = String.format("SELECT DISTINCT * FROM exercise_done WHERE email='%s'", email);
        if (day != null){
            query += String.format(" AND day='%s'", formatDay(day));
        }
        if (name != null) query += String.format(" AND exercise='%s'", name);
        return connection.createStatement().executeQuery(query);
    }

    private ResultSet getWeeklyByWeek(String email, Date week) throws SQLException {
        String query = String.format("SELECT * FROM weeklyToWeek WHERE email='%s'", email);
        query += String.format(" AND week='%s'", formatDay(week));
        return connection.createStatement().executeQuery(query);
    }

    private ResultSet queryExerciseByYear(String email, String exerciseName, int year) throws SQLException {
        String query = String.format("SELECT * FROM exercise_done WHERE email='%s' AND exercise='%s' AND strftime('%%Y', day)='%s'", email, exerciseName, year);
        return connection.createStatement().executeQuery(query);
    }

    private ResultSet queryExerciseByYearAndMonth(String email, String exerciseName, int year, int month) throws SQLException {
        String monthString = String.format("%d-%02d", year, month);
        String query = String.format("SELECT * FROM exercise_done WHERE email='%s' AND exercise='%s' AND strftime('%%Y-%%m', day)='%s'", email, exerciseName, monthString);
        return connection.createStatement().executeQuery(query);
    }
}
