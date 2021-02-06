package com.example.hbnt.model.product;

import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/6
 */
public interface ProductValidator {
    /**
     * 验证产品是否符合业务需求.
     * @param product
     * @return 错误信息。
     */
    List<String> validate(Product product);

    static ProductValidator ofType(String v) {
        if ("BILL".equalsIgnoreCase(v)) {
            return new BillProductValidator();
        } else if ("ORDER".equalsIgnoreCase(v)) {
            return new OrderProductValidator();
        }
        throw new IllegalArgumentException("invalid type: " + v);
    }
}
