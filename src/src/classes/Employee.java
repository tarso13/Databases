package classes;

import java.util.Date;

public abstract class Employee {
    private String firstName;
    private String lastName;

    public abstract String groupEmployer();

    public abstract String JobDepartment();

    private Date beginHiringDate;
    private EmployeesSalary employeesSalary;
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

    public void setFamilyState(FamilyState state) {
        this.familyState = state;
    }

    public FamilyState getFamilyState() {
        return this.familyState;
    }

    public void setBank_Info(BankInfo info) {
        this.bankInfo = info;
    }

    public BankInfo getBankInfo() {
        return this.bankInfo;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public Bonus getBonus() {
        return this.bonus;
    }

    public abstract String toString();

    public EmployeesSalary setEmployeesSalary(EmployeesSalary employeesSalary) {
        this.employeesSalary = employeesSalary;
    }

    public EmployeesSalary getEmployeesSalary() {
        return this.employeesSalary;
    }

}
