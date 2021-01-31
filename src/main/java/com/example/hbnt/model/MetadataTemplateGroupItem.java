package com.example.hbnt.model;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
public class MetadataTemplateGroupItem implements Comparable<MetadataTemplateGroupItem> {

    private Boolean optional;

    private MetadataTemplate<?> metadataTemplate;

    private Integer order;

    @Override
    public int compareTo(MetadataTemplateGroupItem o) {
        return order.compareTo(o.order);
    }
}
