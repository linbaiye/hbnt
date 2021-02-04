package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.Metadata;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
@Getter
public class EnumMetadataTemplate extends MetadataTemplate<String> {

    private List<Value> possibleValues;

    public EnumMetadataTemplate(Long id, String key, String name, String defaultValue,
                                String defaultValueString, Boolean optional, Integer order,
                                Boolean shared, String valueJson, String tag, String placeholder,
                                String widgetType, String webCheckScript, String range) {
        super(id, Type.ENUM, key, name, defaultValue, defaultValueString, optional, order,
                shared, valueJson, tag, placeholder, widgetType, webCheckScript, range);
    }

    @Override
    boolean isValidValue(String s) {
        return false;
    }

    @Override
    Metadata<String> createMetadata(String value) {
        return null;
    }

    @Override
    public void accept(MetadataTemplateVisitor visitor) {
        visitor.visitEnumTemplate(this);
    }

    public static class Value {
        public String code;
        public String name;
    }
}
