package com.epam.knight.model.ammunition;

/**
 * Lists different types of ammunition.
 */
public enum AmmunitionType {
    HELMET(true, "Helmet"),
    CUIRASS(true, "Cuirass"),
    BOOTS(true, "Boots"),
    SWORD(false, "Sword"),
    SHIELD(false, "Shield");

    private final String name;
    private final boolean isArmor;

    AmmunitionType(boolean isArmor, String name) {
        this.isArmor = isArmor;
        this.name = name;
    }

    public boolean isArmor() {
        return isArmor;
    }

    @Override
    public String toString() {
        return name;
    }

}
