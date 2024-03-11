import java.util.*;
public class Excercise {
    /* TO-DO:
     * add more specific muscles
     * add a goal of explosiveness, power.
     * add type: pull or push
     */
    private String name = "";
    enum Muscles{GLUTES, LEGS, CHEST, BACK, SHOULDERS, ABDOMINALS}; //contenedor
    private enum Goals{HIPERTROPHY, TONING, ENDURANCE, STRENGTH};
    boolean equipment;

    Muscles muscle;  //Muscle enum variable
    Goals goal; //Goal enum variable


    public Excercise(String name, boolean equipment){
        this.name = name;
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEquipment() {
        return equipment;
    }

    public void setEquipment(boolean equipment) {
        this.equipment = equipment;
    }

    public void setMuscle(int muscleInt){
        switch(muscleInt){
            case 0:
                muscle = Muscles.GLUTES;
                break;
            case 1:
                muscle = Muscles.LEGS;
                break;
            case 2:
                muscle = Muscles.CHEST;
                break;
            case 3:
                muscle = Muscles.BACK;
                break;
            case 4:
                muscle = Muscles.SHOULDERS;
                break;
            case 5:
                muscle = Muscles.ABDOMINALS;
                break;
            default:
                System.out.println("No group muscle was chosen");
        }
    }

    public Muscles getMuscle() {
        return muscle;
    }
    public void setGoal(int goalInt){
        switch(goalInt){
            case 0:
                goal = Goals.HIPERTROPHY;
                break;
            case 1:
                goal = Goals.TONING;
                break;
            case 2:
                goal = Goals.ENDURANCE;
                break;
            case 3:
                goal = Goals.STRENGTH;
                break;
            default:
                System.out.println("No goal was set");
        }
    }

    public Goals getGoal() {
        return goal;
    }

    @Override
    public String toString() {
        return "Excercise{" +
                "name= '" + name + '\'' +
                ", equipment= " + equipment +
                ", muscle= " + muscle +
                ", goal= " + goal +
                '}';
    }
}
