package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.metadata.Metadata;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public class BoolMetadataTemplate extends MetadataTemplate<String> {

    @Override
    public boolean isValidValue(String s) {
        return false;
    }

    @Override
    public Metadata<String> createMetadata(String value) {
        return null;
    }

    @Override
    public void accept(MetadataTemplateVisitor visitor) {
        visitor.visitBoolTemplate(this);
    }
}
