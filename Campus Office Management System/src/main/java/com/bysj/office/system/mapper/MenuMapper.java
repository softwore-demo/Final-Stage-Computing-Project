package com.bysj.office.system.mapper;

import com.bysj.office.system.entity.Menu;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findUserPermissions(String username);


    List<Menu> findUserMenus(String username);
}
