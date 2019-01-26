package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程分类管理",description = "课程分类管理接口,提供课程分类的增、删、改、查")
public interface CategoryControllerApi {

    @ApiOperation("查询   分类")
    public CategoryNode findCategoryList();

}
