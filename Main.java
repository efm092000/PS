import java.util.*;
public class Main {
    public static void main (String[] args){
        Excercise excercise = new Excercise("Squats", false);
        excercise.setMuscle(1);
        excercise.setGoal(2);
        System.out.println("Exercise Info: " + excercise.toString());

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.setMinutes(2);
        stopwatch.setSeconds(30);
        stopwatch.start();

    }
}