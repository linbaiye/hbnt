package com.example.hbnt.model;

import com.example.hbnt.model.metadata.ListMetadata;
import com.example.hbnt.model.metadata.Metadata;
import com.example.hbnt.model.metadatatemplate.AbstractMetadataTemplate;
import lombok.Getter;

import java.util.*;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@Getter
public class MetadataTemplateGroup implements Comparable<MetadataTemplateGroup> {

    private String name;

    private Integer order;

    private Map<String, AbstractMetadataTemplate<?>> templateMap;

    public MetadataTemplateGroup(String name,
                                 Integer order) {
        this.name = name;
        this.order = order;
        this.templateMap = new HashMap<>();
    }

    public void addTemplate(AbstractMetadataTemplate<?> template) {
        templateMap.put(template.getKey(), template);
    }

    @Override
    public int compareTo(MetadataTemplateGroup o) {
        return order.compareTo(o.order);
    }

    public boolean canCreateMetadata(String key) {
        return name.equalsIgnoreCase(key) || templateMap.containsKey(key);
    }

    private ListMetadata createPayoffRangeList(Map.Entry<String, Object> kv) {
        ListMetadata listMetadata = new ListMetadata(kv.getKey());
        @SuppressWarnings({"unchecked"})
        List<Map<String, Object>> values = (List) kv.getValue();
        for (Map<String, Object> value : values) {
            List<Metadata<?>> metadataList = new LinkedList<>();
            for (String s : value.keySet()) {
                metadataList.add(templateMap.get(s).createMetadata(value.get(s)));
            }
            listMetadata.add(metadataList);
        }
        return listMetadata;
    }

    public Metadata<?> createMetadata(Map.Entry<String, Object> kv) {
        if (!canCreateMetadata(kv.getKey())) {
            throw new IllegalArgumentException("");
        }
        if ("payoffRangeList".equalsIgnoreCase(kv.getKey())) {
            return createPayoffRangeList(kv);
        }
        return templateMap.get(kv.getKey()).createMetadata(kv.getValue());
    }

    public List<AbstractMetadataTemplate<?>> getSortedMetadataTemplateList() {
        List<AbstractMetadataTemplate<?>> metadataTemplateList = new ArrayList<>(templateMap.values());
        Collections.sort(metadataTemplateList);
        return metadataTemplateList;
    }
}
