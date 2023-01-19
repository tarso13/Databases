package src.classes;

import java.sql.SQLException;
import java.util.Date;

public abstract class Employee {
    private final String firstName;
    private final String lastName;

    public abstract String groupEmployer();

    public abstract String JobDepartment();
    private final Date beginHiringDate;

    private String address;

    private int phoneNumber;

    private FamilyState familyState;

    private BankInfo bankInfo;

    private Bonus bonus;

    Employee(String firstName, String lastName, String address, int phoneNumber, Date beginHiringDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.beginHiringDate = beginHiringDate;
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

    public abstract String toString();
}
