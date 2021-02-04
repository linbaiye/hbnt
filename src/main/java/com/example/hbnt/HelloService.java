package com.example.hbnt;

import com.example.hbnt.model.metadatatemplate.MetadataTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@Service
@AllArgsConstructor
public class HelloService {

    @PersistenceContext
    private EntityManager entityManager;

//    private final MetadataTemplateDao metadataTemplateDao;

    @Transactional(rollbackFor = Exception.class)
    public void update() {
        List<MetadataTemplate<?>> metadataTemplates = new LinkedList<>();
        Collections.sort(metadataTemplates);
//        MetadataTemplate template = metadataTemplateDao.findById(1L).orElseThrow(RuntimeException::new);
    }

}
