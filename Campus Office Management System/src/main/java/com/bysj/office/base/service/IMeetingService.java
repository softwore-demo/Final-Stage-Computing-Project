package com.bysj.office.base.service;


import com.bysj.office.base.entity.Meeting;
import com.bysj.office.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IMeetingService extends IService<Meeting> {
    /**
     *
     *
     * @param request QueryRequest
     * @param meeting meeting
     * @return IPage<Meeting>
     */
    IPage<Meeting> findMeetings(QueryRequest request, Meeting meeting);

    /**
     *
     *
     * @param meeting meeting
     * @return List<Meeting>
     */
    List<Meeting> findMeetings(Meeting meeting);

    /**
     * add
     *
     * @param meeting meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * update
     *
     * @param meeting meeting
     */
    void updateMeeting(Meeting meeting);

    /**
     * delete
     *
     * @param meeting meeting
     */
    void deleteMeeting(Meeting meeting);


    void deleteMeeting(String[] meetings);
}
