package com.dailyfit.weekly.dao;

import com.dailyfit.weekly.WeeklyPlan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class WeeklyPlanDaoImpl implements WeeklyPlanDao {
    private final Connection connection;

    public WeeklyPlanDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        try (ResultSet resultSet = sqlQueryWeeklyPlan(weeklyPlan.getWid())) {
            if (!resultSet.next()) {
                sqlCreateWeeklyPlan(weeklyPlan);
                return;
            }
            System.err.println("WeeklyPlan exists");
            // Throw exception
        }
    }

    @Override
    public Optional<WeeklyPlan> readWeeklyPlan(int wid) throws SQLException {
        ResultSet resultSet = sqlQueryWeeklyPlan(wid);
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            return Optional.of(new WeeklyPlan(wid,name));
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
            }
            System.err.println("WeeklyPlan not found");
            // Throw exception
        }
    }

    private ResultSet sqlQueryWeeklyPlan(int wid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT name FROM weeklyplans WHERE wid = '%s'", wid));
    }

    private void sqlCreateWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO weeklyplans (wid, name) VALUES ('%s', '%s')", weeklyPlan.getWid(), weeklyPlan.name()));
    }

    private void sqlDeleteWeeklyPlan(int wid) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM weeklyplans WHERE wid = '%s'", wid));
    }

    private void sqlUpdateWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE weeklyplans SET name = '%s' WHERE wid = '%s'", weeklyPlan.name(),weeklyPlan.getWid()));
    }
}
