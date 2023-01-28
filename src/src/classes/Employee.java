package src.classes;

import java.util.Date;

public abstract class Employee {

    private String firstName;
    private String lastName;

    private LoginInfo loginInfo;

    public String category;
    private Date beginHiringDate;
    private String address;
    private int phoneNumber;
    private FamilyState familyState;
    private BankInfo bankInfo;
    private Bonus bonus;
    private int EmployeeId;
    private double salary;

    Employee(String firstName, String lastName, String address, int phoneNumber, Date beginHiringDate, int EmployeeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.beginHiringDate = beginHiringDate;
        this.EmployeeId = EmployeeId;
    }

    public void setSalary(double salary)
    {
        this.salary = salary;
    }

    public double getSalary(){
        return this.salary;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Date getBeginHiringDate() {
        return this.beginHiringDate;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public String toString(){
        return "Category: " + category + "{" +
                "firstname: " + getFirstName() +
                ",lastname: " + getLastName() +
                ",address: " + getAddress() +
                ",phoneNumber: " + getPhoneNumber() +
                ",beginHiringDate: " + getBeginHiringDate() +
                "}\n";
    }
}
