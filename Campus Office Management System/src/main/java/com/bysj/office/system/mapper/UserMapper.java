package com.bysj.office.system.mapper;

import com.bysj.office.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    /**
     * Find users by user name
     *
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * Find user details
     *
     * @param page
     * @param user
     * @return Ipage
     */
    IPage<User> findUserDetailPage(Page page, @Param("user") User user);

    /**
     * Find user details
     *
     * @param user
     * @return List<User>
     */
    List<User> findUserDetail(@Param("user") User user);

    /**
     * find all teacher
     * @return
     */
    List<User> findAllTeacher();

}
