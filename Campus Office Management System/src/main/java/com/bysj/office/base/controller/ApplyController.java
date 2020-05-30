package com.bysj.office.base.controller;

import com.bysj.office.base.entity.Apply;
import com.bysj.office.base.service.IApplyService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Validated
@Controller
public class ApplyController extends BaseController {

    @Autowired
    private IApplyService applyService;
    @Autowired
    private IUserService userService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/apply")
    public String applyIndex(){
        return FebsUtil.view("office/apply/apply");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/apply/add")
    public String applyAdd(){
        return FebsUtil.view("office/apply/applyAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/apply/update/{id}")
    public String applyUpdate(@PathVariable String id,ModelMap modelMap){
        Apply apply = applyService.getById(id);
        modelMap.put("apply",apply);
        return FebsUtil.view("office/apply/applyUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/teacherapply")
    public String teacherApplyIndex(){
        return FebsUtil.view("office/apply/teacherapply");
    }

    @GetMapping("apply")
    @ResponseBody
    public FebsResponse getAllApplys(Apply apply) {
        return new FebsResponse().success().data(applyService.findApplys(apply));
    }

    @GetMapping("teacherapply")
    @ResponseBody
    public FebsResponse getAllTeacherApplys(QueryRequest request, Apply apply) {
        apply.setOperatorid(Integer.parseInt(getCurrentUser().getId()+""));
        Map<String, Object> dataTable = getDataTable(this.applyService.findApplys(request, apply));

        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("apply/list")
    @ResponseBody
    public FebsResponse applyList(QueryRequest request, Apply apply) {
        Map<String, Object> dataTable = getDataTable(this.applyService.findApplys(request, apply));
        return new FebsResponse().success().data(dataTable);
    }



    @ControllerEndpoint(operation = "add Apply", exceptionMessage = "add Apply fail")
    @PostMapping("apply")
    @ResponseBody
    public FebsResponse addApply(@Valid Apply apply) {
        apply.setCreatetime(new Date());
        User user = userService.getById(apply.getOperatorid());
        apply.setName(user.getName());
        this.applyService.createApply(apply);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "delete Apply", exceptionMessage = "delete Apply fail")
    @GetMapping("apply/delete/{applyIds}")
    @ResponseBody
    public FebsResponse deleteApply(@PathVariable String applyIds) {
        String[] ids = applyIds.split(",");
        this.applyService.deleteApplys(ids);
        return new FebsResponse().success();
    }



    @ControllerEndpoint(operation = "update Apply", exceptionMessage = "update Apply fail")
    @PostMapping("apply/update")
    @ResponseBody
    public FebsResponse updateApply(Apply apply) {
        this.applyService.updateApply(apply);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "export Apply", exceptionMessage = "export Excel fail")
    @PostMapping("apply/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, Apply apply, HttpServletResponse response) {
        List<Apply> applys = this.applyService.findApplys(queryRequest, apply).getRecords();
        ExcelKit.$Export(Apply.class, response).downXlsx(applys, false);
    }
}
