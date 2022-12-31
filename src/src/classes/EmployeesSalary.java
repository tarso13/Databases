package classes;

public class EmployeesSalary {
    private int basicSalary;
    private int contractSalary;

    private Employee employee;
    private int starting_basic_salary;
    private int starting_contract_salary;
    private int SalaryID;
    public EmployeesSalary(int basicSalary, int contractSalary, int salaryID) {
        this.basicSalary = basicSalary;
        this.contractSalary = contractSalary;
        this.SalaryID = salaryID;
    }

    public int getStartingBasicSalary() {
        return this.starting_basic_salary;
    }

    public int getStartingContractSalary() {
        return this.starting_contract_salary;
    }

    public int getBasicSalary() {
        return this.basicSalary;
    }

    public int getContractSalary() {
        return this.contractSalary;
    }

    public int getSalaryID() {
        return this.SalaryID;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public int getSalary() {
        if (employee.groupEmployer() == "Permanent") {
            return basicSalary;
        }
        return contractSalary;
    }

}