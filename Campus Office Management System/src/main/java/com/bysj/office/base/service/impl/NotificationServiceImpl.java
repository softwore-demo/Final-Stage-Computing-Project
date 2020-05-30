package com.bysj.office.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.office.base.entity.Meeting;
import com.bysj.office.base.entity.Notification;
import com.bysj.office.base.mapper.NotificationMapper;
import com.bysj.office.base.service.INotificationService;
import com.bysj.office.common.entity.QueryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements INotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public IPage<Notification> findNotifications(QueryRequest request, Notification notification) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(notification.getTitle())){
            queryWrapper.like("title",notification.getTitle());
        }
        // TODO
        Page<Notification> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findNotificationDetailPage(page,notification);
    }

    @Override
    public List<Notification> findNotifications(Notification notification) {
	    LambdaQueryWrapper<Notification> queryWrapper = new LambdaQueryWrapper<>();
		// TODO
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createNotification(Notification notification) {
        this.save(notification);
    }

    @Override
    @Transactional
    public void updateNotification(Notification notification) {
        this.saveOrUpdate(notification);
    }

    @Override
    @Transactional
    public void deleteNotification(Notification notification) {
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
	    // TODO
	    this.remove(wrapper);
	}

    @Override
    public void deleteNotifications(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }
}
