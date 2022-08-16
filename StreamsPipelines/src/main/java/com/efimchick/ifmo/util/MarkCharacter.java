package com.efimchick.ifmo.util;

import java.util.stream.Stream;

public enum MarkCharacter {
    A(91, 100),
    B(83, 90),
    C(75, 82),
    D(68, 74),
    E(60, 67),
    F(0, 59);

    private final int minLimit;
    private final int maxLimit;

    MarkCharacter(int min, int max) {
        minLimit = min;
        maxLimit = max;
    }

    public static String getMark(double markValue) {
        return Stream.of(MarkCharacter.values())
                .filter(x -> markValue <= x.maxLimit && markValue >= x.minLimit)
                .findFirst()
                .orElse(F)
                .toString();
    }
}
