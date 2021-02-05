package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.metadata.IntegerMetadata;
import com.example.hbnt.model.metadata.Metadata;
import lombok.NoArgsConstructor;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@NoArgsConstructor
public class IntegerMetadataTemplate extends RangeMetadataTemplate<Integer> {

    @Override
    public boolean isValidValue(Object v) {
        return (v instanceof Integer) &&
                withinRange((Integer) v);
    }

    @Override
    public Metadata<Integer> createMetadata(Object v) {
        if (!isValidValue(v)) {
            throw new IllegalArgumentException("");
        }
        return new IntegerMetadata(key, (Integer)v);
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
