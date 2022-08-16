package com.efimchick.ifmo.collections;

import java.util.ArrayList;
import java.util.Collection;

class PairStringList extends ArrayList<String> {
    private static final long serialVersionUID = 1;
    private static final int EVEN_DIVIDER = 2;
    private static final int ADD_STEP = 2;

    private static final String INDEX_OUT_OF_BOUND_MESSAGE = "This index goes out of list size!";

    private boolean checkLeftNeighbour(int index) {
        return index > 0 && index % EVEN_DIVIDER != 0;
    }

    @Override
    public boolean add(String s) {
        super.add(s);
        return super.add(s);
    }

    @Override
    public void add(int index, String element) {
        
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE);
        } else {
            if (checkLeftNeighbour(index)) {
                super.add(index + 1, element);
                super.add(index + 1, element);
            } else {
                super.add(index, element);
                super.add(index, element);
            }
        }
    }

    @Override
    public String remove(int index) {
        String removedString;

        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE);
        } else {
            if (checkLeftNeighbour(index)) {
                removedString = super.remove(index);
                super.remove(index - 1);
            } else {
                removedString = super.remove(index);
                super.remove(index);
            }
        }

        return removedString;
    }

    @Override
    public boolean remove(Object o) {
        super.remove(o);
        return super.remove(o);
    }

    @Override
    public String get(int index) {
        String resultString;
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE);
        } else {
            resultString = super.get(index);
        }

        return resultString;
    }

    @Override
    public String set(int index, String element) {
        String resultString;

        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE);
        } else {
            if (checkLeftNeighbour(index)) {
                super.set(index - 1, element);
            } else {
                super.set(index + 1, element);
            }
            resultString = super.set(index, element);
        }

        return resultString;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean resultOfAdd = true;

        for (String element : c) {
            resultOfAdd &= add(element);
        }

        return resultOfAdd;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        int indexOfAdd = index;

        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE);
        } else {
            for (String element : c) {
                add(indexOfAdd, element);
                indexOfAdd += ADD_STEP;
            }
        }

        return true;
    }
}
