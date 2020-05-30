package com.bysj.office.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.office.base.entity.Apply;
import com.bysj.office.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;



public interface ApplyMapper extends BaseMapper<Apply> {


    IPage<Apply> findApplyDetailPage(Page page, @Param("condMap") Map<String,Object> condMap);

}
