package com.bysj.office.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bysj.office.base.entity.Meeting;
import com.bysj.office.base.mapper.MeetingMapper;
import com.bysj.office.base.service.IMeetingService;
import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements IMeetingService {
    @Autowired
    private MeetingMapper meetingMapper;

    @Override
    public IPage<Meeting> findMeetings(QueryRequest request, Meeting meeting) {
        Map<String,Object> condMap = new HashMap<String,Object>();

        if(meeting.getPersons() != null && !"".contentEquals(meeting.getPersons()))
        {
            condMap.put("persons","%" + meeting.getPersons() + "%");
        }

        if(request.getTimeStr() != null && !"".equalsIgnoreCase(request.getTimeStr()))
        {

            String timeStr = request.getTimeStr();
            Date timeDate = DateUtil.strToDate(timeStr, "yyyy-MM-dd");
            Calendar now = Calendar.getInstance();
            now.setTime(timeDate);
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);


            Date stDate = now.getTime();
            now.add(Calendar.DATE, 1);
            Date edDate = now.getTime();

            condMap.put("stDate", stDate);
           // condMap.put("edDate", edDate);
        }
        Page<Meeting> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findMeetingDetailPage(page,condMap);
    }

    @Override
    public List<Meeting> findMeetings(Meeting meeting) {
	    LambdaQueryWrapper<Meeting> queryWrapper = new LambdaQueryWrapper<>();
		// TODO
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createMeeting(Meeting meeting) {
        this.save(meeting);
    }

    @Override
    @Transactional
    public void updateMeeting(Meeting meeting) {
        this.saveOrUpdate(meeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(Meeting meeting) {
        LambdaQueryWrapper<Meeting> wrapper = new LambdaQueryWrapper<>();
	    // TODO
	    this.remove(wrapper);
	}

    @Override
    public void deleteMeeting(String[] meetings) {
        List<String> list = Arrays.asList(meetings);
        this.removeByIds(list);
    }
}
