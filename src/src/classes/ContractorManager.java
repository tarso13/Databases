package classes;

import java.util.Date;

public class ContractorManager extends Employee{
    private String userName;
    private String password;
    private final int CMId;

    public ContractorManager(String firstName, String lastName, String address, int phoneNumber, Date beginHiringDate, int CMId) {
        super(firstName, lastName, address, phoneNumber, beginHiringDate);
        this.CMId= CMId;
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

    public int getCMId(){
        return this.CMId;
    }

    @Override
    public String groupEmployer() {
        return "Contractor";
    }

    @Override
    public String JobDepartment() {
        return "Manager";
    }

    @Override
    public String toString() {
        return "ContractorManager{" +
                "firstname: " + getFirstName() +
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