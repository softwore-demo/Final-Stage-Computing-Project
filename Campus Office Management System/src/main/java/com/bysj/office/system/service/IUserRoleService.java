package com.bysj.office.system.service;

import com.bysj.office.system.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IUserRoleService extends IService<UserRole> {

    /**
     * Delete by role ID
     *
     * @param roleIds
     */
    void deleteUserRolesByRoleId(List<String> roleIds);

    /**
     * Delete by user ID
     *
     * @param userIds
     */
    void deleteUserRolesByUserId(List<String> userIds);
}
