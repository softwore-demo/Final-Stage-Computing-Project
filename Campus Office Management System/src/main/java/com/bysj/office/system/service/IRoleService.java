package com.bysj.office.system.service;

import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.system.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IRoleService extends IService<Role> {


    /**
     * Find user roles by user name
     *
     * @param username
     * @return
     */
    List<Role> findUserRole(String username);

    /**
     * Find all roles
     *
     * @param role
     * @return
     */
    List<Role> findRoles(Role role);

    /**
     * Find all roles (pagination)
     *
     * @param role
     * @param request request
     * @return IPage
     */
    IPage<Role> findRoles(Role role, QueryRequest request);

    /**
     * Find the corresponding role by role name
     *
     * @param roleName
     * @return
     */
    Role findByName(String roleName);

    /**
     * add role
     *
     * @param role
     */
    void createRole(Role role);

    /**
     * update  role
     *
     * @param role
     */
    void updateRole(Role role);


    /**
     * delete role
     *
     * @param roleIds
     */
    void deleteRoles(String roleIds);
}
