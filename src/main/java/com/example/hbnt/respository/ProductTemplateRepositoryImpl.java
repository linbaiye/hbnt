package com.example.hbnt.respository;

import com.example.hbnt.dao.PsProductMetadataDao;
import com.example.hbnt.dao.PsProductTemplateDao;
import com.example.hbnt.dataobject.PsProductMetadata;
import com.example.hbnt.dataobject.PsProductTemplate;
import com.example.hbnt.model.MetadataTemplateGroup;
import com.example.hbnt.model.ProductTemplate;
import com.example.hbnt.model.ProductTemplateRepository;
import com.example.hbnt.model.metadatatemplate.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tao.lin
 * @date 2021/2/3
 */
@Slf4j
@Repository
@AllArgsConstructor
public class ProductTemplateRepositoryImpl implements ProductTemplateRepository {

    private final PsProductMetadataDao psProductMetadataDao;

    private final PsProductTemplateDao psProductTemplateDao;

    private static final Pattern DECIMAL_RANGE_PATTERN = Pattern.compile("^([(\\[])(-|-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?|\\+)([])])$");

    @AllArgsConstructor
    private static class RangeMetadataStructure<T> {
        private T upper;
        private T lower;
        private boolean upperOpen;
        private boolean lowerOpen;
    }

    @FunctionalInterface
    private interface NumberFactory<T extends Comparable<T>> {
        T create(String str);
    }

