package com.example.hbnt;

import com.example.hbnt.model.MetadataTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.jpa.internal.util.PersistenceUtilHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.transaction.TransactionManager;


/**
 * @author tao.lin
 * @date 2021/1/31
 */
@RestController
@Log
@AllArgsConstructor
public class HelloController {

    private final EntityManagerFactory entityManagerFactory;

    private final HelloService helloService;


    @RequestMapping(value = "/batch")
    public String test() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            for (int i = 0; i < 3; i++) {
                entityManager.persist(new MetadataTemplate(null, String.valueOf(System.currentTimeMillis() / 1000), String.valueOf(System.currentTimeMillis() / 10000)));
            }
            transaction.commit();
        } finally {
            entityManager.close();
        }
        return "OK";
    }


    @RequestMapping(value = "/update")
    public String update() {
        helloService.update();
        return "OK";
    }


    @RequestMapping(value = "/test")
    public String hello(@RequestParam(name = "name") String name,
                        @RequestParam(name = "value") String value) {
        MetadataTemplate template = new MetadataTemplate(null, name, value);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(template);
            transaction.commit();
        } finally {
            entityManager.close();
        }
        return template.toString();
    }
}
