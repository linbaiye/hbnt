package com.example.hbnt.model.metadata;

import com.example.hbnt.model.metadatatemplate.Type;

import java.math.BigDecimal;

/**
 * @author tao.lin
 * @date 2021/2/6
 */
public class DecimalMetadata extends Metadata<BigDecimal>  {

    public DecimalMetadata(String name, BigDecimal value) {
        super(name, value, Type.DECIMAL);
    }
}
