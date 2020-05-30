package com.bysj.office.base.controller;

import com.bysj.office.base.entity.Leave;
import com.bysj.office.base.service.ILeaveService;
import com.bysj.office.common.annotation.ControllerEndpoint;
import com.bysj.office.common.utils.FebsUtil;
import com.bysj.office.common.entity.FebsConstant;
import com.bysj.office.common.controller.BaseController;
import com.bysj.office.common.entity.FebsResponse;
import com.bysj.office.common.entity.QueryRequest;

import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Validated
@Controller
public class LeaveController extends BaseController {

    @Autowired
    private ILeaveService leaveService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/leave")
    public String leaveIndex(){
        return FebsUtil.view("office/leave/leave");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/leaveApply")
    public String leaveApply(){
        return FebsUtil.view("office/leave/leaveApply");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/leave/add")
    public String leaveAdd(){
        return FebsUtil.view("office/leave/leaveAdd");
    }

    @GetMapping("leave")
    @ResponseBody
    public FebsResponse getAllLeaves(Leave leave) {
        return new FebsResponse().success().data(leaveService.findLeaves(leave));
    }

    @GetMapping("leave/list")
    @ResponseBody
    public FebsResponse leaveList(QueryRequest request, Leave leave) {
        Map<String, Object> dataTable = getDataTable(this.leaveService.findLeaves(request, leave));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("leave/apply/list")
    @ResponseBody
    public FebsResponse leaveApplyList(QueryRequest request, Leave leave) {
        /*leave.setName(getCurrentUser().getName());*/
        Map<String, Object> dataTable = getDataTable(this.leaveService.findLeaves(request, leave));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "add Leave", exceptionMessage = "add Leave fail")
    @PostMapping("leave")
    @ResponseBody
    public FebsResponse addLeave(@Valid Leave leave) {
        leave.setCreatetime(new Date());
        leave.setName(getCurrentUser().getName());
        leave.setStatus("1");
        this.leaveService.createLeave(leave);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "delete Leave", exceptionMessage = "delete Leave fail")
    @GetMapping("leave/delete")
    @ResponseBody
    public FebsResponse deleteLeave(Leave leave) {
        this.leaveService.deleteLeave(leave);
        return new FebsResponse().success();
    }

    @GetMapping("leave/status/{leaveId}/{type}")
    @ResponseBody
    public FebsResponse status(@PathVariable Integer leaveId,@PathVariable String type) {
        this.leaveService.changeStatus(leaveId,type);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "update Leave", exceptionMessage = "update Leave fail")
    @PostMapping("leave/update")
    @ResponseBody
    public FebsResponse updateLeave(Leave leave) {
        this.leaveService.updateLeave(leave);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "export Leave", exceptionMessage = "export Excel fail")
    @PostMapping("leave/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, Leave leave, HttpServletResponse response) {
        List<Leave> leaves = this.leaveService.findLeaves(queryRequest, leave).getRecords();
        ExcelKit.$Export(Leave.class, response).downXlsx(leaves, false);
    }
}
