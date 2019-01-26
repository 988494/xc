package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.sevice.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Api(value = "课程分类接口",description = "课程分类接口,提供课程分类的增、删、改、查")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    private CategoryService categoryService;

    @Override
    @ApiOperation("查询课程分类")
    @GetMapping("/list")
    public CategoryNode findCategoryList(){
        CategoryNode CategoryNode = categoryService.findCategoryList();
        return CategoryNode;
    }
}
