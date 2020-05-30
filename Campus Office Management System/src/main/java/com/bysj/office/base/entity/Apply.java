package com.bysj.office.base.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@TableName("t_apply")
public class Apply {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("name")
    private String name;

    /**
     * 
     */
    /*@TableField("job")
    private String job;*/

    /**
     * 
     */
    @TableField("description")
    private String description;

    /**
     * 
     */
    @TableField("starttime")
    private Date starttime;

    /**
     * 
     */
    @TableField("createtime")
    private Date createtime;

    /**
     * 
     */
    @TableField("endtime")
    private Date endtime;

    /**
     * 
     */
    @TableField("status")
    private String status;

    /**
     * 
     */
    @TableField("operatorid")
    private Integer operatorid;

    /**
     *
     */
    @TableField("time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date time;

}
