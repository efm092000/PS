package com.dailyfit.exercise;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

@JsonAutoDetect
public class Exercise {

    private String name;
    private boolean material;
    private String muscleGroup;
    private int difficulty;
    private String type;

    public Exercise(String name, boolean material, String muscleGroup, int difficulty, String type){
        this.name = name;
        this.material = material;
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.type = type;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }
    @JsonGetter("material")
    public boolean getMaterial() {
        return material;
    }
    @JsonGetter("muscleGroup")
    public String getMuscleGroup() {
        return muscleGroup;
    }

    @JsonGetter("difficulty")
    public int getDifficulty() {
        return difficulty;
    }

    @JsonGetter("type")
    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaterial(boolean material) {
        this.material = material;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(name, exercise.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name,material,muscleGroup,difficulty,type);
    }
    @Override
    public String toString() {
        return "Excercise:" +
                "name= '" + name + '\'' +
                ", material= " + material +
                ", muscle group= " + muscleGroup +
                ", difficulty= " + difficulty +
                ", type= " + type + "'";
    }
}