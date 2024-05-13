package com.dailyfit.routine.service;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.exercise.dao.ExerciseDao;
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
    private final ExerciseDao exerciseDao;

    public RoutineServiceImpl(RoutineDao routineDao, ExerciseDao exerciseDao) {

        this.routineDao = routineDao;
        this.exerciseDao = exerciseDao;
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

    @Override
    public void deleteExerciseFromRoutine(int rid, String name, int sets, int reps) throws SQLException {
        routineDao.deleteExerciseFromRoutine(rid, name, sets, reps);
    }

    @Override
    public Routine generateRoutine(String name, String email, String muscleGroup, int maxExercises) throws SQLException{
        Routine routine = routineDao.createRoutine(name, email);
        List<Exercise> exercises = exerciseDao.readExercises(muscleGroup, null, null, null);
        int i=0;
        for (Exercise exercise : exercises) {
            if (i == maxExercises){break;}
            i++;
            routineDao.addExerciseToRoutine(routine.rid(), exercise.getName(), 0, 0);
        }
        return routine;
    }
}
