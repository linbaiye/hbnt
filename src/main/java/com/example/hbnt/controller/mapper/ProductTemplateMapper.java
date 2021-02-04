package com.example.hbnt.controller.mapper;

import com.example.hbnt.controller.dto.ProductTemplateRespDTO;
import com.example.hbnt.model.MetadataTemplateGroup;
import com.example.hbnt.model.ProductTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tao.lin
 * @date 2021/2/4
 */
@Component
public class ProductTemplateMapper {


    private List<ProductTemplateRespDTO.MetadataTemplateGroupDTO> convert(List<MetadataTemplateGroup> groupList) {
        Collections.sort(groupList);
        return groupList.stream().map(e ->
                ProductTemplateRespDTO.MetadataTemplateGroupDTO.builder()
                .groupName(e.getName())
                .metadataList(e.getSortedMetadataTemplateList())
                .build()
        ).collect(Collectors.toList());
    }

    public ProductTemplateRespDTO convert(ProductTemplate productTemplate) {
        return ProductTemplateRespDTO.builder()
                .description(productTemplate.getDescription())
                .commonConfigList(convert(productTemplate.getCommonGroup()))
                .privateConfigList(convert(productTemplate.getPrivateGroup()))
                .name(productTemplate.getName())
                .productType(productTemplate.getProductType())
                .id(productTemplate.getId())
                .serviceLine(productTemplate.getServiceLine())
                .build();
    }

}
