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

    public int getBankID() {
        return this.bankID;
    }

    public int getIBAN() {
        return this.IBAN;
    }

    public String getNameBank() {
        return this.bankName;
    }
    public int getTimesPaid() {
        return this.timesPaid;
    }
}

