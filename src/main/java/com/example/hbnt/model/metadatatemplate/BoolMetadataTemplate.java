package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.Metadata;
import lombok.Builder;
import lombok.Getter;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
@Getter
public class BoolMetadataTemplate extends MetadataTemplate<String> {

    @Builder
    public BoolMetadataTemplate(Long id,
                                String key,
                                String name,
                                String defaultValue,
                                String defaultValueString, Boolean optional, Integer order, Boolean shared) {
        super(id, Type.BOOL, key, name, defaultValue, defaultValueString, optional, order, shared);
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
        visitor.visitBoolTemplate(this);
    }
}
