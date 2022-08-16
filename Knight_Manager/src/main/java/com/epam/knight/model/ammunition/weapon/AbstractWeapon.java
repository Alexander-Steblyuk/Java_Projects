package com.epam.knight.model.ammunition.weapon;

import com.epam.knight.model.ammunition.AbstractAmmunition;
import com.epam.knight.model.ammunition.AmmunitionType;

public abstract class AbstractWeapon extends AbstractAmmunition {
    protected int damage;

    public AbstractWeapon(AmmunitionType type, int weight, int cost, int damage) {
        super(type, weight, cost);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

}
