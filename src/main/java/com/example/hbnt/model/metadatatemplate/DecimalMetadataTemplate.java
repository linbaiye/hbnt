package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.metadata.DecimalMetadata;
import com.example.hbnt.model.metadata.Metadata;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author tao.lin
 * @date 2021/2/3
 */
@NoArgsConstructor
public class DecimalMetadataTemplate extends RangeMetadataTemplate<BigDecimal> {

    @Override
    BigDecimal createValue(String str) {
        return new BigDecimal(str);
    }

    @Override
    public boolean isValidValue(Object v) {
        return (v instanceof BigDecimal) &&
                withinRange((BigDecimal) v) ||
                ((v instanceof Double) && withinRange(BigDecimal.valueOf((Double)v)));
    }

    @Override
    public DecimalMetadata createMetadata(Object value) {
        if (!isValidValue(value)) {
            throw new IllegalArgumentException("bad metadata " + name + ", value: " + value);
        }
        if (value instanceof BigDecimal) {
            return new DecimalMetadata(name, (BigDecimal) value);
        }
        return new DecimalMetadata(name, BigDecimal.valueOf((Double)value));
    }
}
