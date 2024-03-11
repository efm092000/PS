package com.dailyfit.routine.service;

import com.dailyfit.routine.Routine;
import com.dailyfit.routine.dao.RoutineDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class RoutineServiceImpl implements RoutineService{
    private final RoutineDao routineDao;

    public RoutineServiceImpl(RoutineDao routineDao) {
        this.routineDao = routineDao;
    }

    @Override
    public Optional<Routine> getRoutineByRid(int rid) throws SQLException {
        return routineDao.readRoutine(rid);
    }

    @Override
    public Routine createRoutine(Routine routine) {
        return null;
    }

    @Override
    public Routine updateRoutine(Routine routine) {
        return null;
    }

    @Override
    public void deleteRoutine(int rid) {

    }
}