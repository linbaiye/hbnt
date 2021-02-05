package com.example.hbnt.model;

import com.example.hbnt.model.metadata.ListMetadata;
import com.example.hbnt.model.metadata.Metadata;
import com.example.hbnt.model.metadatatemplate.MetadataTemplate;
import com.example.hbnt.model.metadatatemplate.MetadataTemplateVisitor;
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

    private Map<String, MetadataTemplate<?>> templateMap;

    public MetadataTemplateGroup(String name,
                                 Integer order) {
        this.name = name;
        this.order = order;
        this.templateMap = new HashMap<>();
    }

    public void addTemplate(MetadataTemplate<?> template) {
        templateMap.put(template.getKey(), template);
    }

    @Override
    public int compareTo(MetadataTemplateGroup o) {
        return order.compareTo(o.order);
    }

    public <T extends MetadataTemplate<?>> T find(String name, Class<T> token) {
        MetadataTemplate<?> metadataTemplate = templateMap.get(name);
        if (token.isAssignableFrom(metadataTemplate.getClass())) {
            return token.cast(metadataTemplate);
        }
        return null;
    }

    public boolean canCreateMetadata(String key) {
        return name.equalsIgnoreCase(key) || templateMap.containsKey(key);
    }

    public Metadata<?> createMetadata(Map.Entry<String, Object> kv) {
        if (!canCreateMetadata(kv.getKey())) {
            throw new IllegalArgumentException("");
        }
        if (templateMap.containsKey(kv.getKey())) {
            return templateMap.get(kv.getKey()).createMetadata(kv.getValue());
        }
        ListMetadata listMetadata = new ListMetadata(kv.getKey(), new LinkedList<>());
//        List<Map<String, Object>> values = kv.getValue();
        return listMetadata;
    }

    public List<MetadataTemplate<?>> getSortedMetadataTemplateList() {
        List<MetadataTemplate<?>> metadataTemplateList = new ArrayList<>(templateMap.values());
        Collections.sort(metadataTemplateList);
        return metadataTemplateList;
    }

    public void visitTemplates(MetadataTemplateVisitor visitor) {
        for (MetadataTemplate<?> value : templateMap.values()) {
            value.accept(visitor);
        }
    }
}
