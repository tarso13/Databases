package classes;

import java.util.Map;

public class BankInfo {

    private int bankID;
    private int IBAN;

    private String bankName;

    private Map<String, EmployeesSalary> payment;

    public BankInfo(int bankID, int IBAN, String bankName){
        this.bankID = bankID;
        this.IBAN = IBAN;
        this.bankName = bankName;
    }

    public int getBankID() {
        return this.bankID;
    }

    public int getIBAN() {
        return this.IBAN;
    }

    public String getNameBank() {
        return this.bankName;
    }
}

