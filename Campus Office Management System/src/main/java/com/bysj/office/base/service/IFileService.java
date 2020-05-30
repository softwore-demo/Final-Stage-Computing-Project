package com.bysj.office.base.service;


import com.bysj.office.base.entity.File;
import com.bysj.office.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IFileService extends IService<File> {
    /**
     *
     *
     * @param request QueryRequest
     * @param file file
     * @return IPage<File>
     */
    IPage<File> findFiles(QueryRequest request, File file);

    /**
     *
     *
     * @param file file
     * @return List<File>
     */
    List<File> findFiles(File file);

    /**
     * add
     *
     * @param file file
     */
    void createFile(File file);

    /**
     * update
     *
     * @param file file
     */
    void updateFile(File file);

    /**
     * delete
     *
     * @param file file
     */
    void deleteFile(File file);

    void deleteFile(String[] files);
}
