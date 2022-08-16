package com.epam.knight.model;

import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.armor.AbstractArmor;
import com.epam.knight.model.ammunition.weapon.AbstractWeapon;
import com.epam.knight.view.SearchParameter;
import com.epam.knight.view.SortParameter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Manipulates with knight's ammunition and updates knight stats.
 */
public class KnightAmmunitionManager {
    private final Knight knight;

    public KnightAmmunitionManager(Knight knight) {
        this.knight = knight;
    }

    /**
     * Equips item to knight and update knight's stats
     */
    public void equipAmmunitionToKnight(Ammunition item) {
        knight.equip(item);
        updateKnightStats();
    }

    private void updateKnightStats() {
        knight.setWeight(calculateAmmunitionWeight());
        knight.setCost(calculateAmmunitionCost());
        knight.setProtection(calculateAmmunitionProtection());
        knight.setDamage(calculateAmmunitionDamage());
    }

    public int calculateAmmunitionWeight() {
        int currAmmunitionWeight = 0;

        for (Ammunition ammunition : knight.getAmmunition()) {
            currAmmunitionWeight += ammunition.getWeight();
        }

        return currAmmunitionWeight;
    }

    public int calculateAmmunitionCost() {
        int currAmmunitionCost = 0;

        for (Ammunition ammunition : knight.getAmmunition()) {
            currAmmunitionCost += ammunition.getCost();
        }

        return currAmmunitionCost;
    }

    public int calculateAmmunitionDamage() {
        int currAmmunitionDamage = 0;

        for (Ammunition ammunition: knight.getAmmunition()) {
            if (ammunition instanceof AbstractWeapon) {
                currAmmunitionDamage += ((AbstractWeapon) ammunition).getDamage();
            }
        }

        return currAmmunitionDamage;
    }

    public int calculateAmmunitionProtection() {
        int currAmmunitionProtection = 0;

        for (Ammunition ammunition: knight.getAmmunition()) {
            if (ammunition instanceof AbstractArmor) {
                currAmmunitionProtection += ((AbstractArmor) ammunition).getProtection();
            }
        }

        return currAmmunitionProtection;
    }

    public void sortAmmunition(SortParameter option) {
        Ammunition[] sortedAmmunition = knight.getAmmunition();
        Comparator<Ammunition> comparator;

        if (option == SortParameter.WEIGHT) {
            comparator = Comparator.comparingInt(Ammunition::getWeight);
        } else {
            comparator = Comparator.comparingInt(Ammunition::getCost);
        }

        Arrays.sort(sortedAmmunition, comparator);
        knight.setAmmunition(sortedAmmunition);
    }

    public Ammunition[] searchAmmunition(SearchParameter option, int[] params) {
        Ammunition[] knightAmmunition = knight.getAmmunition();
        Ammunition[] searchResults = new Ammunition[knightAmmunition.length];
        int countOfElements = 0;

        if (option == SearchParameter.WEIGHT) {
            for (Ammunition ammunition : knightAmmunition) {
                if (ammunition.getWeight() >= params[0] && ammunition.getWeight() <= params[1]) {
                    searchResults[countOfElements] = ammunition;
                    countOfElements++;
                }
            }
        } else {
            for (Ammunition ammunition : knightAmmunition) {
                if (ammunition.getCost() >= params[0] && ammunition.getCost() <= params[1]) {
                    searchResults[countOfElements] = ammunition;
                    countOfElements++;
                }
            }
        }

        return Arrays.copyOf(searchResults, countOfElements);
    }

}
