package com.dailyfit.weekly.dao;

import com.dailyfit.weekly.WeeklyPlan;
import com.dailyfit.weekly.WeeklyRoutineDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WeeklyPlanDaoImpl implements WeeklyPlanDao {
    private final Connection connection;

    public WeeklyPlanDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        try (ResultSet resultSet = sqlCreateWeeklyPlan(weeklyPlan)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            System.err.println("WeeklyPlan exists");
            // Throw exception
            return 0;
        }
    }

    @Override
    public Optional<WeeklyPlan> readWeeklyPlan(int wid) throws SQLException {
        ResultSet resultSet = sqlQueryWeeklyPlan(wid);
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            return Optional.of(new WeeklyPlan(wid, name, email));
        }
        resultSet.close();
        return Optional.empty();
    }

    @Override
    public void updateWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        try (ResultSet resultSet = sqlQueryWeeklyPlan(weeklyPlan.getWid())) {
            if (resultSet.next()) {
                sqlUpdateWeeklyPlan(weeklyPlan);
                return;
            }
            System.err.println("WeeklyPlan not found");
            // Throw exception
        }
    }

    @Override
    public void deleteWeeklyPlan(int wid) throws SQLException {
        try (ResultSet resultSet = sqlQueryWeeklyPlan(wid)) {
            if (resultSet.next()) {
                sqlDeleteWeeklyPlan(wid);
                return;
            }
            System.err.println("WeeklyPlan not found");
            // Throw exception
        }
    }

    @Override
    public List<WeeklyRoutineDTO> getWeeklyPlanRoutines(int wid) throws SQLException {
        try (ResultSet resultSet = sqlQueryWeeklyPlanRoutines(wid)) {
            List<WeeklyRoutineDTO> weeklyRoutineDTOList = new ArrayList<>();
            while (resultSet.next()) {
                int rid = resultSet.getInt("rid");
                int day = resultSet.getInt("day");
                weeklyRoutineDTOList.add(new WeeklyRoutineDTO(rid, day));
            }
            return weeklyRoutineDTOList;
        }
    }

    @Override
    public WeeklyRoutineDTO createWeeklyRoutine(int wid, int rid, int day) throws SQLException {
        sqlCreateWeeklyRoutine(wid, rid, day);
        return new WeeklyRoutineDTO(rid, day);
    }

    @Override
    public List<WeeklyPlan> getWeeklyPlansByEmail(String email) throws SQLException {
        try (ResultSet resultSet = sqlQueryWeeklyPlanByEmail(email)) {
            List<WeeklyPlan> weeklyPlanList = new ArrayList<>();
            while (resultSet.next()) {
                int wid = resultSet.getInt("wid");
                String name = resultSet.getString("name");
                weeklyPlanList.add(new WeeklyPlan(wid, name, email));
            }
            return weeklyPlanList;
        }
    }

    private void sqlCreateWeeklyRoutine(int wid, int rid, int day) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO routine_weeklyPlan (wid, rid, day) VALUES ('%s', '%s', '%s')", wid, rid, day));
    }

    public ResultSet sqlQueryWeeklyPlanRoutines(int wid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM routine_weeklyPlan WHERE wid = '%s'", wid));
    }

    private ResultSet sqlQueryWeeklyPlan(int wid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM weeklyPlan WHERE wid = '%s'", wid));
    }

    private ResultSet sqlQueryWeeklyPlanByEmail(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM weeklyPlan WHERE email = '%s'", email));
    }

    private ResultSet sqlCreateWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        return connection.createStatement().executeQuery(String.format("INSERT INTO weeklyPlan (name, email) VALUES ('%s', '%s') RETURNING last_insert_rowid()", weeklyPlan.name(), weeklyPlan.email()));
    }

    private void sqlDeleteWeeklyPlan(int wid) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM weeklyPlan WHERE wid = '%s'", wid));
    }

    private void sqlUpdateWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE weeklyPlan SET name = '%s' WHERE wid = '%s'", weeklyPlan.name(),weeklyPlan.getWid()));
    }
}
