package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.Metadata;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * @author tao.lin
 * @date 2021/2/3
 */
public class DecimalMetadataTemplate extends RangeMetadataTemplate<BigDecimal> {

    @Builder
    public DecimalMetadataTemplate(Long id,
                                   String key,
                                   String name,
                                   BigDecimal defaultValue,
                                   String defaultValueString,
                                   Boolean optional,
                                   Integer order,
                                   Boolean shared,
                                   BigDecimal upperBoundary,
                                   boolean upperOpen,
                                   BigDecimal lowerBoundary,
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
    Metadata<BigDecimal> createMetadata(BigDecimal value) {
        return null;
    }

    @Override
    public void accept(MetadataTemplateVisitor visitor) {
        visitor.visitDecimalTemplate(this);
    }
}
