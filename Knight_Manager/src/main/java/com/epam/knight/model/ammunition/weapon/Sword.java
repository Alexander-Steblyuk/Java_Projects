package com.epam.knight.model.ammunition.weapon;

import com.epam.knight.model.ammunition.AmmunitionType;

public class Sword extends AbstractWeapon {
    private static final int DEFAULT_BLADE_LENGTH = 5;
    private int bladeLength;

    public Sword(int weight, int cost, int damage) {
        super(AmmunitionType.SWORD, weight, cost, damage);
        bladeLength = DEFAULT_BLADE_LENGTH;
    }

    public int getBladeLength() {
        return bladeLength;
    }

    public void setBladeLength(int bladeLength) {
        this.bladeLength = bladeLength;
    }

    @Override
    public int getDamage() {
        return damage * bladeLength;
    }

    @Override
    public AmmunitionType getType() {
        return type;
    }
}
