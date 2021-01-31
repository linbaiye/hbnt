package com.example.hbnt.model;

import lombok.AllArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
public class MetadataTemplateRepositoryImpl implements MetadataTemplateRepository {

    private static final Pattern DECIMAL_RANGE_PATTERN = Pattern.compile("^([(\\[])(-|-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?|\\+)([])])$");

    @FunctionalInterface
    private interface NumberFactory<T extends Comparable<T>> {
        T create(String str);
    }

    private <T extends Comparable<T>> RangeMetadataStructure<T> parseRangeExpression(
            NumberFactory<T> factory, String rangeExp, Pattern rangePattern) {
        String exp = rangeExp.replaceAll("\\s+", "");
        Matcher matcher = rangePattern.matcher(exp);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        boolean lowerOpen = "(".equals(matcher.group(1));
        String lowerValueStr = matcher.group(2);
        T lowerValue = "-".equals(lowerValueStr) ? null : factory.create(lowerValueStr);
        String upperValueStr = matcher.group(4);
        T upperValue = "+".equals(upperValueStr) ? null : factory.create(upperValueStr);
        Boolean upperOpen = ")".equals(matcher.group(6));
        return new RangeMetadataStructure<>(upperValue, lowerValue, upperOpen, lowerOpen);
    }

//    private EnumMetadataDefinition createEnum(PsProductMetadata metadata) {
//        List<EnumMetadata.CodeName> list = JSONObject.parseArray(metadata.getValueJson(),
//                EnumMetadata.CodeName.class);
//        return new EnumMetadataDefinition(metadata.getName(), metadata.getKey(), list);
//    }

    public  MetadataTemplate<?> create() {
//        if ("decimal".equals(metadata.getType())) {
//            RangeMetadataStructure<BigDecimal> struct = parseRangeExpression(str -> new BigDecimal(str), metadata.getRange(), DECIMAL_RANGE_PATTERN);
////            return new DecimalMetadataDefinition(metadata.getKey(), metadata.getName(), struct.upper, struct.upperOpen, struct.lower, struct.lowerOpen);
//        } else if ("integer".equals(metadata.getType())) {
//            RangeMetadataStructure<Integer> struct =  parseRangeExpression(str -> Integer.valueOf(str), metadata.getRange(), DECIMAL_RANGE_PATTERN);
////            return new IntegerMetadataDefinition(metadata.getKey(), metadata.getName(), struct.upper, struct.upperOpen, struct.lower, struct.lowerOpen);
//        } else if ("enum".equals(metadata.getType())) {
//        }
//        return null;
        return null;
    }

    @AllArgsConstructor
    private class RangeMetadataStructure<T> {
        private T upper;
        private T lower;
        private boolean upperOpen;
        private boolean lowerOpen;
    }



    @Override
    public MetadataTemplate<?> findById(Long id) {
        return null;
    }

}
