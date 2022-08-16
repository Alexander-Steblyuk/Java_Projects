package com.epam.knight.model.ammunition.armor;

import com.epam.knight.model.ammunition.AmmunitionType;

public class Cuirass extends AbstractArmor {
    private static final int MAX_COOL_GRADE = 100;
    private int coolGrade;
    private boolean capeExistence;

    public Cuirass(int weight, int cost, int protection) {
        super(AmmunitionType.CUIRASS, weight, cost, protection);
    }

    @Override
    public AmmunitionType getType() {
        return type;
    }

    public boolean isCapeExist() {
        return capeExistence;
    }

    public void wearCape() {
        if (!capeExistence) {
            capeExistence = true;
            coolGrade = MAX_COOL_GRADE;
        }
    }

    public int getCoolGrade() {
        return coolGrade;
    }
}
