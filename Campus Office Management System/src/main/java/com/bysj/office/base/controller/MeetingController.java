package com.bysj.office.base.controller;

import com.bysj.office.base.entity.Meeting;
import com.bysj.office.base.service.IMeetingService;
import com.bysj.office.common.annotation.ControllerEndpoint;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


@Slf4j
@Validated
@Controller
public class MeetingController extends BaseController {

    @Autowired
    private IMeetingService meetingService;

    @Autowired
    private IUserService userService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/meeting")
    public String meetingIndex(){
        return FebsUtil.view("office/meeting/meeting");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/meetingteacher")
    public String meetingTeacherIndex(){
        return FebsUtil.view("office/meeting/meetingteacher");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/meeting/add")
    public String meetingAdd(){
        return FebsUtil.view("office/meeting/meetingAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/meeting/update/{id}")
    public String meetingUpdate(@PathVariable Integer id, ModelMap modelMap){
        Meeting meeting = meetingService.getById(id);
        modelMap.put("meeting",meeting);
        return FebsUtil.view("office/meeting/meetingUpdate");
    }

    @GetMapping("meeting")
    @ResponseBody
    public FebsResponse getAllMeetings(Meeting meeting) {
        return new FebsResponse().success().data(meetingService.findMeetings(meeting));
    }

    @GetMapping("meeting/list")
    @ResponseBody
    public FebsResponse meetingList(QueryRequest request, Meeting meeting) {
        Map<String, Object> dataTable = getDataTable(this.meetingService.findMeetings(request, meeting));
        return new FebsResponse().success().data(dataTable);
    }
    @GetMapping("meeting/listteacher")
    @ResponseBody
    public FebsResponse meetingTeacherList(QueryRequest request, Meeting meeting) {
        meeting.setPersons(getCurrentUser().getName());
        Map<String, Object> dataTable = getDataTable(this.meetingService.findMeetings(request, meeting));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "add Meeting", exceptionMessage = "add Meeting fail")
    @PostMapping("meeting")
    @ResponseBody
    public FebsResponse addMeeting(@Valid Meeting meeting) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        String dateStr = meeting.getMyDate()+" "+meeting.getMyTime();
        try {
            meeting.setTime(simpleDateFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.meetingService.createMeeting(meeting);
        User user = userService.findByRealName(meeting.getPersons());
        WebSocketServer.sendInfo("You have an new meeting message.",user.getUserId()+"");
        /*if(!meeting.getPersons().equals("")){
            String[] pers = meeting.getPersons().split(",");
            for(String per:pers){
                User user = userService.findByRealName(per);
                //message
                WebSocketServer.sendInfo("You have an new meeting message.",per);
            }
        }*/


        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "delete Meeting", exceptionMessage = "delete Meeting fail")
    @GetMapping("meeting/delete/{meetings}")
    @ResponseBody
    public FebsResponse deleteMeeting(@PathVariable String meetings) {
        String[] ids = meetings.split(",");
        this.meetingService.deleteMeeting(ids);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "update Meeting", exceptionMessage = "update Meeting fail")
    @PostMapping("meeting/update")
    @ResponseBody
    public FebsResponse updateMeeting(Meeting meeting) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        String dateStr = meeting.getMyDate()+" "+meeting.getMyTime();
        try {
            meeting.setTime(simpleDateFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.meetingService.updateMeeting(meeting);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "export Meeting", exceptionMessage = "export Excel fail")
    @PostMapping("meeting/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, Meeting meeting, HttpServletResponse response) {
        List<Meeting> meetings = this.meetingService.findMeetings(queryRequest, meeting).getRecords();
        ExcelKit.$Export(Meeting.class, response).downXlsx(meetings, false);
    }
}
