package com.epam.knight.model.ammunition.armor;

import com.epam.knight.model.ammunition.AmmunitionType;

public class Boots extends AbstractArmor {
    private static final int DEFAULT_SPEED = 50;
    private int speed;


    public Boots(int weight, int cost, int protection) {
        super(AmmunitionType.BOOTS, weight, cost, protection);
        speed = DEFAULT_SPEED;
    }

    @Override
    public AmmunitionType getType() {
        return type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed > 0) {
            this.speed = speed;
        }
    }

}
