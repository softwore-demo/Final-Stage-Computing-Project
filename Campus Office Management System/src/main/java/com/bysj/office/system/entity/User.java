package com.bysj.office.system.entity;

import com.bysj.office.common.annotation.IsMobile;
import com.bysj.office.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Data
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = -4352868070794165001L;

    public static final String STATUS_VALID = "1";
    public static final String STATUS_LOCK = "0";
    public static final String DEFAULT_AVATAR = "default.jpg";
    public static final String DEFAULT_PASSWORD = "1234qwer";
    public static final String SEX_MALE = "0";
    public static final String SEX_FEMALE = "1";
    public static final String SEX_UNKNOW = "2";
    public static final String THEME_BLACK = "black";
    public static final String THEME_WHITE = "white";
    public static final String TAB_OPEN = "1";
    public static final String TAB_CLOSE = "0";


    /**
     * id
     */
    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Long userId;

    /**
     * username
     */
    @TableField("USERNAME")
    @Size(min = 4, max = 10, message = "{range}")
    private String username;

    /**
     * password
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * dept ID
     */
    @TableField("DEPT_ID")
    private Long deptId;

    /**
     * email
     */
    @TableField("EMAIL")
    @Size(max = 50, message = "{noMoreThan}")
    @Email(message = "{email}")
    private String email;

    /**
     * monile
     */
    @TableField("MOBILE")
    @IsMobile(message = "{mobile}")
    private String mobile;

    /**
     * status 0lock 1unlock
     */
    @TableField("STATUS")
    @NotBlank(message = "{required}")
    private String status;

    /**
     * createtime
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * modifyTime
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * lastLoginTime
     */
    @TableField("LAST_LOGIN_TIME")
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒", timezone = "GMT+8")
    private Date lastLoginTime;

    

    /**
     * avatar
     */
    @TableField("AVATAR")
    private String avatar;

    /**
     * theme
     */
    @TableField("THEME")
    private String theme;

    private String name;

    /**
     * isTab
     */
    @TableField("IS_TAB")
    private String isTab;

    /**
     * description
     */
    @TableField("DESCRIPTION")
    @Size(max = 100, message = "{noMoreThan}")
    private String description;


    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    @NotBlank(message = "{required}")
    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String roleName;

    public Long getId() {
        return userId;
    }
}
