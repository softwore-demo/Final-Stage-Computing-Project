package com.bysj.office.base.controller;

import com.bysj.office.base.entity.Notification;
import com.bysj.office.base.service.INotificationService;
import com.bysj.office.common.annotation.ControllerEndpoint;
import com.bysj.office.common.utils.FebsUtil;
import com.bysj.office.common.entity.FebsConstant;
import com.bysj.office.common.controller.BaseController;
import com.bysj.office.common.entity.FebsResponse;
import com.bysj.office.common.entity.QueryRequest;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.ast.Not;
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
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Validated
@Controller
public class NotificationController extends BaseController {

    @Autowired
    private INotificationService notificationService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/notification")
    public String notificationIndex(){
        return FebsUtil.view("office/notification/notification");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/notification/add")
    public String notificationAdd(){
        return FebsUtil.view("office/notification/notificationAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/notification/update/{notificationId}")
    public String notificationUpdate(@PathVariable String notificationId, ModelMap modelMap){
        Notification notification = notificationService.getById(notificationId);
        modelMap.put("notification",notification);
        return FebsUtil.view("office/notification/notificationUpdate");
    }

    @GetMapping("notification")
    @ResponseBody
    public FebsResponse getAllNotifications(Notification notification) {
        return new FebsResponse().success().data(notificationService.findNotifications(notification));
    }

    @GetMapping("notification/list")
    @ResponseBody
    public FebsResponse notificationList(QueryRequest request, Notification notification) {
        Map<String, Object> dataTable = getDataTable(this.notificationService.findNotifications(request, notification));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "add Notification", exceptionMessage = "add Notification fail")
    @PostMapping("notification")
    @ResponseBody
    public FebsResponse addNotification(@Valid Notification notification) throws IOException {
        notification.setCreatetime(new Date());
        this.notificationService.createNotification(notification);
        //message
        WebSocketServer.sendInfos("You have an new notification message");
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "delete Notification", exceptionMessage = "delete Notification fail")
    @GetMapping("notification/delete/{notificationIds}")
    @ResponseBody
    public FebsResponse deleteNotification(@PathVariable String notificationIds) {
        String[] ids = notificationIds.split(",");
        this.notificationService.deleteNotifications(ids);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "update Notification", exceptionMessage = "update Notification fail")
    @PostMapping("notification/update")
    @ResponseBody
    public FebsResponse updateNotification(Notification notification) {
        this.notificationService.updateNotification(notification);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "export Notification", exceptionMessage = "export Excel fail")
    @PostMapping("notification/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, Notification notification, HttpServletResponse response) {
        List<Notification> notifications = this.notificationService.findNotifications(queryRequest, notification).getRecords();
        ExcelKit.$Export(Notification.class, response).downXlsx(notifications, false);
    }
}
