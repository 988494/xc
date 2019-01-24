package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsGridFsApi;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Api(value = "cms存储模板文件接口",description = "cms存储模板文件，把模板文件存储到mongodb GridFs中")
@RestController
@Slf4j
public class CmsGridFsController implements CmsGridFsApi {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    //存储模板文件
    @Override
    @GetMapping("/gridfs")
    public Object save() {
        //要存储的文件
        File file = new File("F:\\xc\\xcUIService\\xc-framework-parent\\xc-service-manage-cms\\src\\main\\resources\\templates\\index_banner.ftl");
        //定义输入流
        FileInputStream fileInputStream = null;
        try {
             fileInputStream  = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(fileInputStream,"轮播图测试文件01","");
        log.info("objectId={}",objectId);
        //得到文件ID
        String fileId = objectId.toString();
        log.info("fileId={}",fileId);
        Map<String,Object> map = new HashMap<>();
        map.put("objectId",objectId);
        map.put("fileId",fileId);
        return map;
    }
}
