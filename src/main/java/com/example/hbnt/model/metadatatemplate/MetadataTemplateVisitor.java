package com.example.hbnt.model.metadatatemplate;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
public interface MetadataTemplateVisitor {

    void visitBoolTemplate(BoolMetadataTemplate boolMetadataTemplate);

    void visitDecimalTemplate(DecimalMetadataTemplate decimalMetadataTemplate);

    void visitEnumTemplate(EnumMetadataTemplate enumMetadataTemplate);

    void visitIntegerTemplate(IntegerMetadataTemplate integerMetadataTemplate);

    void visitStringTemplate(StringMetadataTemplate stringMetadataTemplate);
}
