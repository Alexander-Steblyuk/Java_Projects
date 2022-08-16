package com.epam.knight.controller;

import com.epam.knight.model.Knight;

/**
 * Generates knight with some ammunition.
 */
public final class KnightGenerator {

    private KnightGenerator() {
        super();
    }
    /**
     * Use it to quickly generate knight
     *
     * @return knight
     */
    public static Knight generateKnight() {
        return new Knight();
    }

}
