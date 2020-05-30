package com.bysj.office.system.service;

import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.system.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IUserService extends IService<User> {

    /**
     * Find users by user name
     *
     * @param username
     * @return
     */
    User findByName(String username);

    List<User> findAllTeacher();

    /**
     * Find user details
     *
     * @param request request
     * @param user
     * @return IPage
     */
    IPage<User> findUserDetailList(User user, QueryRequest request);

    /**
     * Find user details by user name
     *
     * @param username
     * @return
     */
    User findUserDetailList(String username);

    /**
     * Update user login time
     *
     * @param username
     */
    void updateLoginTime(String username);

    /**
     * New users
     *
     * @param user user
     */
    void createUser(User user);

    /**
     * delete user
     *
     * @param userIds
     */
    void deleteUsers(String[] userIds);

    /**
     * update user
     *
     * @param user user
     */
    void updateUser(User user);

    /**
     * rest password
     *
     * @param usernames
     */
    void resetPassword(String[] usernames);

    /**
     * register user
     *
     * @param username
     * @param password
     */
    void regist(String username, String password);

    /**
     * update password
     *
     * @param username
     * @param password
     */
    void updatePassword(String username, String password);

    /**
     * Update user image
     *
     * @param username
     * @param avatar
     */
    void updateAvatar(String username, String avatar);

    /**
     * Modify user system configuration (personalized configuration)
     *
     * @param username
     * @param theme
     * @param isTab
     */
    void updateTheme(String username, String theme, String isTab);

    /**
     * Update personal information
     *
     * @param user
     */
    void updateProfile(User user);

    /**
     *
     * @param name
     * @return
     */
    User findByRealName(String name);
}
