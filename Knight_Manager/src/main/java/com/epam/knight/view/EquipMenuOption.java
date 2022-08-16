package com.epam.knight.view;

import com.epam.knight.model.ammunition.AmmunitionType;

import java.util.Locale;

public enum EquipMenuOption {
    SWORD {
        @Override
        public AmmunitionType getAmmunitionType() {
            return AmmunitionType.SWORD;
        }
    },
    SHIELD {
        @Override
        public AmmunitionType getAmmunitionType() {
            return AmmunitionType.SHIELD;
        }
    },
    HELMET {
        @Override
        public AmmunitionType getAmmunitionType() {
            return AmmunitionType.HELMET;
        }
    },
    CUIRASS {
        @Override
        public AmmunitionType getAmmunitionType() {
            return AmmunitionType.CUIRASS;
        }
    },
    BOOTS {
        @Override
        public AmmunitionType getAmmunitionType() {
            return AmmunitionType.BOOTS;
        }
    };

    public abstract AmmunitionType getAmmunitionType();

    @Override
    public String toString() {
        return super.toString().toLowerCase(Locale.ROOT);
    }
}
