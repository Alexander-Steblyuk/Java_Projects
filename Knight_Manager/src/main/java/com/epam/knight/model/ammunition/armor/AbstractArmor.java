package com.epam.knight.model.ammunition.armor;

import com.epam.knight.model.ammunition.AbstractAmmunition;
import com.epam.knight.model.ammunition.AmmunitionType;

public abstract class AbstractArmor extends AbstractAmmunition {
    protected int protection;

    public AbstractArmor(AmmunitionType type, int weight, int cost, int protection) {
        super(type, weight, cost);
        this.protection = protection;
    }

    public int getProtection() {
        return protection;
    }
}
