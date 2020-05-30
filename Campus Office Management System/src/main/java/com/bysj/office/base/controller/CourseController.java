package com.bysj.office.base.controller;

import com.bysj.office.base.entity.Course;
import com.bysj.office.base.service.ICourseService;
import com.bysj.office.common.annotation.ControllerEndpoint;
import com.bysj.office.common.utils.DateUtil;
import com.bysj.office.common.utils.FebsUtil;
import com.bysj.office.common.entity.FebsConstant;
import com.bysj.office.common.controller.BaseController;
import com.bysj.office.common.entity.FebsResponse;
import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.system.entity.User;
import com.bysj.office.system.service.IUserService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.Session;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Validated
@Controller
public class CourseController extends BaseController {

    @Autowired
    private ICourseService courseService;
    @Autowired
    private IUserService userService;
    @GetMapping(FebsConstant.VIEW_PREFIX + "office/course")
    public String courseIndex(){
        return FebsUtil.view("office/course/course");
    }
    @GetMapping(FebsConstant.VIEW_PREFIX + "office/course/add")
    public String courseAdd(){
        return FebsUtil.view("office/course/courseAdd");
    }
    @GetMapping(FebsConstant.VIEW_PREFIX + "office/courseteacher")
    public String courseTeacher(){
        return FebsUtil.view("office/course/courseteacher");
    }
    @GetMapping(FebsConstant.VIEW_PREFIX + "office/course/update/{id}")
    public String courseUpdate(@PathVariable Integer id, ModelMap modelMap){
        Course course = courseService.getById(id);
        Date date = course.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
        course.setMyDate(simpleDateFormat.format(date));
        course.setMyTime(simpleDateFormat1.format(date));
        modelMap.put("course",course);
        return FebsUtil.view("office/course/courseUpdate");
    }

    @GetMapping("course")
    @ResponseBody
    public FebsResponse getAllCourses(Course course) {
        return new FebsResponse().success().data(courseService.findCourses(course));
    }

    @GetMapping("course/teacherlist")
    @ResponseBody
    public FebsResponse courseteacherList(QueryRequest request, Course course) {
        course.setPersons(getCurrentUser().getName());
        Map<String, Object> dataTable = getDataTable(this.courseService.findCourses(request, course));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("course/list")
    @ResponseBody
    public FebsResponse courseList(QueryRequest request, Course course) {
        Map<String, Object> dataTable = getDataTable(this.courseService.findCourses(request, course));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "add Course", exceptionMessage = "add Course fail")
    @PostMapping("course")
    @ResponseBody
    public FebsResponse addCourse(@Valid Course course) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        String dateStr = course.getMyDate()+" "+course.getMyTime();
        try {
            course.setTime(simpleDateFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.courseService.createCourse(course);
        User user = userService.findByRealName(course.getPersons());
        //message
        WebSocketServer.sendInfo("You have an new course message.",user.getUserId()+"");
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "delete Course", exceptionMessage = "delete Course fail")
    @GetMapping("course/delete/{courseIds}")
    @ResponseBody
    public FebsResponse deleteCourse(@PathVariable String courseIds) {
        String[] ids = courseIds.split(",");
        this.courseService.deleteCourse(ids);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "update Course", exceptionMessage = "update Course fail")
    @PostMapping("course/update")
    @ResponseBody
    public FebsResponse updateCourse(Course course) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        String dateStr = course.getMyDate()+" "+course.getMyTime();
        try {
            course.setTime(simpleDateFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.courseService.updateCourse(course);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "export Course", exceptionMessage = "export Excel fail")
    @PostMapping("course/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, Course course, HttpServletResponse response) {
        List<Course> courses = this.courseService.findCourses(queryRequest, course).getRecords();
        ExcelKit.$Export(Course.class, response).downXlsx(courses, false);
    }
}
