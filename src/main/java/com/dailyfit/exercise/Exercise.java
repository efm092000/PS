package com.dailyfit.exercise;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

@JsonAutoDetect
public class Exercise {

    private String name;
    private Boolean material;
    private String muscleGroup;
    private Integer difficulty;
    private String type;

    private String image;
    private String gif;
    private String description;

    public Exercise(String name, Boolean material, String muscleGroup, Integer difficulty, String type, String gif, String description){
        this.name = name;
        this.material = material;
        this.muscleGroup = muscleGroup;
        this.difficulty = difficulty;
        this.type = type;
        this.gif = gif;
        this.description = description;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }
    @JsonGetter("material")
    public Boolean getMaterial() {
        return material;
    }
    @JsonGetter("muscleGroup")
    public String getMuscleGroup() {
        return muscleGroup;
    }

    @JsonGetter("difficulty")
    public Integer getDifficulty() {
        return difficulty;
    }

    @JsonGetter("type")
    public String getType() {
        return type;
    }
    @JsonGetter("gif")
    public String getGif() {
        return gif;
    }
    @JsonGetter("description")
    public String getDescription() {
        return description;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setMaterial(Boolean material) {
        this.material = material;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setGif(String gif) {
        this.gif = gif;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return Objects.hash(name,material,muscleGroup,difficulty,type,gif,description);
    }
    @Override
    public String toString() {
        return "Excercise:" +
                "name= '" + name + '\'' +
                ", material= " + material +
                ", muscle group= " + muscleGroup +
                ", difficulty= " + difficulty +
                ", type= " + type +
                ", gif= " + gif +
                ", description= " + description + "'";
    }
}