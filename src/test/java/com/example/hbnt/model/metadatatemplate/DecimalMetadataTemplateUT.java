package com.example.hbnt.model.metadatatemplate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author tao.lin
 * @date 2021/2/6
 */
public class DecimalMetadataTemplateUT {

    @Test
    public void withinRange() {
        DecimalMetadataTemplate template = new DecimalMetadataTemplate();
        template.setRange("(0.3, 0.4]");
        template.init();
        Assert.assertTrue(template.isValidValue(new BigDecimal("0.31")));
        Assert.assertFalse(template.isValidValue(new BigDecimal("0.299")));
    }
}
