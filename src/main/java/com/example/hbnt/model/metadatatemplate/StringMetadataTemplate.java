package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.Metadata;
import lombok.Builder;
import lombok.Getter;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
@Getter
public class StringMetadataTemplate extends MetadataTemplate<String> {

    public StringMetadataTemplate(Long id, String key, String name, String defaultValue,
                                  String defaultValueString, Boolean optional, Integer order,
                                  Boolean shared, String valueJson, String tag, String placeholder,
                                  String widgetType, String webCheckScript, String range) {
        super(id, Type.STRING, key, name, defaultValue, defaultValueString, optional, order,
                shared, valueJson, tag, placeholder, widgetType, webCheckScript, range);
    }


    @Override
    boolean isValidValue(String s) {
        return true;
    }

    @Override
    Metadata<String> createMetadata(String value) {
        return null;
    }

    @Override
    public void accept(MetadataTemplateVisitor visitor) {
        visitor.visitStringTemplate(this);
    }
}
