package com.example.hbnt.model.metadatatemplate;

import com.example.hbnt.model.metadata.Metadata;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author tao.lin
 * @date 2021/1/31
 */
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class MetadataTemplate<T> implements Comparable<MetadataTemplate<?>> {

    protected Long id;

    /**
     * 元数据key
     */
    protected String key;

    /**
     * 元数据名称
     */
    protected String name;

    /**
     * 数据类型 string:字符串 decimal:小数 integer:整数 bool:布尔值(0或1) 5:enum 6:json
     */
    protected Type type;

    /**
     * 数据范围,type=string|decimal|integer时有效,例如:(0,9]代表>0且<=9;[0,+)代表>=0
     */
    protected String range;

    /**
     * 值json,type=bool|enum时有效
     */
    protected String valueJson;

    /**
     * 默认值
     */
    protected String defaultValue;

    /**
     * 校验脚本
     */
    protected String checkScript;

    /**
     * 分组
     */
    protected String group;

    /**
     * 分组内顺序
     */
    protected Integer groupSort;

    /**
     * 顺序
     */
    protected Integer sort;

    /**
     * 标签
     */
    protected String tag;

    /**
     * 界面控件输入提示 widget_type=input|textarea时有效
     */
    protected String placeholder;

    /**
     * 控件类型 input:输入框 radio:单选框 select:下拉列表 checkbox:复选框 textarea:文本域
     */
    protected String widgetType;

    /**
     * 界面校验脚本
     */
    protected String webCheckScript;

    /**
     * 描述
     */
    protected String desc;

    /**
     * 添加人
     */
    protected String addUser;

    /**
     * 修改人
     */
    protected String modifyUser;

    /**
     * 是否删除，0：正常，1：删除
     */
    protected Integer isDelete;

    /**
     * 记录新增时间
     */
    protected Date addTime;

    /**
     * 记录最后一次修改时间
     */
    protected Date modifyTime;

    protected boolean required;

    public abstract boolean isValidValue(Object v);

    public boolean canCreateMetadata(String key) {
        return this.key.equalsIgnoreCase(key);
    }

    public abstract Metadata<T> createMetadata(Object value);

    public void init() { }

    @Override
    public int compareTo(MetadataTemplate<?> o) {
        return sort.compareTo(o.sort);
    }

    public abstract void accept(MetadataTemplateVisitor visitor);
}