    private <T extends Comparable<T>> RangeMetadataStructure<T> parseRangeExpression(
            NumberFactory<T> factory, String rangeExp, Pattern rangePattern) {
        if (!StringUtils.hasText(rangeExp)) {
            return new RangeMetadataStructure<>(null,null, false, false);
        }
        String exp = rangeExp.replaceAll("\\s+", "");
        Matcher matcher = rangePattern.matcher(exp);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("bad exp " + rangeExp);
        }
        boolean lowerOpen = "(".equals(matcher.group(1));
        String lowerValueStr = matcher.group(2);
        T lowerValue = "-".equals(lowerValueStr) ? null : factory.create(lowerValueStr);
        String upperValueStr = matcher.group(4);
        T upperValue = "+".equals(upperValueStr) ? null : factory.create(upperValueStr);
        boolean upperOpen = ")".equals(matcher.group(6));
        return new RangeMetadataStructure<>(upperValue, lowerValue, upperOpen, lowerOpen);
    }

    private IntegerMetadataTemplate ofInteger(PsProductMetadata metadata, boolean optional) {
        RangeMetadataStructure<Integer> struct = parseRangeExpression(Integer::valueOf, metadata.getRange(), DECIMAL_RANGE_PATTERN);
        return IntegerMetadataTemplate.builder()
                .defaultValueString(metadata.getDefaultValue())
                .lowerBoundary(struct.lower)
                .lowerOpen(struct.lowerOpen)
                .upperBoundary(struct.upper)
                .upperOpen(struct.upperOpen)
                .id(metadata.getId())
                .key(metadata.getKey())
                .name(metadata.getName())
                .defaultValue(metadata.getDefaultValue() != null && StringUtils.hasText(metadata.getDefaultValue()) ? Integer.valueOf(metadata.getDefaultValue()) : null)
                .order(metadata.getSort())
                .shared(false)
                .optional(optional)
                .build();
    }

    private DecimalMetadataTemplate ofDecimal(PsProductMetadata metadata, boolean optional) {
        RangeMetadataStructure<BigDecimal> struct = parseRangeExpression(BigDecimal::new, metadata.getRange(), DECIMAL_RANGE_PATTERN);
        return DecimalMetadataTemplate.builder()
                .defaultValueString(metadata.getDefaultValue())
                .lowerBoundary(struct.lower)
                .lowerOpen(struct.lowerOpen)
                .upperBoundary(struct.upper)
                .upperOpen(struct.upperOpen)
                .id(metadata.getId())
                .key(metadata.getKey())
                .name(metadata.getName())
                .defaultValue(metadata.getDefaultValue() != null && StringUtils.hasText(metadata.getDefaultValue()) ? new BigDecimal(metadata.getDefaultValue()) : null)
                .order(metadata.getSort())
                .shared(false)
                .optional(optional)
                .build();
    }

    private StringMetadataTemplate ofString(PsProductMetadata metadata, boolean optional) {
        return StringMetadataTemplate.builder()
                .id(metadata.getId())
                .defaultValue(metadata.getDefaultValue())
                .defaultValueString(metadata.getDefaultValue())
                .optional(optional)
                .name(metadata.getName())
                .order(metadata.getSort())
                .key(metadata.getKey())
                .shared(false).build();
    }

    private EnumMetadataTemplate ofEnum(PsProductMetadata metadata, boolean optional) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return EnumMetadataTemplate.builder()
                    .defaultValue(metadata.getDefaultValue())
                    .id(metadata.getId())
                    .key(metadata.getKey())
                    .name(metadata.getName())
                    .possibleValues(objectMapper.readValue(metadata.getValueJson(),
                            new TypeReference<List<EnumMetadataTemplate.Value>>() {
                            }))
                    .defaultValueString(metadata.getDefaultValue())
                    .optional(optional)
                    .shared(false)
                    .order(metadata.getSort())
                    .build();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private MetadataTemplate<?> create(PsProductMetadata metadata, boolean optional) {
        if (Type.INTEGER.name().equalsIgnoreCase(metadata.getType())) {
            return ofInteger(metadata, optional);
        } else if (Type.DECIMAL.name().equalsIgnoreCase(metadata.getType())) {
            return ofDecimal(metadata, optional);
        } else if (Type.STRING.name().equalsIgnoreCase(metadata.getType())) {
            return ofString(metadata, optional);
        } else if (Type.ENUM.name().equalsIgnoreCase(metadata.getType()) ||
                Type.BOOL.name().equalsIgnoreCase(metadata.getType())) {
            return ofEnum(metadata, optional);
        }
        throw new IllegalArgumentException("");
    }


    private List<MetadataTemplateGroup> parseMetadataTemplateGroup(Map<String, Boolean> keys,
                                                                   Set<PsProductMetadata> metadataSet) {
        Map<String, MetadataTemplateGroup> namedGroups = new HashMap<>(8);
        for (PsProductMetadata psProductMetadata : metadataSet) {
            if (!namedGroups.containsKey(psProductMetadata.getGroup())) {
                namedGroups.put(psProductMetadata.getGroup(), new MetadataTemplateGroup(psProductMetadata.getGroup(), psProductMetadata.getGroupSort()));
            }
            MetadataTemplateGroup group = namedGroups.get(psProductMetadata.getGroup());
            MetadataTemplate<?> template = create(psProductMetadata, keys.getOrDefault(psProductMetadata.getKey(), false));
            template.setValueJson(psProductMetadata.getValueJson());
            template.setPlaceholder(psProductMetadata.getPlaceholder());
            template.setTag(psProductMetadata.getTag());
            template.setWidgetType(psProductMetadata.getWidgetType());
            group.addTemplate(template);
        }
        return new ArrayList<>(namedGroups.values());

    }


    @Override
    public ProductTemplate findById(int id) {
        PsProductTemplate psProductTemplate = psProductTemplateDao.findById(id).orElseThrow(IllegalArgumentException::new);

        Map<String, Boolean> commonKeys = psProductTemplate.keys(true);
        Set<PsProductMetadata> commonMetadata = psProductMetadataDao.findByKeyIn(commonKeys.keySet());

        Map<String, Boolean> privateKeys = psProductTemplate.keys(false);
        Set<PsProductMetadata> privateMetadata = psProductMetadataDao.findByKeyIn(privateKeys.keySet());

        return ProductTemplate
                .builder().description(psProductTemplate.getDescription())
                .commonGroup(parseMetadataTemplateGroup(commonKeys, commonMetadata))
                .privateGroup(parseMetadataTemplateGroup(privateKeys, privateMetadata))
                .name(psProductTemplate.getName())
                .productType(psProductTemplate.getProductType())
                .serviceLine(psProductTemplate.getServiceLine())
                .id(psProductTemplate.getId())
                .build();
    }
}
