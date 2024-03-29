package com.dailyfit.routine.service;

import com.dailyfit.routine.Routine;
import com.dailyfit.routine.RoutineExerciseDTO;
import com.dailyfit.routine.dao.RoutineDao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
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
    public Routine createRoutine(String name, String email) throws SQLException {
        return routineDao.createRoutine(name, email);
    }

    @Override
    public Routine updateRoutine(int rid, String name) throws SQLException {
        Routine routine = new Routine(rid, name, "");
        routineDao.updateRoutine(routine);
        return routine;
    }

    @Override
    public void deleteRoutine(int rid) throws SQLException {
        routineDao.deleteRoutine(rid);
    }

    @Override
    public List<Routine> getUserRoutines(String email) throws SQLException {
        return routineDao.getUserRoutines(email);
    }

    @Override
    public List<RoutineExerciseDTO> getRoutineExercises(int rid) throws SQLException {
        return routineDao.getRoutineExercises(rid);
    }

    @Override
    public RoutineExerciseDTO addExerciseToRoutine(int rid, String name, int sets, int reps) throws SQLException {
        return routineDao.addExerciseToRoutine(rid, name, sets, reps);
    }
}
