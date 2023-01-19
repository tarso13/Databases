package src.classes;

import java.util.Map;

public class BankInfo {

    private int bankID;
    private int IBAN;

    private String bankName;
    private int timesPaid;

    public BankInfo(int bankID, int IBAN, String bankName,int timesPaid){
        this.bankID = bankID;
        this.IBAN = IBAN;
        this.bankName = bankName;
        this.timesPaid = timesPaid;
    }
}

