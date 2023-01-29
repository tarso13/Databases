package src.classes;

public class BankInfo {

    private int bankID;
    private int IBAN;
    private String bankName;
    private int timesPaid;

    BankInfo(int bankID, int IBAN, String bankName, int timesPaid){
        this.bankID = bankID;
        this.IBAN = IBAN;
        this.bankName = bankName;
        this.timesPaid = timesPaid;
    }
}

