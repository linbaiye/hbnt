package com.example.hbnt.service;

import com.example.hbnt.model.product.Product;
import com.example.hbnt.model.ProductTemplate;
import com.example.hbnt.model.ProductTemplateRepository;
import com.example.hbnt.model.command.CreateProduct;
import com.example.hbnt.model.product.ProductValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductTemplateRepository productTemplateRepository;

    public List<Product> createProducts(CreateProduct command) {
        ProductTemplate productTemplate = productTemplateRepository.findById(command.getTemplateId());
        return productTemplate.create(command, ProductValidator.ofType(command.getProductType()));
    }

}
