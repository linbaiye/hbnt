package com.example.hbnt.model;

import com.example.hbnt.model.metadatatemplate.*;

import java.io.File;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
public class TermMetadataGroupValidator implements MetadataTemplateGroupValidator, MetadataTemplateVisitor {

    @Override
    public void validate(MetadataTemplateGroup group) {
        group.visitTemplates(this);
    }

    @Override
    public void visitBoolTemplate(BoolMetadataTemplate boolMetadataTemplate) {

    }

    @Override
    public void visitDecimalTemplate(DecimalMetadataTemplate decimalMetadataTemplate) {

    }

    @Override
    public void visitEnumTemplate(EnumMetadataTemplate enumMetadataTemplate) {

    }

    @Override
    public void visitIntegerTemplate(IntegerMetadataTemplate integerMetadataTemplate) {

    }

    @Override
    public void visitStringTemplate(StringMetadataTemplate stringMetadataTemplate) {
        if ("installmentCycle".equalsIgnoreCase(stringMetadataTemplate.getKey())) {
            if ("".equalsIgnoreCase(stringMetadataTemplate.getDefaultValue())) {

            }
        }
    }
}
