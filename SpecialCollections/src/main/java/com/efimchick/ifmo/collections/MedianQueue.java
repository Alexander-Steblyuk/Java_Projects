package com.efimchick.ifmo.collections;

import java.util.LinkedList;


class MedianQueue extends LinkedList<Integer> {
    private static final long serialVersionUID = 1;
    private static final int EVEN_DIVIDER = 2;
    private static final int HALF_OF_SIZE_DIVIDER = 2;

    @Override
    public Integer peek() {
        return super.get(getMedianIndex());
    }

    @Override
    public Integer poll() {
        return super.remove(getMedianIndex());
    }

    @Override
    public boolean offer(Integer integer) {
        boolean resultOfOffer;
        int insertIndex;

        if (this.size() > 0 && integer <= getLast()) {
            insertIndex = getInsertIndex(integer);
            super.add(insertIndex, integer);
            resultOfOffer = true;
        } else {
            resultOfOffer = super.offer(integer);
        }

        return resultOfOffer;
    }

    private int getMedianIndex() {
        int indexOfMedian;

        indexOfMedian = this.size() / HALF_OF_SIZE_DIVIDER;

        if (this.size() % EVEN_DIVIDER == 0) {
            indexOfMedian -= 1;
        }

        return indexOfMedian;
    }

    private int getInsertIndex(Integer element) {
        int insertIndex = 0;

        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) >= element) {
                insertIndex = i;
                break;
            }
        }

        return insertIndex;
    }

}
