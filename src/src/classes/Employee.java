package classes;

import java.sql.SQLException;
import java.util.Date;

public abstract class Employee {

    public enum employee {PE, PM, CE, CM}
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

    private EmployeesSalary salary;

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

    public void setBankInfo(BankInfo info) {
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

    public void setEmployeesSalary(EmployeesSalary salary) throws SQLException{
        this.salary = salary;
    }

    public EmployeesSalary getEmployeesSalary() {
        return this.salary;
    }

    public abstract String toString();

    public employee findEmployee(){
        if (groupEmployer().equals("Permanent")){
            if (JobDepartment().equals("Educator")) return employee.PE;
            return employee.PM;
        } else{
            if (JobDepartment().equals("Educator")) return employee.CE;
            return employee.CM;
        }
    }

    public double calculateFamilyBonus() {
        int underageKids=0;

        if (familyState == null) return 0;

        if (familyState.getState().equals("married")){
            for (int i : familyState.getKidsAges()){
                if (i <18) underageKids +=1;
            }

            return 0.05*(underageKids+1);
        }
        return 0;
    }

    public void setSalary() throws SQLException {
        String date= beginHiringDate.toString();
        String dateParts[] = date.split("-");
        double bonus1 = 0, basicSalary = salary.getBasicSalary(), contractSalary = salary.getContractSalary();


        if (groupEmployer() == "Permanent"){
            bonus1 += bonus.getFamilyBonus() + 0.15 * (2022 - Integer.parseInt(dateParts[0]));
            if (JobDepartment() == "Educator") bonus1 += bonus.getSearchBonus();
            basicSalary += basicSalary * bonus1;
            contractSalary = 0;
        } else {
            bonus1 += getBonus().getFamilyBonus();
            if (JobDepartment() == "Educator") bonus1 += bonus.getLibraryBonus();
            contractSalary = contractSalary * ((2022-Integer.parseInt(dateParts[0]))*12 + 12-Integer.parseInt(dateParts[1])+1)/salary.getMonthsContract();
            basicSalary = 0;
        }

        this.salary = new EmployeesSalary(basicSalary,contractSalary,salary.getMonthsContract(),salary.getSalaryID());
    }
}
