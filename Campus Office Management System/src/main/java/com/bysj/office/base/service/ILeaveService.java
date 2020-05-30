package com.bysj.office.base.service;


import com.bysj.office.base.entity.Leave;
import com.bysj.office.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ILeaveService extends IService<Leave> {
    /**
     *
     *
     * @param request QueryRequest
     * @param leave leave
     * @return IPage<Leave>
     */
    IPage<Leave> findLeaves(QueryRequest request, Leave leave);

    /**
     *
     *
     * @param leave leave
     * @return List<Leave>
     */
    List<Leave> findLeaves(Leave leave);

    /**
     * add
     *
     * @param leave leave
     */
    void createLeave(Leave leave);

    /**
     * update
     *
     * @param leave leave
     */
    void updateLeave(Leave leave);

    /**
     * delete
     *
     * @param leave leave
     */
    void deleteLeave(Leave leave);

    void changeStatus(Integer id,String status);
}
