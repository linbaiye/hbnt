package com.example.hbnt.respository;

import com.example.hbnt.dao.PsProductMetadataDao;
import com.example.hbnt.dao.PsProductTemplateDao;
import com.example.hbnt.dataobject.PsProductMetadata;
import com.example.hbnt.dataobject.PsProductTemplate;
import com.example.hbnt.model.MetadataTemplateGroup;
import com.example.hbnt.model.ProductTemplate;
import com.example.hbnt.model.ProductTemplateRepository;
import com.example.hbnt.model.metadatatemplate.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    private MetadataTemplate<?> create(PsProductMetadata metadata, boolean optional) {
        MetadataTemplate<?> template;
        if (Type.INTEGER.name().equalsIgnoreCase(metadata.getType())) {
            template = new IntegerMetadataTemplate();
        } else if (Type.DECIMAL.name().equalsIgnoreCase(metadata.getType())) {
            template = new DecimalMetadataTemplate();
        } else if (Type.STRING.name().equalsIgnoreCase(metadata.getType())) {
            template = new StringMetadataTemplate();
        } else if (Type.ENUM.name().equalsIgnoreCase(metadata.getType())) {
            template = new EnumMetadataTemplate();
        } else if (Type.BOOL.name().equalsIgnoreCase(metadata.getType())) {
            template = new BoolMetadataTemplate();
        } else {
            throw new IllegalArgumentException("");
        }
        template.setRequired(!optional);
        BeanUtils.copyProperties(metadata, template);
        template.init();
        return template;
    }


    private List<MetadataTemplateGroup> parseMetadataTemplateGroup(Map<String, Boolean> keys,
                                                                   Set<PsProductMetadata> metadataSet) {
        Map<String, MetadataTemplateGroup> namedGroups = new HashMap<>(8);
        for (PsProductMetadata psProductMetadata : metadataSet) {
            if (!namedGroups.containsKey(psProductMetadata.getGroup())) {
                namedGroups.put(psProductMetadata.getGroup(), new MetadataTemplateGroup(psProductMetadata.getGroup(), psProductMetadata.getGroupSort()));
            }
            MetadataTemplateGroup group = namedGroups.get(psProductMetadata.getGroup());
            group.addTemplate(create(psProductMetadata, keys.getOrDefault(psProductMetadata.getKey(), false)));
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
