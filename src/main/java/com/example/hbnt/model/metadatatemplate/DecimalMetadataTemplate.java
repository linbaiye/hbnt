package com.example.hbnt.model.metadatatemplate;

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
    public Metadata<BigDecimal> createMetadata(BigDecimal value) {
        return null;
    }

    @Override
    public void accept(MetadataTemplateVisitor visitor) {
        visitor.visitDecimalTemplate(this);
    }

    @Override
    BigDecimal createValue(String str) {
        return new BigDecimal(str);
    }
}
