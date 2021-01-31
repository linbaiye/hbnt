package com.example.hbnt.model;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
public class IntegerMetadataTemplate extends RangeMetadataTemplate<Integer> {

    public IntegerMetadataTemplate(String name,
                                   String key,
                                   Integer upperBoundary,
                                   boolean upperOpen,
                                   Integer lowerBoundary, boolean lowerOpen) {
        super(name, key, upperBoundary, upperOpen, lowerBoundary, lowerOpen);
    }

    @Override
    Metadata<Integer> createMetadata(Integer value) {
        return null;
    }
}
