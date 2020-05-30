package com.bysj.office.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.office.common.annotation.ControllerEndpoint;
import com.bysj.office.common.controller.BaseController;
import com.bysj.office.common.entity.FebsResponse;
import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.common.exception.FebsException;
import com.bysj.office.common.utils.MD5Util;
import com.bysj.office.system.entity.Role;
import com.bysj.office.system.entity.User;
import com.bysj.office.system.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;


    @GetMapping("{username}")
    public User getUser(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findUserDetailList(username);
    }

    @GetMapping("teacher")
    public FebsResponse getAllTeachers() {

        return new FebsResponse().success().data(userService.findAllTeacher());
    }

    @GetMapping
    public FebsResponse getAllUsers() {
        return new FebsResponse().success().data(userService.list());
    }

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return this.userService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    @GetMapping("list")
    public FebsResponse userList(User user, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.userService.findUserDetailList(user, request));
        return new FebsResponse().success().data(dataTable);
    }

    @PostMapping
    @ControllerEndpoint(operation = "add user", exceptionMessage = "add user fail")
    public FebsResponse addUser(@Valid User user) {
        this.userService.createUser(user);
        return new FebsResponse().success();
    }

    @GetMapping("delete/{userIds}")
    @ControllerEndpoint(operation = "delete user", exceptionMessage = "delete user fail")
    public FebsResponse deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) {
        if(!(getCurrentUser().getUserId()+"").equals(userIds)) {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        }
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @ControllerEndpoint(operation = "update user", exceptionMessage = "update user fail")
    public FebsResponse updateUser(@Valid User user) {
        if (user.getUserId() == null)
            throw new FebsException("user ID is empty");
        this.userService.updateUser(user);
        return new FebsResponse().success();
    }

    @PostMapping("password/reset/{usernames}")
    @ControllerEndpoint(exceptionMessage = "rest password fail")
    public FebsResponse resetPassword(@NotBlank(message = "{required}") @PathVariable String usernames) {
        String[] usernameArr = usernames.split(StringPool.COMMA);
        this.userService.resetPassword(usernameArr);
        return new FebsResponse().success();
    }

    @PostMapping("password/update")
    @ControllerEndpoint(exceptionMessage = "update password fail")
    public FebsResponse updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String newPassword) {
        User user = getCurrentUser();
        if (!StringUtils.equals(user.getPassword(), MD5Util.encrypt(user.getUsername(), oldPassword))) {
            throw new FebsException("old password is wrong");
        }
        userService.updatePassword(user.getUsername(), newPassword);
        return new FebsResponse().success();
    }

    @GetMapping("avatar/{image}")
    @ControllerEndpoint(exceptionMessage = "update avatar fail")
    public FebsResponse updateAvatar(@NotBlank(message = "{required}") @PathVariable String image) {
        User user = getCurrentUser();
        this.userService.updateAvatar(user.getUsername(), image);
        return new FebsResponse().success();
    }

    @PostMapping("theme/update")
    @ControllerEndpoint(exceptionMessage = "update system setting fail")
    public FebsResponse updateTheme(String theme, String isTab) {
        User user = getCurrentUser();
        this.userService.updateTheme(user.getUsername(), theme, isTab);
        return new FebsResponse().success();
    }


    @PostMapping("profile/update")
    @ControllerEndpoint(exceptionMessage = "update user fail")
    public FebsResponse updateProfile(User user) throws FebsException {
        User currentUser = getCurrentUser();
        user.setUserId(currentUser.getUserId());
        this.userService.updateProfile(user);
        return new FebsResponse().success();
    }

    @GetMapping("excel")
    @ControllerEndpoint(exceptionMessage = "export Excel fail")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) {
        List<User> users = this.userService.findUserDetailList(user, queryRequest).getRecords();
        ExcelKit.$Export(User.class, response).downXlsx(users, false);
    }
}
