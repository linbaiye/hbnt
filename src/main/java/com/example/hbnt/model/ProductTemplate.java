package com.example.hbnt.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

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

}
