package com.example.hbnt.dataobject;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 产品元数据
 * </p>
 *
 * @author lei.wu
 * @since 2020-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ps_product_metadata")
public class PsProductMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 元数据key
     */
    private String key;

    /**
     * 元数据名称
     */
    private String name;

    /**
     * 数据类型 string:字符串 decimal:小数 integer:整数 bool:布尔值(0或1) 5:enum 6:json
     */
    private String type;

    /**
     * 数据范围,type=string|decimal|integer时有效,例如:(0,9]代表>0且<=9;[0,+)代表>=0
     */
    private String range;

    /**
     * 值json,type=bool|enum时有效
     */
    private String valueJson;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 校验脚本
     */
    private String checkScript;

    /**
     * 分组
     */
    private String group;

    /**
     * 分组内顺序
     */
    private Integer groupSort;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 标签
     */
    private String tag;

    /**
     * 界面控件输入提示 widget_type=input|textarea时有效
     */
    private String placeholder;

    /**
     * 控件类型 input:输入框 radio:单选框 select:下拉列表 checkbox:复选框 textarea:文本域
     */
    private String widgetType;

    /**
     * 界面校验脚本
     */
    private String webCheckScript;

    /**
     * 描述
     */
    private String desc;

    /**
     * 添加人
     */
    private String addUser;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 是否删除，0：正常，1：删除
     */
    private Integer isDelete;

    /**
     * 记录新增时间
     */
    private Date addTime;

    /**
     * 记录最后一次修改时间
     */
    private Date modifyTime;


}
