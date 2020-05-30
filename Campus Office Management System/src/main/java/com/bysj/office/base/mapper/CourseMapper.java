package com.bysj.office.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.office.base.entity.Apply;
import com.bysj.office.base.entity.Course;
import org.apache.ibatis.annotations.Param;


public interface CourseMapper extends BaseMapper<Course> {


    IPage<Course> findCourseDetailPage(Page page, @Param("course") Course course);

}
