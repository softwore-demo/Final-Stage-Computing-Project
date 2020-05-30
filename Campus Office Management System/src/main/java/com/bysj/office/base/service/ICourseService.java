package com.bysj.office.base.service;


import com.bysj.office.base.entity.Course;
import com.bysj.office.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ICourseService extends IService<Course> {
    /**
     *
     *
     * @param request QueryRequest
     * @param course course
     * @return IPage<Course>
     */
    IPage<Course> findCourses(QueryRequest request, Course course);

    /**
     *
     *
     * @param course course
     * @return List<Course>
     */
    List<Course> findCourses(Course course);

    /**
     * add
     *
     * @param course course
     */
    void createCourse(Course course);

    /**
     * update
     *
     * @param course course
     */
    void updateCourse(Course course);

    /**
     * delete
     *
     * @param course course
     */
    void deleteCourse(Course course);

    void deleteCourse(String[] ids);
}
