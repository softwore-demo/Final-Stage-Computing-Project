package com.bysj.office.base.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
@TableName("t_file")
public class File {

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
    @TableField("path")
    private String path;

    /**
     * 
     */
    @TableField("type")
    private String type;

    /**
     * 
     */
    @TableField("description")
    private String description;

    /**
     * 
     */
    @TableField("createtime")
    private Date createtime;

}
