package com.bysj.office.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bysj.office.base.entity.Meeting;
import com.bysj.office.base.entity.Notification;
import org.apache.ibatis.annotations.Param;


public interface NotificationMapper extends BaseMapper<Notification> {


    IPage<Notification> findNotificationDetailPage(Page page, @Param("notification") Notification notification);

}
