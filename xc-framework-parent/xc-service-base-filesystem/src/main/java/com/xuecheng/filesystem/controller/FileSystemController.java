package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FilesystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/filesystem")
@Api(value = "文件管理接口",description = "文件管理接口,提供文件的增、删、改、查")
public class FileSystemController implements FilesystemControllerApi {

    @Autowired
    private FileSystemService fileSystemService;

    @Override
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public UploadFileResult upload(@RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "filetag",required = true) String filetag,
                                   @RequestParam(value = "businesskey",required = false) String businesskey,
                                   @RequestParam(value = "metedata",required = false) String metadata) {
        return fileSystemService.upload(file,filetag,businesskey,metadata);
    }
}
