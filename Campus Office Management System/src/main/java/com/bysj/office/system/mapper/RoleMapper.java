package com.bysj.office.system.mapper;

import com.bysj.office.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {

    /**
     * Find user roles by user name
     *
     * @param username
     * @return
     */
    List<Role> findUserRole(String username);

    /**
     *Find role details
     *
     * @param page
     * @param role
     * @return IPage<User>
     */
    IPage<Role> findRolePage(Page page, @Param("role") Role role);
}
