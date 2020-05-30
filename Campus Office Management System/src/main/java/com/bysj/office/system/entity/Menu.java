package com.bysj.office.system.entity;

import com.bysj.office.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Data
@TableName("t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 8571011372410167901L;

    public static final String TYPE_MENU = "0";
    public static final String TYPE_BUTTON = "1";

    public static final Long TOP_NODE = 0L;

    /**
     * menuId
     */
    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private Long menuId;

    /**
     * parentId
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * menuName
     */
    @TableField("MENU_NAME")
    @NotBlank(message = "{required}")
    private String menuName;

    /**
     * URL
     */
    @TableField("URL")
    @Size(max = 50, message = "{noMoreThan}")
    private String url;

    /**
     * perms
     */
    @TableField("PERMS")
    @Size(max = 50, message = "{noMoreThan}")
    private String perms;

    /**
     * icon
     */
    @TableField("ICON")
    @Size(max = 50, message = "{noMoreThan}")
    private String icon;

    /**
     * type
     */
    @TableField("TYPE")
    @NotBlank(message = "{required}")
    private String type;

    /**
     * orderNum
     */
    @TableField("ORDER_NUM")
    private Long orderNum;

    /**
     * createTime
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * modifyTime
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;


}
