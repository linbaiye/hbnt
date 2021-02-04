package com.example.hbnt.controller.dto;

import com.example.hbnt.model.metadatatemplate.MetadataTemplate;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
@Builder
@Data
public class ProductTemplateRespDTO {
    private Integer id;

    private String name;

    private String serviceLine;

    private String description;

    private String productType;

    private List<MetadataTemplateGroupDTO> commonConfigList;

    private List<MetadataTemplateGroupDTO> privateConfigList;

    @Builder
    @Data
    public static class MetadataTemplateGroupDTO {

        private String groupName;

        private List<MetadataTemplate<?>> metadataList;
    }
}
