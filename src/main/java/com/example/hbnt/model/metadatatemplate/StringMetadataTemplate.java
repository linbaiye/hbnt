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
public class StringMetadataTemplate extends AbstractMetadataTemplate<String> {

    @Override
    public boolean isValidValue(Object s) {
        return s instanceof String;
    }

    @Override
    public StringMetadata createMetadata(Object value) {
        if (isValidValue(value)) {
            return new StringMetadata(key, (String)value);
        }
        throw new IllegalArgumentException("");
    }

}
