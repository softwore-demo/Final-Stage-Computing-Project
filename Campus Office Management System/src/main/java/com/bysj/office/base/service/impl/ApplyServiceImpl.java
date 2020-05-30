package com.bysj.office.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.office.base.entity.Apply;
import com.bysj.office.base.mapper.ApplyMapper;
import com.bysj.office.base.service.IApplyService;
import com.bysj.office.common.entity.FebsConstant;
import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.common.utils.SortUtil;
import com.bysj.office.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public IPage<Apply> findApplys(QueryRequest request, Apply apply) {
        Map<String,Object> condMap = new HashMap<String,Object>();

        if(apply.getName() != null && !"".contentEquals(apply.getName()))
        {
            condMap.put("name","%" + apply.getName() + "%");
        }
        if(apply.getOperatorid() != null)
        {
            condMap.put("operatorid",apply.getOperatorid());
        }
        Page<Apply> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findApplyDetailPage(page,condMap);
    }

    @Override
    public List<Apply> findApplys(Apply apply) {
	    QueryWrapper<Apply> queryWrapper = new QueryWrapper<>();
	    if(apply.getOperatorid()!=null && apply.getOperatorid()!=0){
            queryWrapper.eq("operatorid",apply.getOperatorid());
        }
		// TODO
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createApply(Apply apply) {
        this.save(apply);
    }

    @Override
    @Transactional
    public void updateApply(Apply apply) {
        this.saveOrUpdate(apply);
    }

    @Override
    @Transactional
    public void deleteApply(Apply apply) {
        LambdaQueryWrapper<Apply> wrapper = new LambdaQueryWrapper<>();
	    // TODO
	    this.remove(wrapper);
	}

    @Override
    public void deleteApplys(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }
}
