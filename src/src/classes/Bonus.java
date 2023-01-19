package src.classes;

public class Bonus {

    private double familyBonus;
    private double searchBonus;
    private double libraryBonus;

    private int BonusId;

    public Bonus(int BonusId, double familyBonus, double searchBonus, double libraryBonus){
        this.BonusId = BonusId;
        this.familyBonus = familyBonus;
        this.searchBonus = searchBonus;
        this.libraryBonus = libraryBonus;
    }
}
