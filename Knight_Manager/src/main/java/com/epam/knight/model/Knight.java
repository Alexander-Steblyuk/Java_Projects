package com.epam.knight.model;

import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.AmmunitionType;
import com.epam.knight.model.ammunition.armor.AbstractArmor;

import java.util.Arrays;

/**
 * Stores equipped ammunition and calculated stats.
 */
public class Knight {
    private static final int DEFAULT_AMMUNITION_CAPACITY = 1;

    private Ammunition[] ammunition;
    private int weight;
    private int cost;
    private int damage;
    private int protection;
    private int coolGrade;
    private int speed;

    public Knight() {
        ammunition = new Ammunition[DEFAULT_AMMUNITION_CAPACITY];
    }

    public Knight(int ammunitionCapacity) {
        ammunition = new Ammunition[ammunitionCapacity];
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getProtection() {
        return protection;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCoolGrade() {
        return coolGrade;
    }

    public Ammunition[] getAmmunition() {
        return ammunition.clone();
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }

    public void setCoolGrade(int coolGrade) {
        this.coolGrade = coolGrade;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAmmunition(Ammunition[] ammunition) {
        this.ammunition = ammunition.clone();
    }

    /**
     * Add new ammunition element to knight
     *
     * @param element that should be equipped to the knight
     */

    public void equip(Ammunition element) {
        if (!isArmorTypeExists(element)) {
            if (ammunition.length >= 1 && ammunition[ammunition.length - 1] != null) {
                ammunition = Arrays.copyOf(ammunition, ammunition.length + 1);
            }
            ammunition[ammunition.length - 1] = element;
        }
    }

    private boolean isArmorTypeExists(Ammunition element) {
        AmmunitionType checkType;
        boolean result = false;

        if (element instanceof AbstractArmor) {
            checkType = element.getType();

            for (Ammunition ammunitionElement : ammunition) {
                if (ammunitionElement != null && ammunitionElement.getType() == checkType) {
                    result = true;
                }
            }
        }

        return result;
    }
}

