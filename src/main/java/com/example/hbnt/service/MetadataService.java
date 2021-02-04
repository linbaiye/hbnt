//package com.wlqq.loan.product.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.wlqq.loan.product.dao.PsProductMetadataDao;
//import com.wlqq.loan.product.model.PsProductMetadata;
//import com.wlqq.loan.product.service.ProductMetadataService;
//import lombok.*;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class ProductMetadataServiceImpl implements ProductMetadataService {
//
//    @Autowired
//    private PsProductMetadataDao dao;
//
//    @Override
//    public List<PsProductMetadata> list(List<String> keyList) {
//        return dao.list(null, keyList);
//    }
//
//    @Override
//    public List<PsProductMetadata> listKeyName(List<String> keyList) {
//        return dao.list("`key`,`name`", keyList);
//    }
//
//
//    @Override
//    public List<String> check(JSONObject object) {
//        Map<String, CheckMetadata> metaMap = buildCheckMetadataMap();
//        List<String> errorList = new ArrayList<>();
//        Set<Map.Entry<String, CheckMetadata>> entries = metaMap.entrySet();
//        for (Map.Entry<String, CheckMetadata> entry : entries) {
//            check(entry.getValue(), object, errorList);
//        }
//        //分期参数关联校验
//        termParamRelationCheck(object, errorList);
//        //账单费参数关联校验
//        billFeeParamRelationCheck(object, errorList);
//        //服务费参数关联校验
//        feeParamRelationCheck(object, errorList);
//        //逾期参数关联校验
//        overdueParamRelationCheck(object, errorList);
//        //按期提前结清参数关联校验
//        prerepayRelationCheck(object, errorList);
//        //贷款提前结清参数关联校验
//        payoffParamRelationCheck(metaMap, object, errorList);
//        //展期参数关联校验
//        rolloverParamRelationCheck(object, errorList);
//        return errorList;
//    }
//
//    private Map<String, CheckMetadata> buildCheckMetadataMap() {
//        return dao.listAll().stream().map(metadata -> {
//            CheckMetadata checkMetadata = new CheckMetadata();
//            checkMetadata.setKey(metadata.getKey());
//            checkMetadata.setMetadata(metadata);
//            if ("enum".equals(metadata.getType()) || "bool".equals(metadata.getType())) {
//                List<EnumValue> enumValueList = JSON.parseArray(metadata.getValueJson(), EnumValue.class);
//                checkMetadata.setEnumCodeSet(enumValueList.stream().map(EnumValue::getCode).collect(Collectors.toSet()));
//                checkMetadata.setEnumMap(enumValueList.stream().collect(Collectors.toMap(EnumValue::getCode, EnumValue::getName)));
//                checkMetadata.setEnumKeyJoinString(String.join(",", checkMetadata.getEnumCodeSet()));
//            } else if ("integer".equals(metadata.getType()) || "string".equals(metadata.getType())) {
//                if (StringUtils.isNotBlank(metadata.getRange())) {
//                    String[] split = metadata.getRange().split(",");
//                    String left = split[0].trim();
//                    String right = split[1].trim();
//                    Integer minInteger = left.endsWith("-") ? null : Integer.valueOf(left.substring(1));
//                    if (left.startsWith("[")) {
//                        checkMetadata.setCloseMinInteger(minInteger);
//                    } else {
//                        checkMetadata.setOpenMinInteger(minInteger);
//                    }
//                    Integer maxInteger = right.startsWith("+") ? null : Integer.valueOf(right.substring(0, right.length() - 1));
//                    if (right.endsWith("]")) {
//                        checkMetadata.setCloseMaxInteger(maxInteger);
//                    } else {
//                        checkMetadata.setOpenMaxInteger(maxInteger);
//                    }
//                }
//            } else if ("decimal".equals(metadata.getType())) {
//                if (StringUtils.isNotBlank(metadata.getRange())) {
//                    String[] split = metadata.getRange().split(",");
//                    String left = split[0].trim();
//                    String right = split[1].trim();
//                    BigDecimal minDecimal = left.endsWith("-") ? null : new BigDecimal(left.substring(1));
//                    if (left.startsWith("[")) {
//                        checkMetadata.setCloseMinDecimal(minDecimal);
//                    } else {
//                        checkMetadata.setOpenMinDecimal(minDecimal);
//                    }
//                    BigDecimal maxDecimal = right.startsWith("+") ? null : new BigDecimal(right.substring(0, right.length() - 1));
//                    if (right.endsWith("]")) {
//                        checkMetadata.setCloseMaxDecimal(maxDecimal);
//                    } else {
//                        checkMetadata.setOpenMaxDecimal(maxDecimal);
//                    }
//                }
//            }
//            return checkMetadata;
//        }).collect(Collectors.toMap(CheckMetadata::getKey, Function.identity()));
//    }
//
//    private void rolloverParamRelationCheck(JSONObject object, List<String> errorList) {
//        if (object.containsKey("rolloverAllow")) {
//            String rolloverAllow = object.get("rolloverAllow").toString();
//            if ("1".equals(rolloverAllow) && (!object.containsKey(MetadataProperty.ROLLOVER_FEE_TYPE.getCode())
//                    || !object.containsKey("rolloverCycleType") || !object.containsKey("rolloverCycle"))) {
//                errorList.add("当是否允许展期(rolloverAllow)为允许时,展期费收取方式(rolloverFeeType),展期周期类型(rolloverCycleType),展期周期数(rolloverCycle)都不能为空.");
//            }
//            if (object.containsKey(MetadataProperty.ROLLOVER_FEE_TYPE.getCode())) {
//                String rolloverFeeType = object.get(MetadataProperty.ROLLOVER_FEE_TYPE.getCode()).toString();
//                if ("FIXED".equals(rolloverFeeType) && !object.containsKey("fixedRolloverFee")) {
//                    errorList.add("当展期费收取方式(rolloverFeeType)为FIXED时,固定展期费(fixedRolloverFee)不能为空.");
//                }
//                if ("RATE".equals(rolloverFeeType) && !object.containsKey("rolloverFeeRate")) {
//                    errorList.add("当展期费收取方式(rolloverFeeType)为FIXED时,展期费费率(rolloverFeeRate)不能为空.");
//                }
//            }
//        }
//    }
//
//    private void payoffParamRelationCheck(Map<String, CheckMetadata> metaMap, JSONObject object, List<String> errorList) {
//        Object payoffRangeList = object.get("payoffRangeList");
//        if (Objects.nonNull(payoffRangeList)) {
//            List<JSONObject> jsonList = JSON.parseArray(payoffRangeList.toString(), JSONObject.class);
//            for (JSONObject o : jsonList) {
//                check(metaMap.get(MetadataProperty.PAYOFF_MIN_DAY.getCode()), o, errorList);
//                check(metaMap.get(MetadataProperty.PAYOFF_MAX_DAY.getCode()), o, errorList);
//                check(metaMap.get(MetadataProperty.PAYOFF_REPAY_FEE_RATE.getCode()), o, errorList);
//                check(metaMap.get(MetadataProperty.PAYOFF_REPAY_FEE_TYPE.getCode()), o, errorList);
//                check(metaMap.get(MetadataProperty.PAYOFF_REPAY_FEE_DIVIDE_TYPE.getCode()), o, errorList);
//                check(metaMap.get(MetadataProperty.PAYOFF_INTEREST_TYPE.getCode()), o, errorList);
//                check(metaMap.get(MetadataProperty.PAYOFF_INTEREST_DIVIDE_TYPE.getCode()), o, errorList);
//                check(metaMap.get(MetadataProperty.PAYOFF_INTEREST_RATE.getCode()), o, errorList);
//                if (!o.containsKey(MetadataProperty.PAYOFF_MIN_DAY.getCode())
//                        || !o.containsKey(MetadataProperty.PAYOFF_MAX_DAY.getCode())) {
//                    errorList.add("当是否允许提前结清(payoffAllow)为允许时,提前结清开始区间(payoffMinDay)和结束区间(payoffMaxDay)不能为空.");
//                }
//                if (o.containsKey(MetadataProperty.PAYOFF_MIN_DAY.getCode())
//                        && o.containsKey(MetadataProperty.PAYOFF_MAX_DAY.getCode())) {
//                    Integer payoffMinDay = Integer.valueOf(o.get(MetadataProperty.PAYOFF_MIN_DAY.getCode()).toString());
//                    Integer payoffMaxDay = Integer.valueOf(o.get(MetadataProperty.PAYOFF_MAX_DAY.getCode()).toString());
//                    if (payoffMinDay > payoffMaxDay) {
//                        errorList.add("提前结清开始区间(payoffMinDay)不能超过结束区间(payoffMaxDay).");
//                    }
//                }
//                if (!o.containsKey(MetadataProperty.PAYOFF_REPAY_FEE_TYPE.getCode())) {
//                    errorList.add("当存在提前结清收费区间时,提前结清手续费收费方式(payoffRepayFeeType)不能为空.");
//                }
//                if (o.containsKey(MetadataProperty.PAYOFF_REPAY_FEE_TYPE.getCode())) {
//                    String payoffRepayFeeType = o.get(MetadataProperty.PAYOFF_REPAY_FEE_TYPE.getCode()).toString();
//                    if (!"NO".equals(payoffRepayFeeType) && (!o.containsKey(MetadataProperty.PAYOFF_REPAY_FEE_RATE.getCode())
//                            || !o.containsKey(MetadataProperty.PAYOFF_REPAY_FEE_DIVIDE_TYPE.getCode()))) {
//                        errorList.add("当提前结清手续费收取方式(payoffRepayFeeType)不为NO时,提前结清手续费费率(payoffRepayFeeRate)和提前结清手续费分摊方式(payoffRepayFeeDivideType)都不能为空.");
//                    }
//                }
//                if (!o.containsKey(MetadataProperty.PAYOFF_INTEREST_TYPE.getCode())) {
//                    errorList.add("当存在提前结清收费区间时,提前结清利息收取方式(payoffInterestType)不能为空.");
//                }
//                if (o.containsKey(MetadataProperty.PAYOFF_INTEREST_TYPE.getCode())) {
//                    String payoffInterestType = o.get(MetadataProperty.PAYOFF_INTEREST_TYPE.getCode()).toString();
//                    if (!o.containsKey(MetadataProperty.PAYOFF_INTEREST_DIVIDE_TYPE.getCode())) {
//                        errorList.add("当存在提前结清收费区间时,提亲结清利息分摊方式(payoffInterestDivideType)不能为空.");
//                    }
//                    if ("LOAN_PRINCIPAL_RATE".equals(payoffInterestType) || "UNPAID_PRINCIPAL_RATE".equals(payoffInterestType)) {
//                        if (!o.containsKey(MetadataProperty.PAYOFF_INTEREST_RATE.getCode())) {
//                            errorList.add("当提前结清利息收取方式(payoffInterest)为LOAN_PRINCIPAL_RATE或UNPAID_PRINCIPAL_RATE时,提前结清利率(payoffInterestRate)不能为空.");
//                        }
//                    }
//                }
//            }
//        }
//        if (object.containsKey("payoffAllow")) {
//            String payoffAllow = object.get("payoffAllow").toString();
//            if ("1".equals(payoffAllow) && !object.containsKey("payoffRangeList")) {
//                errorList.add("当是否允许提前结清(payoffAllow)为允许时,提前结清区间不能为空.");
//            }
//        }
//    }
//
//    private void prerepayRelationCheck(JSONObject object, List<String> errorList) {
//        if (object.containsKey("prerepayAllow")) {
//            String prerepayAllow = object.get("prerepayAllow").toString();
//            if ("1".equals(prerepayAllow) && !object.containsKey(MetadataProperty.PREREPAY_INTEREST_TYPE.getCode())) {
//                errorList.add("当是否允许按期提前结清(prerepayAllow)为允许时,按期提前结清利息收取方式(prerepayInterestType)不能为空.");
//            }
//        }
//        if (object.containsKey(MetadataProperty.PREREPAY_INTEREST_TYPE.getCode())) {
//            String prerepayInterestType = object.get(MetadataProperty.PREREPAY_INTEREST_TYPE.getCode()).toString();
//            if ("LOAN_PRINCIPAL_RATE".equals(prerepayInterestType) || "UNPAID_PRINCIPAL_RATE".equals(prerepayInterestType)) {
//                if (!object.containsKey("prerepayInterestRate")) {
//                    errorList.add("当按期提前结清利息收取方式(prerepayInterestType)为LOAN_PRINCIPAL_RATE或UNPAID_PRINCIPAL_RATE时,按期提前结清利率(prerepayInterestRate)不能为空.");
//                }
//            }
//        }
//    }
//
//    private void overdueParamRelationCheck(JSONObject object, List<String> errorList) {
//        if (object.containsKey(MetadataProperty.OVERDUE_DAYS.getCode()) && object.containsKey("penaltyStartDays")) {
//            int overdueDays = Integer.parseInt(object.get(MetadataProperty.OVERDUE_DAYS.getCode()).toString());
//            int penaltyStartDays = Integer.parseInt(object.get("penaltyStartDays").toString());
//            if (penaltyStartDays < overdueDays) {
//                errorList.add("罚息开始日(penaltyStartDays)必须>=逾期开始日(overdueDays).");
//            }
//        }
//
//        if (object.containsKey(MetadataProperty.OVERDUE_DAYS.getCode()) && object.containsKey("badStartDays")) {
//            int overdueDays = Integer.parseInt(object.get(MetadataProperty.OVERDUE_DAYS.getCode()).toString());
//            int badStartDays = Integer.parseInt(object.get("badStartDays").toString());
//            if (badStartDays < overdueDays) {
//                errorList.add("坏账开始日(badStartDays)必须>=逾期开始日(overdueDays).");
//            }
//        }
//    }
//
//    private void feeParamRelationCheck(JSONObject object, List<String> errorList) {
//        if (object.containsKey("feeMethod")) {
//            String feeMethod = object.get("feeMethod").toString();
//            if (!"NONE".equals(feeMethod) && !object.containsKey("feeDivideType")) {
//                errorList.add("当服务费收取方式(feeMethod)不为NONE时,服务费分摊方式(feeDivideType)不能为空.");
//            }
//            if ("PRINCIPLE_PERCENT".equals(feeMethod) && (!object.containsKey("feeRate") || !object.containsKey("minFee") || !object.containsKey("maxFee"))) {
//                errorList.add("当服务费收取方式(feeMethod)为PRINCIPLE_PERCENT时,服务费费率(feeRate),最少服务费(minFee),最多服务费(maxFee)不能为空.");
//            }
//            if ("FIXED_AMOUNT".equals(feeMethod) && !object.containsKey("fixedFee")) {
//                errorList.add("当服务费收取方式(feeMethod)为PRINCIPLE_PERCENT时,固定服务费(fixedFee)不能为空.");
//            }
//        }
//    }
//
//    private void billFeeParamRelationCheck(JSONObject object, List<String> errorList) {
//        if (object.containsKey("billFeeType")) {
//            String billFeeType = object.get("billFeeType").toString();
//            if ("PERCENT".equals(billFeeType) && (!object.containsKey("billFeePercent") || !object.containsKey("billFeeMaxAmount") || !object.containsKey("billFeeMinAmount"))) {
//                errorList.add("当账单费类型(billFeeType)为PERCENT时,账单费费率(billFeePercent),最少账单费(billFeeMinAmount),最多账单费(billFeeMaxAmount)不能为空.");
//            }
//            if ("FIXED".equals(billFeeType) && !object.containsKey("billFee")) {
//                errorList.add("当账单费类型(billFeeType)为PERCENT时,固定账单费(billFee)不能为空.");
//            }
//        }
//    }
//
//    private void termParamRelationCheck(JSONObject object, List<String> errorList) {
//        if (object.containsKey(MetadataProperty.INSTALLMENT_CYCLE.getCode())) {
//            String installmentCycle = object.get(MetadataProperty.INSTALLMENT_CYCLE.getCode()).toString();
//            if ("SCF".equals(installmentCycle) && !object.containsKey("loanDays")) {
//                errorList.add("当分期周期类型(installmentCycle)为SCF时,贷款天数(loanDays)不能为空.");
//            }
//        }
//        if (object.containsKey("repayMethod") && object.containsKey("term")) {
//            String repayMethod = object.get("repayMethod").toString();
//            int term = Integer.parseInt(object.get("term").toString());
//            if ("BORROW_PAYBACK".equals(repayMethod) || "DISPOSABLE_PAYOFF".equals(repayMethod)) {
//                if (term != 1) {
//                    errorList.add("当还款方式(repayMethod)为BORROW_PAYBACK或DISPOSABLE_PAYOFF时,分期数(term)只能为1.");
//                }
//            }
//        }
//        if (object.containsKey(MetadataProperty.FIXED_REPAY_DAY_TYPE.getCode())) {
//            String fixedRepayDayType = object.get(MetadataProperty.FIXED_REPAY_DAY_TYPE.getCode()).toString();
//            if (!"LAST_DAY_OF_MONTH".equals(fixedRepayDayType) && !"NO".equals(fixedRepayDayType)) {
//                if (!object.containsKey(MetadataProperty.FIXED_DAYS.getCode())) {
//                    errorList.add("当固定还款日类型(fixedRepayDayType)不为LAST_DAY_OF_MONTH或者NO时,固定还款日(fixedDays)不能为空.");
//                } else {
//                    Integer fixedDays = Integer.valueOf(object.get(MetadataProperty.FIXED_DAYS.getCode()).toString());
//                    if ("MONTH".equals(fixedRepayDayType) && fixedDays > 28) {
//                        errorList.add("当固定还款日类型(fixedRepayDayType)为MONTH时,固定还款日(fixedDays)不能超过28.");
//                    }
//                    if ("MORE_THAN_DAY_OF_MONTH".equals(fixedRepayDayType) && fixedDays > 28) {
//                        errorList.add("当固定还款日类型(fixedRepayDayType)为MORE_THAN_DAY_OF_MONTH时,固定还款日(fixedDays)不能超过28.");
//                    }
//                    if ("WEEK".equals(fixedRepayDayType) && fixedDays > 7) {
//                        errorList.add("当固定还款日类型(fixedRepayDayType)为WEEK时,固定还款日(fixedDays)不能超过7.");
//                    }
//                }
//            }
//        }
//        if (object.containsKey("productType")) {
//            String productType = object.get("productType").toString();
//            if ("BILL".equals(productType)) {
//                if (!object.containsKey(MetadataProperty.BILL_DAY.getCode()) || !object.containsKey(MetadataProperty.FIXED_DAYS.getCode())) {
//                    errorList.add("当产品类型(productType)为BILL时,账单日(billDay)和固定还款日(fixedDays)不能为空.");
//                }
//                if (object.containsKey(MetadataProperty.FIXED_REPAY_DAY_TYPE.getCode())) {
//                    String fixedRepayDayType = object.get(MetadataProperty.FIXED_REPAY_DAY_TYPE.getCode()).toString();
//                    if (!"DAY_AFTER_BILL_DAY".equals(fixedRepayDayType)) {
//                        errorList.add("当产品类型(productType)为BILL时,固定还款日类型(fixedRepayDayType)只能为DAY_AFTER_BILL_DAY.");
//                    }
//                }
//                if (object.containsKey(MetadataProperty.INSTALLMENT_CYCLE.getCode())) {
//                    String installmentCycle = object.get(MetadataProperty.INSTALLMENT_CYCLE.getCode()).toString();
//                    if (!"MONTH".equals(installmentCycle) && !"WEEK".equals(installmentCycle) && !"DAY".equals(installmentCycle)) {
//                        errorList.add("当产品类型(productType)为BILL时,可选值只能是MONTH/WEEK/DAY.");
//                    }
//                    if ("DAY".equals(installmentCycle) && object.containsKey(MetadataProperty.BILL_DAY.getCode()) && object.containsKey(MetadataProperty.FIXED_DAYS.getCode())) {
//                        Integer billDay = Integer.valueOf(object.get(MetadataProperty.BILL_DAY.getCode()).toString());
//                        Integer fixedDays = Integer.valueOf(object.get(MetadataProperty.FIXED_DAYS.getCode()).toString());
//                        if (billDay > fixedDays) {
//                            errorList.add("当产品类型(productType)为BILL且分期周期类型(installmentCycle)为DAY时,账单日(billDay)必须<=固定还款日(fixedDays).");
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private void check(CheckMetadata metadata, JSONObject object, List<String> errorList) {
//        Object value;
//        if (Objects.nonNull(metadata) && Objects.nonNull(value = object.get(metadata.getKey()))) {
//            if ("enum".equals(metadata.getMetadata().getType()) || "bool".equals(metadata.getMetadata().getType())) {
//                if (!metadata.getEnumCodeSet().contains(value.toString())) {
//                    errorList.add(String.format("%s(%s)必须在(%s)之中", metadata.getMetadata().getName(), metadata.getKey(), metadata.getEnumKeyJoinString()));
//                }
//            } else if ("integer".equals(metadata.getMetadata().getType())) {
//                try {
//                    int integer = Integer.parseInt(value.toString());
//                    if (Objects.nonNull(metadata.getCloseMinInteger()) && integer < metadata.getCloseMinInteger()) {
//                        errorList.add(String.format("%s(%s)必须>=%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getCloseMinInteger()));
//                    }
//                    if (Objects.nonNull(metadata.getCloseMaxInteger()) && integer > metadata.getCloseMaxInteger()) {
//                        errorList.add(String.format("%s(%s)必须<=%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getCloseMaxInteger()));
//                    }
//                    if (Objects.nonNull(metadata.getOpenMinInteger()) && integer <= metadata.getOpenMinInteger()) {
//                        errorList.add(String.format("%s(%s)必须>%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getOpenMinInteger()));
//                    }
//                    if (Objects.nonNull(metadata.getOpenMaxInteger()) && integer >= metadata.getOpenMaxInteger()) {
//                        errorList.add(String.format("%s(%s)必须<%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getOpenMaxInteger()));
//                    }
//                } catch (NumberFormatException e) {
//                    errorList.add(String.format("%s(%s)必须是整数.", metadata.getMetadata().getName(), metadata.getKey()));
//                }
//            } else if ("string".equals(metadata.getMetadata().getType())) {
//                int length = value.toString().length();
//                if (Objects.nonNull(metadata.getCloseMinInteger()) && length < metadata.getCloseMinInteger()) {
//                    errorList.add(String.format("%s(%s)字符串长度必须>=%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getCloseMinInteger()));
//                }
//                if (Objects.nonNull(metadata.getCloseMaxInteger()) && length > metadata.getCloseMaxInteger()) {
//                    errorList.add(String.format("%s(%s)字符串长度必须<=%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getCloseMaxInteger()));
//                }
//                if (Objects.nonNull(metadata.getOpenMinInteger()) && length <= metadata.getOpenMinInteger()) {
//                    errorList.add(String.format("%s(%s)字符串长度必须>%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getOpenMinInteger()));
//                }
//                if (Objects.nonNull(metadata.getOpenMaxInteger()) && length >= metadata.getOpenMaxInteger()) {
//                    errorList.add(String.format("%s(%s)字符串长度必须<%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getOpenMaxInteger()));
//                }
//            } else if ("decimal".equals(metadata.getMetadata().getType())) {
//                try {
//                    BigDecimal decimal = new BigDecimal(value.toString());
//                    if (Objects.nonNull(metadata.getCloseMinDecimal()) && decimal.compareTo(metadata.getCloseMinDecimal()) < 0) {
//                        errorList.add(String.format("%s(%s)必须>=%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getCloseMinDecimal()));
//                    }
//                    if (Objects.nonNull(metadata.getCloseMaxDecimal()) && decimal.compareTo(metadata.getCloseMaxDecimal()) > 0) {
//                        errorList.add(String.format("%s(%s)必须<=%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getCloseMaxDecimal()));
//                    }
//                    if (Objects.nonNull(metadata.getOpenMinDecimal()) && decimal.compareTo(metadata.getOpenMinDecimal()) <= 0) {
//                        errorList.add(String.format("%s(%s)必须>%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getOpenMinDecimal()));
//                    }
//                    if (Objects.nonNull(metadata.getOpenMaxDecimal()) && decimal.compareTo(metadata.getOpenMaxDecimal()) >= 0) {
//                        errorList.add(String.format("%s(%s)必须<%s.", metadata.getMetadata().getName(), metadata.getKey(), metadata.getOpenMaxDecimal()));
//                    }
//                } catch (NumberFormatException e) {
//                    errorList.add(String.format("%s(%s)必须是数字.", metadata.getMetadata().getName(), metadata.getKey()));
//                }
//            }
//        }
//    }
//
//    @Data
//    @Builder
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class EnumValue {
//        private String code;
//        private String name;
//    }
//
//    @Data
//    @Builder
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class CheckMetadata {
//        private String key;
//        private PsProductMetadata metadata;
//        private Map<String, String> enumMap;
//        private Set<String> enumCodeSet;
//        private String enumKeyJoinString;
//        private Integer closeMinInteger;
//        private Integer closeMaxInteger;
//        private Integer openMinInteger;
//        private Integer openMaxInteger;
//        private BigDecimal closeMinDecimal;
//        private BigDecimal closeMaxDecimal;
//        private BigDecimal openMinDecimal;
//        private BigDecimal openMaxDecimal;
//    }
//
//    @Getter
//    @AllArgsConstructor
//    enum MetadataProperty {
//
//        /**
//         * enums
//         */
//        BILL_DAY("billDay", "账单日"),
//        OVERDUE_DAYS("overdueDays", "逾期日"),
//        FIXED_DAYS("fixedDays", "固定还款日"),
//        FIXED_REPAY_DAY_TYPE("fixedRepayDayType", "固定还款日类型"),
//        INSTALLMENT_CYCLE("installmentCycle", "分期类型"),
//        ROLLOVER_FEE_TYPE("rolloverFeeType", "展期费收取方式"),
//        PAYOFF_MIN_DAY("payoffMinDay", "提前结清的开始区间"),
//        PAYOFF_MAX_DAY("payoffMaxDay", "提前结清的结束区间"),
//        PAYOFF_REPAY_FEE_RATE("payoffRepayFeeRate", "提前结清的还款手续费费率"),
//        PAYOFF_REPAY_FEE_TYPE("payoffRepayFeeType", "提前结清的还款手续费类型"),
//        PAYOFF_REPAY_FEE_DIVIDE_TYPE("payoffRepayFeeDivideType", "提前结清的还款手续费分摊方式"),
//        PAYOFF_INTEREST_TYPE("payoffInterestType", "提前结清的利息收取方式"),
//        PAYOFF_INTEREST_DIVIDE_TYPE("payoffInterestDivideType", "提前结清的利息分摊方式"),
//        PAYOFF_INTEREST_RATE("payoffInterestRate", "提前结清的利息利率"),
//        PREREPAY_INTEREST_TYPE("prerepayInterestType", "提前结清的利息计算方式"),
//        GUARANTEE_FEE_RATE("guaranteeFeeRate", "担保费率"),
//        ;
//
//        private String code;
//
//        private String desc;
//    }
//}
