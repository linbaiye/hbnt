package com.example.hbnt.model;

import com.example.hbnt.model.command.CreateProduct;
import com.example.hbnt.model.metadata.Metadata;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@Getter
@Builder
public class ProductTemplate {

    private Integer id;

    private String name;

    private String serviceLine;

    private String description;

    private String productType;

    private List<MetadataTemplateGroup> commonGroup;

    private List<MetadataTemplateGroup> privateGroup;

    private Metadata<?> createMetadata(Map.Entry<String, Object> kv) {
        for (MetadataTemplateGroup group : commonGroup) {
            if (group.canCreateMetadata(kv.getKey())) {
                return group.createMetadata(kv);
            }
        }
        for (MetadataTemplateGroup group : privateGroup) {
            if (group.canCreateMetadata(kv.getKey())) {
                return group.createMetadata(kv);
            }
        }
        throw new IllegalArgumentException();
    }

    public List<Product> create(CreateProduct command) {
        Map<String, Object> json = command.parseDetail();
        Product product = new Product();
        return null;
    }

}
