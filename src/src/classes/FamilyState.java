package classes;

import java.util.List;

public class FamilyState {

    private String state;
    private int numberKids;

    private final int StateID;

    private EmployeesSalary salary;

    private  List<Integer> ages;

    FamilyState(String state, int numberKids, List<Integer> ages, int StateID){
        this.state = state;
        this.numberKids = numberKids;
        this.ages = ages;
        this.StateID = StateID;
    }

    public String getState() {
        return this.state;
    }

    public int getNumberKids() {
        return this.numberKids;
    }

    public List<Integer> getAges() {
        return this.ages;
    }

    public int getStateID() {
        return this.StateID;
    }

    public void setFamilyStateSalary(EmployeesSalary salary) {
        this.salary = salary;
    }

    public EmployeesSalary getFamilyStateSalary() {
        return this.salary;
    }

    public String toString(){
        return "FamilyState{" +
                "state" + state +
                ", numberKids" + numberKids +
                ", ages of kids " + ages +
                "}";
    }

    public int setFamilyBonus(){
        int bonus=0;

        for (int i=0; i<numberKids; i++){
            bonus += 0.05 * salary.getSalary();
        }

        return bonus;
    }
}