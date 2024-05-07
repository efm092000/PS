package com.dailyfit.progress;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Date;

@JsonAutoDetect
public record ExerciseDone(@JsonGetter("exercise") String exercise,
                           @JsonGetter("day") Date day,
                           @JsonGetter("email") String email,
                           @JsonGetter("rid") int rid,
                           @JsonGetter("weight") int weight,
                           @JsonGetter("sets") int sets,
                           @JsonGetter("reps") int reps) {
}