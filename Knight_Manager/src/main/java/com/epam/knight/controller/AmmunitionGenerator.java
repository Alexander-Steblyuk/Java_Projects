package com.epam.knight.controller;

import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.AmmunitionType;
import com.epam.knight.model.ammunition.armor.Boots;
import com.epam.knight.model.ammunition.armor.Cuirass;
import com.epam.knight.model.ammunition.armor.Helmet;
import com.epam.knight.model.ammunition.weapon.Shield;
import com.epam.knight.model.ammunition.weapon.Sword;

/**
 * Generates pieces of {@link Ammunition}.
 */
public class AmmunitionGenerator {
    private static final int WEIGHT_INDEX = 0;
    private static final int COST_INDEX = 1;
    private static final int OTHER_PARAM_INDEX = 2;
    private static final int DEFAULT_ARMOR_WEIGHT = 100;
    private static final int DEFAULT_WEAPON_WEIGHT = 45;
    private static final int DEFAULT_ARMOR_COST = 78;
    private static final int DEFAULT_WEAPON_COST = 60;
    private static final int DEFAULT_PROTECTION = 50;
    private static final int DEFAULT_DAMAGE = 35;

    public Ammunition generateAmmunition(AmmunitionType type, int[] stats) {
        Ammunition ammunition = null;

        switch (type) {
            case BOOTS:
                ammunition = new Boots(stats[WEIGHT_INDEX], stats[COST_INDEX], stats[OTHER_PARAM_INDEX]);
                break;
            case CUIRASS:
                ammunition = new Cuirass(stats[WEIGHT_INDEX], stats[COST_INDEX], stats[OTHER_PARAM_INDEX]);
                break;
            case HELMET:
                ammunition = new Helmet(stats[WEIGHT_INDEX], stats[COST_INDEX], stats[OTHER_PARAM_INDEX]);
                break;
            case SHIELD:
                ammunition = new Shield(stats[WEIGHT_INDEX], stats[COST_INDEX], stats[OTHER_PARAM_INDEX]);
                break;
            case SWORD:
                ammunition = new Sword(stats[WEIGHT_INDEX], stats[COST_INDEX], stats[OTHER_PARAM_INDEX]);
                break;
            default:
                break;
        }

        return ammunition;
    }

    public Ammunition generateAmmunition(AmmunitionType type) {
        Ammunition ammunition = null;

        switch (type) {
            case BOOTS:
                ammunition = new Boots(DEFAULT_ARMOR_WEIGHT, DEFAULT_ARMOR_COST, DEFAULT_PROTECTION);
                break;
            case CUIRASS:
                ammunition = new Cuirass(DEFAULT_ARMOR_WEIGHT, DEFAULT_ARMOR_COST, DEFAULT_PROTECTION);
                break;
            case HELMET:
                ammunition = new Helmet(DEFAULT_ARMOR_WEIGHT, DEFAULT_ARMOR_COST, DEFAULT_PROTECTION);
                break;
            case SHIELD:
                ammunition = new Shield(DEFAULT_WEAPON_WEIGHT, DEFAULT_WEAPON_COST, DEFAULT_DAMAGE);
                break;
            case SWORD:
                ammunition = new Sword(DEFAULT_WEAPON_WEIGHT, DEFAULT_WEAPON_COST, DEFAULT_DAMAGE);
                break;
            default:
                break;
        }

        return ammunition;
    }

}
