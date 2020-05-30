package com.bysj.office.base.service.impl;

import com.bysj.office.base.entity.File;
import com.bysj.office.base.mapper.FileMapper;
import com.bysj.office.base.service.IFileService;
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


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public IPage<File> findFiles(QueryRequest request, File file) {
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
        // TODO
        Page<File> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<File> findFiles(File file) {
	    LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
		// TODO
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createFile(File file) {
        this.save(file);
    }

    @Override
    @Transactional
    public void updateFile(File file) {
        this.saveOrUpdate(file);
    }

    @Override
    @Transactional
    public void deleteFile(File file) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
	    // TODO
	    this.remove(wrapper);
	}

    @Override
    public void deleteFile(String[] files) {
        List<String> list = Arrays.asList(files);
        this.removeByIds(list);
    }
}
