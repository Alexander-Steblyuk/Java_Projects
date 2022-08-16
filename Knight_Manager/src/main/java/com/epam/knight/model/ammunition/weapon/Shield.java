package com.epam.knight.model.ammunition.weapon;

import com.epam.knight.model.ammunition.AmmunitionType;

public class Shield extends AbstractWeapon {
    private static final int DEFAULT_BLOCK_GRADE = 10;
    private int blockGrade;

    public Shield(int weight, int cost, int damage) {
        super(AmmunitionType.SHIELD, weight, cost, damage);
        blockGrade = DEFAULT_BLOCK_GRADE;
    }

    public int getBlockGrade() {
        return blockGrade;
    }

    public void setBlockGrade(int blockGrade) {
        this.blockGrade = blockGrade;
    }

    @Override
    public int getDamage() {
        return damage + blockGrade;
    }

    @Override
    public AmmunitionType getType() {
        return type;
    }
}
