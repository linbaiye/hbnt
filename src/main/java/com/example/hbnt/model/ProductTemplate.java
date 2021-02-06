package com.example.hbnt.model;

import com.example.hbnt.model.command.CreateProduct;
import com.example.hbnt.model.metadata.Metadata;
import com.example.hbnt.model.product.Product;
import com.example.hbnt.model.product.ProductValidator;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

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

    private Optional<Metadata<?>> createMetadata(Map.Entry<String, Object> kv,
                                                 List<MetadataTemplateGroup> groupList) {
        for (MetadataTemplateGroup group : groupList) {
            if (group.canCreateMetadata(kv.getKey())) {
                return Optional.of(group.createMetadata(kv));
            }
        }
        return Optional.empty();
    }

    private List<Metadata<?>> createMetadataList(Map<String, Object> command) {
        List<Metadata<?>> metadataList = new LinkedList<>();
        for (Map.Entry<String, Object> stringObjectEntry : command.entrySet()) {
            Optional<Metadata<?>> optionalMetadata = createMetadata(stringObjectEntry, commonGroup);
            if (!optionalMetadata.isPresent()) {
                optionalMetadata = createMetadata(stringObjectEntry, privateGroup);
            }
            if (!optionalMetadata.isPresent()) {
                throw new IllegalArgumentException("Bad metadata " + stringObjectEntry.getKey());
            }
            metadataList.add(optionalMetadata.get());
        }
        return metadataList;
    }

    public List<Product> create(CreateProduct command, ProductValidator validator) {
        List<Product> productList = new LinkedList<>();
        List<Metadata<?>> commonDetail = createMetadataList(command.parseDetail());
        List<CreateProduct.Item> itemArray = command.parseItemArray();
        for (CreateProduct.Item item : itemArray) {
            Product product = Product.builder()
                    .itemName(item.getItemName())
                    .apr(command.getApr())
                    .productType(productType)
                    .invalidTime(item.getInvalidTime())
                    .validTime(item.getValidTime())
                    .amountLowerLimit(item.getAmountLowerLimit())
                    .amountUpperLimit(item.getAmountUpperLimit())
                    .serviceLine(serviceLine)
                    .description(command.getDescription())
                    .commonDetail(commonDetail)
                    .privateDetail(createMetadataList(item.getDetailJson())).build();
            List<String> errors = validator.validate(product);
            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(String.join(",", errors));
            }
            productList.add(product);
        }
        return productList;
    }

}
