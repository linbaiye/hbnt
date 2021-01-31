package com.example.hbnt.model;


/**
 * @author tao.lin
 * @date 2021/1/31
 */
public abstract class RangeMetadataTemplate<C extends Comparable<C>> extends MetadataTemplate<C> {

    C upperBoundary;
    boolean upperOpen;

    C lowerBoundary;
    boolean lowerOpen;

    static final Comparable<?> INFINITE = null;

    public RangeMetadataTemplate(String name,
                                 String key,
                                 C upperBoundary,
                                 boolean upperOpen,
                                 C lowerBoundary,
                                 boolean lowerOpen) {
        this.upperBoundary = upperBoundary;
        this.upperOpen = upperOpen;
        this.lowerBoundary = lowerBoundary;
        this.lowerOpen = lowerOpen;
    }


    @Override
    boolean isValidValue(C value) {
        boolean upperOk;
        if (upperBoundary == INFINITE) {
            upperOk = true;
        } else {
            upperOk = upperOpen ? upperBoundary.compareTo(value) > 0 : upperBoundary.compareTo(value) >= 0;
        }
        boolean lowerOk;
        if (lowerBoundary == INFINITE) {
            lowerOk = true;
        } else {
            lowerOk = lowerOpen ? lowerBoundary.compareTo(value) < 0 : lowerBoundary.compareTo(value) <= 0;
        }
        return lowerOk && upperOk;
    }
}
