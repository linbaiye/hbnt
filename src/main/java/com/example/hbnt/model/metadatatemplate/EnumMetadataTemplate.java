package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.metadata.EnumMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
@Getter
@SuperBuilder
@NoArgsConstructor
public class EnumMetadataTemplate extends AbstractMetadataTemplate<String> {

    private List<Value> possibleValues;

    @Override
    public boolean isValidValue(Object v) {
        if (!(v instanceof String)) {
            return false;
        }
        for (Value possibleValue : possibleValues) {
            if (possibleValue.code.equalsIgnoreCase((String)v)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumMetadata createMetadata(Object value) {
        if (!isValidValue(value)) {
            throw new IllegalArgumentException("");
        }
        return new EnumMetadata(name, (String) value);
    }

    @Override
    public void init() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            possibleValues = objectMapper.readValue(valueJson, new TypeReference<List<Value>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static class Value {
        public String code;
        public String name;
    }
}
