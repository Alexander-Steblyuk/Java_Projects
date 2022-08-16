package com.epam.knight.view;

import java.util.Locale;

public enum SearchParameter {
    COST,
    WEIGHT;

    @Override
    public String toString() {
        return super.toString().toLowerCase(Locale.ROOT);
    }
}
