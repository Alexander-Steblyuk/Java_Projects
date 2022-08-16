package com.efimchick.ifmo.collections;

import java.util.Comparator;
import java.util.TreeSet;

class SortedByAbsoluteValueIntegerSet extends TreeSet<Integer> {
    private static final long serialVersionUID = 1;

    private static final Comparator<Integer> COMPARATOR = new Comparator<>() {
        @Override
        public int compare(Integer num1, Integer num2) {
            return Integer.compare(Math.abs(num1), Math.abs(num2));
        }
    };

    public SortedByAbsoluteValueIntegerSet() {
        super(COMPARATOR);
    }

    @Override
    public boolean contains(Object o) {
        boolean isContains;

        if (o instanceof Integer) {
            isContains = super.contains(Math.abs((Integer) o));
        } else {
            isContains = super.contains(o);
        }

        return isContains;
    }
}
