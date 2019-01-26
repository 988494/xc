package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDicthinaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
@Api(value = "数据字典接口",description = "提供数据字典接口的管理、查询功能")
public class SysDicthinaryController implements SysDicthinaryControllerApi {

    @Autowired
    private SysDicthinaryService sysDicthinaryService;

    //数据字典
    @Override
    @ApiOperation(value = "数据字典查询接口")
    @GetMapping("/dictionary/get/{dType}")
    public SysDictionary getByType(@PathVariable("dType") String type) {
        SysDictionary sysDictionary = sysDicthinaryService.getByType(type);
        return sysDictionary;
    }
}
