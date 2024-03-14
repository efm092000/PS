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
    public int createWeeklyPlan(WeeklyPlan weeklyPlan) throws SQLException {
        try (ResultSet resultSet = sqlQueryWeeklyPlan(weeklyPlan.getWid())) {
            if (!resultSet.next()) {
                return sqlCreateWeeklyPlan(weeklyPlan).getInt(1);
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

    private ResultSet sqlQueryWeeklyPlan(int wid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM weeklyPlan WHERE wid = '%s'", wid));
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
