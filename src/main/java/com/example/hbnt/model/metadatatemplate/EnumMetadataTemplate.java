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

    @Builder
    public EnumMetadataTemplate(Long id,
                                String key,
                                String name,
                                String defaultValue,
                                String defaultValueString,
                                Boolean optional,
                                List<Value> possibleValues,
                                Integer order, Boolean shared) {
        super(id, Type.ENUM, key, name, defaultValue, defaultValueString, optional, order, shared);
        this.possibleValues = possibleValues;
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
