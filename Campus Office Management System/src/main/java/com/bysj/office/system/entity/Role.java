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
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -4493960686192269860L;
    /**
     * ID
     */
    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Long roleId;

    /**
     * name
     */
    @TableField("ROLE_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    private String roleName;

    /**
     * remark
     */
    @TableField("REMARK")
    @Size(max = 50, message = "{noMoreThan}")
    private String remark;

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

    /**
     * menuIds
     */
    private transient String menuIds;
}
