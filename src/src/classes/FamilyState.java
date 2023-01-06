package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FamilyState {

    private String state;
    private int numberKids;

    private final int StateID;

    private  String ages;

    private List<Integer> kidsAges;

    public FamilyState(String state, int numberKids, String ages, int StateID){
        this.state = state;
        this.numberKids = numberKids;
        this.ages = ages;
        setKidsAges();
        this.StateID = StateID;
    }

    public String getState() {
        return this.state;
    }

    public int getNumberKids() {
        return this.numberKids;
    }

    public void setKidsAges() {
        Scanner scanner = new Scanner(this.ages);
        List<Integer> list = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        this.kidsAges = list;
    }

    public List<Integer> getKidsAges(){
        return this.kidsAges;
    }

    public int getStateID() {
        return this.StateID;
    }

    public String toString() {
        return "FamilyState{" +
                "state" + state +
                ", numberKids" + numberKids +
                ", ages of kids " + ages +
                "}";
    }
}
