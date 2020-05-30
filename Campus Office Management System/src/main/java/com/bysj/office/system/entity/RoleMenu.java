package com.bysj.office.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("t_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -5200596408874170216L;
    /**
     * roleId
     */
    @TableField("ROLE_ID")
    private Long roleId;

    /**
     * menuId
     */
    @TableField("MENU_ID")
    private Long menuId;


}
