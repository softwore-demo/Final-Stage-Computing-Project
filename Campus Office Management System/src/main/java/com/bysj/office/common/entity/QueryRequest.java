package com.bysj.office.common.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Data
@ToString
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    private int pageSize = 10;
    private int pageNum = 1;

    private String field;
    private String order;
    private String timeStr ;
}
