package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.Metadata;
import lombok.Builder;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
public class IntegerMetadataTemplate extends RangeMetadataTemplate<Integer> {


    @Builder
    public IntegerMetadataTemplate(Long id,
                                   String key,
                                   String name,
                                   Integer defaultValue,
                                   String defaultValueString,
                                   Boolean optional,
                                   Integer order,
                                   Boolean shared,
                                   Integer upperBoundary,
                                   boolean upperOpen,
                                   Integer lowerBoundary,
                                   boolean lowerOpen) {
        super(id,
                Type.INTEGER,
                key,
                name,
                defaultValue,
                defaultValueString,
                optional,
                order,
                shared,
                upperBoundary,
                upperOpen,
                lowerBoundary,
                lowerOpen);
    }

    @Override
    Metadata<Integer> createMetadata(Integer value) {
        if (!isValidValue(value)) {
            throw new IllegalArgumentException("");
        }
        return null;
    }

    @Override
    public void accept(MetadataTemplateVisitor visitor) {
        visitor.visitIntegerTemplate(this);
    }
}
