package com.xuecheng.api.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "cms存储模板文件接口",description = "cms存储模板文件，把模板文件存储到mongodb GridFs中")
public interface CmsGridFsApi {

    //存储模板文件
    @ApiOperation("把模板文件存储到mongodb GridFs中")
    public Object save();

}
