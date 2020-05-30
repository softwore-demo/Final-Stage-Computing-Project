package com.bysj.office.base.service;


import com.bysj.office.base.entity.Notification;
import com.bysj.office.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface INotificationService extends IService<Notification> {
    /**
     *
     *
     * @param request QueryRequest
     * @param notification notification
     * @return IPage<Notification>
     */
    IPage<Notification> findNotifications(QueryRequest request, Notification notification);

    /**
     *
     *
     * @param notification notification
     * @return List<Notification>
     */
    List<Notification> findNotifications(Notification notification);

    /**
     * add
     *
     * @param notification notification
     */
    void createNotification(Notification notification);

    /**
     * update
     *
     * @param notification notification
     */
    void updateNotification(Notification notification);

    /**
     * delete
     *
     * @param notification notification
     */
    void deleteNotification(Notification notification);

    void deleteNotifications(String[] ids);
}
