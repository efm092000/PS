package com.dailyfit.progress.dao;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.progress.ExerciseDone;
import com.dailyfit.weekly.WeeklyPlan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        try (ResultSet resultSet = queryDoneExercises(email, day, exerciseName)) {
            List<ExerciseDone> exercises = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                if (name != null) {
                    ExerciseDone exercise = new ExerciseDone(
                            resultSet.getString("exercise"),
                            resultSet.getDate("day"),
                            resultSet.getString("email"),
                            resultSet.getInt("rid"),
                            resultSet.getInt("weight"));
                    exercises.add(exercise);
                }
            }
            return exercises;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ExerciseDone> getDoneExerciseOfUser(String email, String name) {
        return getDoneExercises(email, null, name);    }

    @Override
    public List<String> getDoneExerciseNamesOfUser(String email) {
        try (ResultSet resultSet = queryDoneExercises(email, null, null)) {
            List<String> exercises = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                if (name != null) {
                    exercises.add(resultSet.getString("exercise"));
                }
            }
            return exercises;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void setWeeklyToWeek(int wid, String email, Date week) {
        try {
            connection.createStatement().execute(
                    String.format(
                            "INSERT INTO weeklyToWeek (wid,week,email) VALUES (%d,'%s','%s')",
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

        String query = String.format("INSERT INTO exercise_done (exercise, email, day, rid, weight) VALUES ('%s', '%s', '%s', %d, %d)",
                exerciseDone.exercise(),
                exerciseDone.email(),
                formatDay(exerciseDone.day()),
                exerciseDone.rid(),
                exerciseDone.weight());

        connection.createStatement().execute(query);

    }


    private ResultSet queryDoneExercises(String email, Date day, String name) throws SQLException {
        String query = String.format("SELECT * FROM exercise_done WHERE email='%s'", email);
        if (day != null){
            query += String.format(" AND day='%s'", formatDay(day));
        }
        if (name != null) query += String.format(" AND name='%s'", name);
        return connection.createStatement().executeQuery(query);
    }
    private ResultSet getWeeklyByWeek(String email, Date week) throws SQLException {
        String query = String.format("SELECT * FROM weeklyToWeek WHERE email='%s'", email);
        query += String.format(" AND week='%s'", formatDay(week));
        return connection.createStatement().executeQuery(query);
    }
}
