package com.bysj.office.base.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
@TableName("t_notification")
public class Notification {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("title")
    private String title;

    /**
     * 
     */
    @TableField("content")
    private String content;

    /**
     * 
     */
    @TableField("createtime")
    private Date createtime;

}
