package ru.babenko.models.banks;

public class CentralBank implements Bank {
    private static CentralBank instance;

    private CentralBank() { }

    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    public Bank createBank(String name) {
        return new BankImpl(name);
    }

    @Override
    public String getName() {
        return "Central Bank";
    }
}
