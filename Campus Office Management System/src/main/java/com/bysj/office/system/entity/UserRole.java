package com.bysj.office.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 2354394771912648574L;
    /**
     * userId
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * roleId
     */
    @TableField("ROLE_ID")
    private Long roleId;


}
