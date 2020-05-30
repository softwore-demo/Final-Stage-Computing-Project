package com.bysj.office.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.office.base.entity.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


@Mapper
public interface MeetingMapper extends BaseMapper<Meeting> {


    IPage<Meeting> findMeetingDetailPage(Page page, @Param("condMap") Map<String,Object> condMap);

}
