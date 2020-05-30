package com.bysj.office.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.office.base.entity.Course;
import com.bysj.office.base.entity.Leave;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


public interface LeaveMapper extends BaseMapper<Leave> {

    IPage<Leave> findLeaveDetailPage(Page page, Map<String,Object> condMap);
}
