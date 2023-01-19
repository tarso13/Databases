package src.classes;

import java.util.Date;

public class ContractorEducator extends src.classes.Employee {
    private String userName;
    private String password;
    private final int CEId;

    public ContractorEducator(String firstName, String lastName, String address, int phoneNumber, Date beginHiringDate, int CEId) {
        super(firstName, lastName, address, phoneNumber, beginHiringDate);
        this.CEId= CEId;
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

    public int getCEId(){
        return this.CEId; }
    @Override
    public String groupEmployer() {
        return "Contractor";
    }

    @Override
    public String JobDepartment() {
        return "Educator";
    }

    @Override
    public String toString() {
        return "ContractorEducator{" +
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
