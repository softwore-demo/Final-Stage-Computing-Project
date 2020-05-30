package com.bysj.office.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.office.base.entity.Course;
import com.bysj.office.base.entity.Leave;
import com.bysj.office.base.mapper.LeaveMapper;
import com.bysj.office.base.service.ILeaveService;
import com.bysj.office.common.entity.QueryRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public IPage<Leave> findLeaves(QueryRequest request, Leave leave) {
        Map<String,Object> condMap = new HashMap<String,Object>();

        if(leave.getName() != null && !"".contentEquals(leave.getName()))
        {
            condMap.put("name","%" + leave.getName() + "%");
        }
        Page<Leave> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findLeaveDetailPage(page,condMap);
    }

    @Override
    public List<Leave> findLeaves(Leave leave) {
	    QueryWrapper<Leave> queryWrapper = new QueryWrapper<>();
	    if(StringUtils.isNotEmpty(leave.getName())){
	        queryWrapper.eq("name",leave.getName());
        }
		// TODO
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createLeave(Leave leave) {
        this.save(leave);
    }

    @Override
    @Transactional
    public void updateLeave(Leave leave) {
        this.saveOrUpdate(leave);
    }

    @Override
    @Transactional
    public void deleteLeave(Leave leave) {
        LambdaQueryWrapper<Leave> wrapper = new LambdaQueryWrapper<>();
	    // TODO
	    this.remove(wrapper);
	}

    @Override
    public void changeStatus(Integer id, String status) {

        Leave leave = leaveMapper.selectById(id);
        leave.setStatus(status);
        leaveMapper.updateById(leave);
    }
}
