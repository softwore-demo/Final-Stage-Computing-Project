package com.bysj.office.base.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
@TableName("t_course")
public class Course {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("time")
    private Date time;
    @TableField(exist = false)
    private String myDate;
    @TableField(exist = false)
    private String myTime;

    /**
     * 
     */
    @TableField("address")
    private String address;

    /**
     * 
     */
    @TableField("description")
    private String description;

    /**
     * 
     */
    @TableField("persons")
    private String persons;


}
