package com.epam.knight.model.ammunition.armor;

import com.epam.knight.model.ammunition.AmmunitionType;

public class Helmet extends AbstractArmor {
    private static final int VISOR_PROTECTION = 10;
    private boolean visorIsDown;

    public Helmet(int weight, int cost, int protection) {
        super(AmmunitionType.HELMET, weight, cost, protection);
    }

    public void moveVisor() {
        visorIsDown = !visorIsDown;
    }

    public void setVisorIsDown(boolean visorIsDown) {
        this.visorIsDown = visorIsDown;
    }

    public boolean isVisorLowered() {
        return visorIsDown;
    }

    @Override
    public int getProtection() {
        int fullProtection = super.getProtection();
        if (isVisorLowered()) {
            fullProtection += VISOR_PROTECTION;
        }

        return fullProtection;
    }

    @Override
    public AmmunitionType getType() {
        return type;
    }
}
