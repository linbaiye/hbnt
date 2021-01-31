package com.example.hbnt.model;

import java.util.Map;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
public class MetadataTemplateGroup implements Comparable<MetadataTemplateGroup> {

    private String name;

    private Integer order;

    private Map<String, MetadataTemplateGroupItem> items;

    @Override
    public int compareTo(MetadataTemplateGroup o) {
        return order.compareTo(o.order);
    }
}
