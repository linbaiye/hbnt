package com.example.hbnt.model.metadatatemplate;


import com.example.hbnt.respository.ProductTemplateRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
public abstract class RangeMetadataTemplate<T extends Comparable<T>> extends MetadataTemplate<T> {

    private static final Comparable<?> INFINITE = null;

    private Range range;

    private static final Pattern DECIMAL_RANGE_PATTERN = Pattern.compile("^([(\\[])(-|-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?|\\+)([])])$");

    public RangeMetadataTemplate(Long id, Type type, String key, String name,
                                 T defaultValue, String defaultValueString, Boolean optional,
                                 Integer order, Boolean shared, String valueJson,
                                 String tag, String placeholder, String widgetType,
                                 String webCheckScript, String range) {
        super(id, type, key, name, defaultValue, defaultValueString, optional, order,
                shared, valueJson, tag, placeholder, widgetType, webCheckScript, range);
    }

    abstract T createValue(String str);

    public void parseRange() {
        if (!StringUtils.hasText(super.range)) {
            range = new Range(null, false, null, false);
        }
        String exp = super.range.replaceAll("\\s+", "");
        Matcher matcher = DECIMAL_RANGE_PATTERN.matcher(exp);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("bad exp " + super.range);
        }
        boolean lowerOpen = "(".equals(matcher.group(1));
        String lowerValueStr = matcher.group(2);
        T lowerValue = "-".equals(lowerValueStr) ? null : createValue(lowerValueStr);
        String upperValueStr = matcher.group(4);
        T upperValue = "+".equals(upperValueStr) ? null : createValue(upperValueStr);
        boolean upperOpen = ")".equals(matcher.group(6));
        range = new Range(upperValue, upperOpen, lowerValue, lowerOpen);
    }

    @Override
    boolean isValidValue(T value) {
        return range.isWithin(value);
    }

    @AllArgsConstructor
    private class Range {

        private T upperBoundary;

        private boolean upperOpen;

        private T lowerBoundary;

        private boolean lowerOpen;

        private boolean isWithin(T value) {
            boolean upperOk;
            if (upperBoundary == INFINITE) {
                upperOk = true;
            } else {
                upperOk = upperOpen ? upperBoundary.compareTo(value) > 0 : upperBoundary.compareTo(value) >= 0;
            }
            boolean lowerOk;
            if (lowerBoundary == INFINITE) {
                lowerOk = true;
            } else {
                lowerOk = lowerOpen ? lowerBoundary.compareTo(value) < 0 : lowerBoundary.compareTo(value) <= 0;
            }
            return lowerOk && upperOk;
        }
    }
}
