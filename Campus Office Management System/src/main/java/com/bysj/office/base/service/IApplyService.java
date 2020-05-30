package com.bysj.office.base.service;


import com.bysj.office.base.entity.Apply;
import com.bysj.office.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IApplyService extends IService<Apply> {
    /**
     *
     *
     * @param request QueryRequest
     * @param apply apply
     * @return IPage<Apply>
     */
    IPage<Apply> findApplys(QueryRequest request, Apply apply);

    /**
     *
     *
     * @param apply apply
     * @return List<Apply>
     */
    List<Apply> findApplys(Apply apply);

    /**
     * add
     *
     * @param apply apply
     */
    void createApply(Apply apply);

    /**
     * update
     *
     * @param apply apply
     */
    void updateApply(Apply apply);

    /**
     * delete
     *
     * @param apply apply
     */
    void deleteApply(Apply apply);

    void deleteApplys(String[] ids);
}
