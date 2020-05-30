package com.bysj.office.system.service;

import com.bysj.office.common.entity.MenuTree;
import com.bysj.office.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IMenuService extends IService<Menu> {

    /**
     * Find user permission set
     *
     * @param username
     * @return
     */
    List<Menu> findUserPermissions(String username);

    /**
     * Find user menu collection
     *
     * @param username
     * @return
     */
    MenuTree<Menu> findUserMenus(String username);

    /**
     * Find all menus / buttons (tree structure)
     *
     * @return MenuTree<Menu>
     */
    MenuTree<Menu> findMenus(Menu menu);

    /**
     * Find all menus / buttons
     *
     * @return MenuTree<Menu>
     */
    List<Menu> findMenuList(Menu menu);

    /**
     * add menu
     *
     * @param menu
     */
    void createMenu(Menu menu);

    /**
     * update menu
     *
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * delete menu
     *
     * @param menuIds
     */
    void deleteMeuns(String menuIds);
}
