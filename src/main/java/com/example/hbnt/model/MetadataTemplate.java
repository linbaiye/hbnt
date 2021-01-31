package com.example.hbnt.model;

import lombok.Data;
import javax.persistence.*;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@Data
public abstract class MetadataTemplate<T> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Type type;

    String key;

    String name;

    T defaultValue;

    String defaultValueString;


}
