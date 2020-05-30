package com.bysj.office.system.controller;


import com.bysj.office.common.annotation.ControllerEndpoint;
import com.bysj.office.common.controller.BaseController;
import com.bysj.office.common.entity.FebsResponse;
import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.common.exception.FebsException;
import com.bysj.office.system.entity.Role;
import com.bysj.office.system.service.IRoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public FebsResponse getAllRoles(Role role) {
        return new FebsResponse().success().data(roleService.findRoles(role));
    }

    @GetMapping("list")
    public FebsResponse roleList(Role role, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.roleService.findRoles(role, request));
        return new FebsResponse().success().data(dataTable);
    }

    @PostMapping
    @ControllerEndpoint(operation = "add role", exceptionMessage = "add role fail")
    public FebsResponse addRole(@Valid Role role) {
        this.roleService.createRole(role);
        return new FebsResponse().success();
    }

    @GetMapping("delete/{roleIds}")
    @ControllerEndpoint(operation = "delete role", exceptionMessage = "delete role fail")
    public FebsResponse deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        this.roleService.deleteRoles(roleIds);
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @ControllerEndpoint(operation = "update role", exceptionMessage = "update role fail")
    public FebsResponse updateRole(Role role) {
        this.roleService.updateRole(role);
        return new FebsResponse().success();
    }

    @GetMapping("excel")
    @ControllerEndpoint(exceptionMessage = "export Excel fail")
    public void export(QueryRequest queryRequest, Role role, HttpServletResponse response) throws FebsException {
        List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
        ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
    }

}
