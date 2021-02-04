package com.example.hbnt.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author tao.lin
 * @date 2021/2/3
 */
@Data
@Entity
@Table(name = "ps_product_template")
public class PsProductTemplate implements Serializable {

    private static final long serialVersionUID = -1709756466978785844L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 产品模板名称
     */
    private String name;
    /**
     * 适用产品类型
     */
    private String productType;
    /**
     * 对应的业务线
     */
    private String serviceLine;
    /**
     * 产品模板描述
     */
    private String description;
    /**
     * 公共配置项的keys，每个产品只配置一遍，格式：configKey:是否必填0/1，如key1:0,key2:1,key3:0
     */
    private String commonConfigKeys;
    /**
     * 私有配置项的keys，每个产品下的Item都要配置一遍，格式：configKey:是否必填0/1，如key1:0,key2:1,key3:0
     */
    private String privateConfigKeys;

    private Boolean isDeleted;

    private Date addTime;

    private Date modifyTime;


    public Map<String, Boolean> keys(boolean common) {
        return common ? parse(commonConfigKeys) : parse(privateConfigKeys);
    }

    private Map<String, Boolean> parse(String str) {
        Map<String, Boolean> ret = new HashMap<>();
        String[] pairs = str.split(",");
        for (String pair : pairs) {
            String[] kv = pair.split(":");
            if ("1".equalsIgnoreCase(kv[1])) {
                ret.put(kv[0], true);
            } else if ("0".equalsIgnoreCase(kv[1])) {
                ret.put(kv[0], false);
            } else {
                throw new IllegalArgumentException();
            }
        }
        return ret;
    }
}
