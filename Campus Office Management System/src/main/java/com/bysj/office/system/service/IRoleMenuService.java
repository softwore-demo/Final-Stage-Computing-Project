package com.bysj.office.system.service;

import com.bysj.office.system.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * Delete by role ID
     *
     * @param roleIds
     */
    void deleteRoleMenusByRoleId(List<String> roleIds);

    /**
     * Delete by menu ID
     *
     * @param menuIds
     */
    void deleteRoleMenusByMenuId(List<String> menuIds);
}
