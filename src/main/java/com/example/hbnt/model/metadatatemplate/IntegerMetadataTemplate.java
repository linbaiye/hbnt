package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.Metadata;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@SuperBuilder
public class IntegerMetadataTemplate extends RangeMetadataTemplate<Integer> {

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

    @Override
    Integer createValue(String str) {
        return Integer.valueOf(str);
    }
}
