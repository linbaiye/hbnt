package com.example.hbnt.controller;

import com.example.hbnt.service.HelloService;
import com.example.hbnt.controller.dto.ProductTemplateRespDTO;
import com.example.hbnt.controller.mapper.ProductTemplateMapper;
import com.example.hbnt.dao.PsProductMetadataDao;
import com.example.hbnt.model.ProductTemplateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author tao.lin
 * @date 2021/1/31
 */
@RestController
@Log
@AllArgsConstructor
public class HelloController {

//    private final EntityManagerFactory entityManagerFactory;

    private final HelloService helloService;

    private final PsProductMetadataDao metadataDao;

    private final ProductTemplateRepository productTemplateRepository;

    private final ProductTemplateMapper productTemplateMapper;


//    @RequestMapping(value = "/batch")
//    public String test() {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        try {
//            EntityTransaction transaction = entityManager.getTransaction();
//            transaction.begin();
//            for (int i = 0; i < 3; i++) {
//            }
//            transaction.commit();
//        } finally {
//            entityManager.close();
//        }
//        return "OK";
//    }


    @RequestMapping(value = "/update")
    public ProductTemplateRespDTO update() {
//        metadataDao.findById(1L);
        return productTemplateMapper.convert(productTemplateRepository.findById(2));
//        helloService.update();
    }

    @RequestMapping(value = "/hello")
    public void test() {
        metadataDao.findById(2L);
    }



    @RequestMapping(value = "/test")
    public String hello(@RequestParam(name = "name") String name,
                        @RequestParam(name = "value") String value) {
//        MetadataTemplate template = new MetadataTemplate(null, name, value);
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        try {
//            EntityTransaction transaction = entityManager.getTransaction();
//            transaction.begin();
//            entityManager.persist(template);
//            transaction.commit();
//        } finally {
//            entityManager.close();
//        }
//        return template.toString();

        return "OK";
    }
}
