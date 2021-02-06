package com.example.hbnt.model.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建template.
 * @author tao.lin
 * @date 2021/2/3
 */
@Builder
@Getter
public class CreateProduct {


    private String name;

    private String productType;

    private String serviceLine;

    private Integer templateId;

    private String description;

    /*
    serviceLine: CASH
productType: ORDER
templateId: 9
description: asd
name: asd
apr: 0.12
     */

    private BigDecimal apr;

    /**
     * {"interestStartDays":1,"penaltyStartDays":1,"badStartDays":1,"penaltyMethod":"1","penaltyRate":0.01}
     */
    private String detailJson;

    /**
     * 产品配置方案的JSONArray.
     */
    private String itemsJsonArray;

    /*
    [{"validTime":"2021-02-06 00:00:00","invalidTime":"2031-02-05 11:24:27","detailJson":{"term":"1","repayMethod":"DAILY_EQUAL_PRINCIPAL_INTEREST","installmentCycle":"MONTH","cycle":"1","fixedRepayDayType":"MONTH","fixedDays":"1","prerepayAllow":"1","payoffAllow":"1","prerepayIntervalWithPlanDay":"0","prerepayLeastLoanDays":"1","prerepayInterestType":"UNPAID_PRINCIPAL_RATE","payoffMinTerm":"1","payoffLeastLoanDays":"1","payoffRangeList":[{"payoffMinDay":1,"payoffMaxDay":12,"payoffRepayFeeType":"NO","payoffRepayFeeDivideType":"CURRENT","payoffRepayFeeRate":0.01,"payoffInterestType":"UNPAID_PRINCIPAL_RATE","payoffInterestDivideType":"CURRENT","payoffInterestRate":0.01},{"payoffMinDay":13,"payoffMaxDay":14,"payoffRepayFeeType":"NO","payoffRepayFeeDivideType":"CURRENT","payoffRepayFeeRate":0.01,"payoffInterestType":"UNPAID_PRINCIPAL_RATE","payoffInterestDivideType":"CURRENT","payoffInterestRate":0.01}]},"itemName":"qwe","amountLowerLimit":1,"amountUpperLimit":500},{"validTime":"2021-02-06 00:00:00","invalidTime":"2031-02-05 11:24:27","detailJson":{"term":"1","repayMethod":"DAILY_EQUAL_PRINCIPAL_INTEREST","installmentCycle":"MONTH","cycle":"1","fixedRepayDayType":"MONTH","fixedDays":"1","prerepayAllow":"0","payoffAllow":"0"},"itemName":"ffw","amountLowerLimit":1,"amountUpperLimit":10000}]
     */


    @Data
    public static class Item {

        /**
         * 配置项名称，如3期按月计息
         */
        private String itemName;

        /**
         * 生效时间（用于产品筛选）
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date validTime;

        /**
         * 失效时间（用于产品筛选）
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date invalidTime;

        /**
         * 贷款金额下限（用于产品筛选）
         */
        private BigDecimal amountLowerLimit;

        /**
         * 贷款金额上限（用于产品筛选）
         */
        private BigDecimal amountUpperLimit;

        /**
         * 配置项详情（JSON）
         *
         */
        private Map<String, Object> detailJson;

    }


    public Map<String, Object> parseDetail() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(detailJson, HashMap.class);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Item> parseItemArray() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(itemsJsonArray, new TypeReference<List<Item>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
