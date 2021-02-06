package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.metadata.IntegerMetadata;
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
                withinRange((Integer) v) ||
                ((v instanceof String) &&
                withinRange(Integer.valueOf((String)v)));
    }

    @Override
    public IntegerMetadata createMetadata(Object v) {
        if (!isValidValue(v)) {
            throw new IllegalArgumentException(v.toString());
        }
        if (v instanceof Integer) {
            return new IntegerMetadata(key, (Integer) v);
        }
        return new IntegerMetadata(key, createValue((String)v));
    }

    @Override
    Integer createValue(String str) {
        return Integer.valueOf(str);
    }
}
