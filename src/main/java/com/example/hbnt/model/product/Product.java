package com.example.hbnt.model.product;

import com.example.hbnt.model.metadata.Metadata;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tao.lin
 * @date 2021/2/5
 */
@Builder
@Getter
public class Product {
    private Integer id;

    private String serviceLine;

    private String description;

    private String productType;

    private String itemName;

    private Date validTime;

    private Date invalidTime;

    private BigDecimal amountLowerLimit;

    private BigDecimal amountUpperLimit;

    private BigDecimal apr;

    private List<Metadata<?>> commonDetail;

    private List<Metadata<?>> privateDetail;

}
