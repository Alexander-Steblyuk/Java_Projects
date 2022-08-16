package com.epam.knight.model.ammunition;

public abstract class AbstractAmmunition implements Ammunition {
    protected int weight;
    protected int cost;
    protected final AmmunitionType type;

    public AbstractAmmunition(AmmunitionType type, int weight, int cost) {

        if (type == null) {
            throw new IllegalArgumentException();
        }

        this.type = type;
        this.weight = weight;
        this.cost = cost;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getCost() {
        return cost;
    }

}
