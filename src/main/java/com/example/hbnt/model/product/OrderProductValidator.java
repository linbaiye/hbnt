package com.example.hbnt.model.product;

import java.util.Collections;
import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/6
 */
public class OrderProductValidator implements ProductValidator {

    @Override
    public List<String> validate(Product product) {
        return Collections.emptyList();
    }
}
