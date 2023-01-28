package src.classes;

import java.util.Date;

public class PermanentEducator extends Employee{
    private String userName;
    private String password;
    private final int PEId;

    public PermanentEducator(String firstName, String lastName, String address, int phoneNumber, Date beginHiringDate, int PEId, int EmployeeId) {
        super(firstName, lastName, address, phoneNumber, beginHiringDate, EmployeeId);
        this.PEId= PEId;
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

    public int getPEId(){
        return this.PEId;
    }

//    @Override
//    public String groupEmployer() {
//        return "Permanent";
//    }
//
//    @Override
//    public String JobDepartment() {
//        return "Educator";
//    }

//    @Override
//    public String toString() {
//        return "PermanentEducator{" +
//                "firstname: " + getFirstName() +
//                ",lastname: " + getLastName() +
//                ",address: " + getAddress() +
//                ",phoneNumber: " + getPhoneNumber() +
//                ",beginHiringDate: " + getBeginHiringDate() +
//                ",username: " + userName +
//                ", password: " + password +
//                ", groupEmployer: " + groupEmployer() +
//                ", JobDepartment: " + JobDepartment() +
//                "}\n";
//    }
}

