package classes;

import com.sun.source.tree.ReturnTree;

import java.sql.SQLException;

public class EmployeesSalary {

    private double initialBSalary;

    private double initialCSalary;
    private double basicSalary;
    private double contractSalary;

    private int monthsContract;

    private int SalaryID;
    
    public EmployeesSalary(double basicSalary, double contractSalary, int monthsContract, int salaryID){
        this.basicSalary = basicSalary;
        this.contractSalary = contractSalary;
        this.monthsContract = monthsContract;
        this.SalaryID = salaryID;
    }

    public void setInitialSalary(double basicSalary, double contractSalary) {
        this.initialBSalary = basicSalary;
        this.initialCSalary = contractSalary;
    }

    public double getInitialBSalary(){
        return this.initialBSalary;
    }

    public double getInitialCSalary(){
        return this.initialCSalary;
    }

    public double getBasicSalary(){
        return this.basicSalary;
    }

    public double getContractSalary(){
        return this.contractSalary;
    }

    public int getMonthsContract(){
        return this.monthsContract;
    }

    public int getSalaryID(){
        return this.SalaryID;
    }
}
