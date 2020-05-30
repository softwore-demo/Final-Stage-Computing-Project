package com.bysj.office.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.office.base.entity.Apply;
import com.bysj.office.base.entity.Course;
import com.bysj.office.base.mapper.CourseMapper;
import com.bysj.office.base.service.ICourseService;
import com.bysj.office.common.entity.QueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public IPage<Course> findCourses(QueryRequest request, Course course) {

        if(course.getPersons() != null && !"".contentEquals(course.getPersons()))
        {
            course.setPersons("%" + course.getPersons() + "%");
        }
        Page<Course> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findCourseDetailPage(page,course);
    }

    @Override
    public List<Course> findCourses(Course course) {
	    LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
		// TODO
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createCourse(Course course) {
        this.save(course);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        this.saveOrUpdate(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Course course) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
	    // TODO
	    this.remove(wrapper);
	}

    @Override
    public void deleteCourse(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }
}
