package classes;

import java.util.Date;

public class PermanentManager extends Employee{
    private String userName;
    private String password;
    private final int PMId;

    public PermanentManager(String firstName, String lastName, String address, int phoneNumber, Date beginHiringDate, int PMId) {
        super(firstName, lastName, address, phoneNumber, beginHiringDate);
        this.PMId= PMId;
    }

    public void setUserName(String username){
        this.userName = username;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public int getPMId(){
        return this.PMId;
    }

    @Override
    public String groupEmployer() {
        return "Permanent";
    }

    @Override
    public String JobDepartment() {
        return "Management";
    }

    @Override
    public String toString() {
        return "firstname: " + getFirstName() +
                ",lastname: " + getLastName() +
                ",address: " + getAddress() +
                ",phoneNumber: " + getPhoneNumber() +
                ",beginHiringDate: " + getBeginHiringDate() +
                ",username: " + userName +
                ", password: " + password +
                ", groupEmployer: " + groupEmployer() +
                ", JobDepartment: " + JobDepartment() +
                "}\n";
    }
}
