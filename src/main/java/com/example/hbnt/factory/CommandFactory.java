package com.example.hbnt.factory;

import com.example.hbnt.model.command.CreateProduct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author tao.lin
 * @date 2021/2/6
 */
@Component
public class CommandFactory {

    public CreateProduct createCommand() {
        return CreateProduct.builder()
                .apr(new BigDecimal("0.12"))
                .description("test")
                .detailJson("{\"interestStartDays\":1,\"penaltyStartDays\":1,\"badStartDays\":1,\"penaltyMethod\":\"1\",\"penaltyRate\":0.01}")
                .itemsJsonArray("[{\"validTime\":\"2021-02-06 00:00:00\",\"invalidTime\":\"2031-02-05 11:24:27\",\"detailJson\":{\"term\":\"1\",\"repayMethod\":\"DAILY_EQUAL_PRINCIPAL_INTEREST\",\"installmentCycle\":\"MONTH\",\"cycle\":\"1\",\"fixedRepayDayType\":\"MONTH\",\"fixedDays\":\"1\",\"prerepayAllow\":\"1\",\"payoffAllow\":\"1\",\"prerepayIntervalWithPlanDay\":\"0\",\"prerepayLeastLoanDays\":\"1\",\"prerepayInterestType\":\"UNPAID_PRINCIPAL_RATE\",\"payoffMinTerm\":\"1\",\"payoffLeastLoanDays\":\"1\",\"payoffRangeList\":[{\"payoffMinDay\":1,\"payoffMaxDay\":12,\"payoffRepayFeeType\":\"NO\",\"payoffRepayFeeDivideType\":\"CURRENT\",\"payoffRepayFeeRate\":0.01,\"payoffInterestType\":\"UNPAID_PRINCIPAL_RATE\",\"payoffInterestDivideType\":\"CURRENT\",\"payoffInterestRate\":0.01},{\"payoffMinDay\":13,\"payoffMaxDay\":14,\"payoffRepayFeeType\":\"NO\",\"payoffRepayFeeDivideType\":\"CURRENT\",\"payoffRepayFeeRate\":0.01,\"payoffInterestType\":\"UNPAID_PRINCIPAL_RATE\",\"payoffInterestDivideType\":\"CURRENT\",\"payoffInterestRate\":0.01}]},\"itemName\":\"qwe\",\"amountLowerLimit\":1,\"amountUpperLimit\":500},{\"validTime\":\"2021-02-06 00:00:00\",\"invalidTime\":\"2031-02-05 11:24:27\",\"detailJson\":{\"term\":\"1\",\"repayMethod\":\"DAILY_EQUAL_PRINCIPAL_INTEREST\",\"installmentCycle\":\"MONTH\",\"cycle\":\"1\",\"fixedRepayDayType\":\"MONTH\",\"fixedDays\":\"1\",\"prerepayAllow\":\"0\",\"payoffAllow\":\"0\"},\"itemName\":\"ffw\",\"amountLowerLimit\":1,\"amountUpperLimit\":10000}]")
                .productType("ORDER")
                .serviceLine("CASH")
                .templateId(2)
                .name("CASH TEMPLATE")
                .build();
    }
}
