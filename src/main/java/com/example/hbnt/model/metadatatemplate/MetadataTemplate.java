package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.Metadata;
import lombok.Data;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@Data
public abstract class MetadataTemplate<T> implements Comparable<MetadataTemplate<?>> {

    protected Long id;

    Type type;

    String key;

    String name;

    T defaultValue;

    String defaultValueString;

    Boolean optional;

    Integer order;

    Boolean shared;

    String valueJson;

    String tag;

    /**
     * 界面控件输入提示 widget_type=input|textarea时有效
     */
    String placeholder;

    /**
     * 控件类型 input:输入框 radio:单选框 select:下拉列表 checkbox:复选框 textarea:文本域
     */
    String widgetType;

    /**
     * 界面校验脚本
     */
    String webCheckScript;

    public MetadataTemplate(Long id,
                            Type type,
                            String key,
                            String name,
                            T defaultValue,
                            String defaultValueString,
                            Boolean optional,
                            Integer order,
                            Boolean shared) {
        this.id = id;
        this.type = type;
        this.key = key;
        this.name = name;
        this.defaultValue = defaultValue;
        this.defaultValueString = defaultValueString;
        this.optional = optional;
        this.order = order;
        this.shared = shared;
    }

    abstract boolean isValidValue(T t);

    abstract Metadata<T> createMetadata(T value);

    @Override
    public int compareTo(MetadataTemplate<?> o) {
        return order.compareTo(o.order);
    }

    public abstract void accept(MetadataTemplateVisitor visitor);
}
