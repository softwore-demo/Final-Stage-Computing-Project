package com.bysj.office.base.controller;

import com.bysj.office.base.entity.File;
import com.bysj.office.base.service.IFileService;
import com.bysj.office.common.annotation.ControllerEndpoint;
import com.bysj.office.common.utils.FebsUtil;
import com.bysj.office.common.entity.FebsConstant;
import com.bysj.office.common.controller.BaseController;
import com.bysj.office.common.entity.FebsResponse;
import com.bysj.office.common.entity.QueryRequest;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Slf4j
@Validated
@Controller
public class FileController extends BaseController {

    @Autowired
    private IFileService fileService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "file")
    public String fileIndex(){
        return FebsUtil.view("file/file");
    }

    @GetMapping("file")
    @ResponseBody
    public FebsResponse getAllFiles(File file) {
        return new FebsResponse().success().data(fileService.findFiles(file));
    }

    @GetMapping("file/list")
    @ResponseBody
    public FebsResponse fileList(QueryRequest request, File file) {
        Map<String, Object> dataTable = getDataTable(this.fileService.findFiles(request, file));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "add File", exceptionMessage = "add File fail")
    @PostMapping("file")
    @ResponseBody
    public FebsResponse addFile(@Valid File file) {
        this.fileService.createFile(file);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "delete File", exceptionMessage = "delete File fail")
    @GetMapping("file/delete")
    @ResponseBody
    public FebsResponse deleteFile(File file) {
        this.fileService.deleteFile(file);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "update File", exceptionMessage = "update File fail")
    @PostMapping("file/update")
    @ResponseBody
    public FebsResponse updateFile(File file) {
        this.fileService.updateFile(file);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "export File", exceptionMessage = "export Excel fail")
    @PostMapping("file/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, File file, HttpServletResponse response) {
        List<File> files = this.fileService.findFiles(queryRequest, file).getRecords();
        ExcelKit.$Export(File.class, response).downXlsx(files, false);
    }
}
