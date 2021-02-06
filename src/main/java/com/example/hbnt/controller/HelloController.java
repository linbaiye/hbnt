package com.example.hbnt.controller;

import com.example.hbnt.controller.dto.ProductTemplateRespDTO;
import com.example.hbnt.controller.mapper.ProductTemplateMapper;
import com.example.hbnt.factory.CommandFactory;
import com.example.hbnt.model.product.Product;
import com.example.hbnt.model.ProductTemplateRepository;
import com.example.hbnt.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@RestController
@Log
@AllArgsConstructor
public class HelloController {

    private final ProductService productService;

    private final ProductTemplateRepository productTemplateRepository;

    private final ProductTemplateMapper productTemplateMapper;

    private final CommandFactory commandFactory;

    @RequestMapping(value = "/find")
    public ProductTemplateRespDTO find() {
        return productTemplateMapper.convert(productTemplateRepository.findById(2));
    }

    @RequestMapping(value = "/create")
    public List<Product> create() {
        return productService.createProducts(commandFactory.createCommand());
    }

}
