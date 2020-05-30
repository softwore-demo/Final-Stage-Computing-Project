package com.bysj.office.base.controller;

import com.bysj.office.base.entity.File;
import com.bysj.office.base.service.IFileService;
import com.bysj.office.common.annotation.ControllerEndpoint;
import com.bysj.office.common.controller.BaseController;
import com.bysj.office.common.entity.FebsConstant;
import com.bysj.office.common.entity.FebsResponse;
import com.bysj.office.common.entity.QueryRequest;
import com.bysj.office.common.utils.FebsUtil;
import com.bysj.office.common.utils.FileUtil;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@Controller
public class UploadController extends BaseController {

    @Autowired
    private IFileService fileService;
    @Value("${filepath}")
    private String filepath;

    @GetMapping(FebsConstant.VIEW_PREFIX + "office/upload")
    public String uploadIndex(){
        return FebsUtil.view("office/upload/uploadList");
    }


    @GetMapping(FebsConstant.VIEW_PREFIX + "office/upload/add")
    public String uploadAdd(){
        return FebsUtil.view("office/upload/upload");
    }


    @GetMapping("upload")
    @ResponseBody
    public FebsResponse getAllUploads(File file) {
        return new FebsResponse().success().data(fileService.findFiles(file));
    }

    @GetMapping("upload/list")
    @ResponseBody
    public FebsResponse uploadList(QueryRequest request, File upload) {
        Map<String, Object> dataTable = getDataTable(this.fileService.findFiles(request, upload));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "add upload", exceptionMessage = "add upload fail")
    @PostMapping("upload")
    @ResponseBody
    public FebsResponse addupload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if(file.isEmpty()){
            return new FebsResponse().fail();
        }
        String description = request.getParameter("description");//获取data中数据

        String fileName = file.getOriginalFilename();
//        String filePath = "d:/upload/";
        java.io.File dest = new java.io.File(filepath+fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File upload = new File();
        upload.setDescription(description);
        upload.setName(fileName);
        upload.setPath(fileName);
        upload.setCreatetime(new Date());
        this.fileService.createFile(upload);
        return new FebsResponse().success();
    }

    @GetMapping("download/{filename}")
    public void download(@PathVariable String filename,HttpServletResponse response) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         response = requestAttributes.getResponse();

        String type = new MimetypesFileTypeMap().getContentType(filename);

        response.setHeader("Content-type",type);

        String hehe = new String(filename.getBytes("utf-8"), "utf8");

        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        FileUtil.download(filepath+filename, response);
    }

    @ControllerEndpoint(operation = "delete upload", exceptionMessage = "delete upload fail")
    @GetMapping("upload/delete/{uploads}")
    @ResponseBody
    public FebsResponse deleteupload(@PathVariable String uploads) {
        String[] ids = uploads.split(",");
        this.fileService.deleteFile(ids);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "update upload", exceptionMessage = "update upload fail")
    @PostMapping("upload/update")
    @ResponseBody
    public FebsResponse updateupload(File upload) {
        this.fileService.updateFile(upload);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "export upload", exceptionMessage = "export Excel fail")
    @PostMapping("upload/excel")
    @ResponseBody
    public void export(QueryRequest queryRequest, File upload, HttpServletResponse response) {
        List<File> uploads = this.fileService.findFiles(queryRequest, upload).getRecords();
        ExcelKit.$Export(File.class, response).downXlsx(uploads, false);
    }
}
