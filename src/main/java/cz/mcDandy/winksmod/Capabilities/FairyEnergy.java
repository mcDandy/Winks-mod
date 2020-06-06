package cz.mcDandy.winksmod.Capabilities;

public class FairyEnergy implements IFairyEnergy {
    private double amount = 0;

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;

        if(this.amount < 0)
            this.amount = 0;
    }

    @Override
    public void addOrSubtractAmount(double amount) {
        this.amount += amount;

        if(this.amount < 0)
            this.amount = 0;
    }
}