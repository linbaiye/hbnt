package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.metadata.Metadata;
import com.example.hbnt.model.metadata.StringMetadata;
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
public class StringMetadataTemplate extends MetadataTemplate<String> {

    @Override
    public boolean isValidValue(String s) {
        return true;
    }

    @Override
    public Metadata<String> createMetadata(Object value) {
        return new StringMetadata(key, (String)value);
    }

    @Override
    public void accept(MetadataTemplateVisitor visitor) {
        visitor.visitStringTemplate(this);
    }
}
