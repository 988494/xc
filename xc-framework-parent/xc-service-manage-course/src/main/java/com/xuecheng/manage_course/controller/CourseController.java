package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.sevice.CourseMarketService;
import com.xuecheng.manage_course.sevice.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@Api(value = "课程管理接口",description = "课程管理接口,提供课程的增、删、改、查")
public class CourseController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMarketService courseMarketService;

    //课程查询计划
    @Override
    @ApiOperation("课程查询计划")
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        TeachplanNode teachplanNode = courseService.findTeachplanList(courseId);
        return teachplanNode;
    }

    @Override
    @ApiOperation("添加课程计划")
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @Override
    @ApiOperation("查询我的所有课程")
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseListPage(@PathVariable("page") int page,@PathVariable("size") int size,CourseListRequest courseListRequest) {
        QueryResponseResult queryResponseResult = courseService.findCourseListPage(page,size,courseListRequest);
        return queryResponseResult;
    }

    @Override
    @ApiOperation("新增课程")
    @PostMapping("/coursebase/add")
    public ResponseResult addCourseBase(@RequestBody  CourseBase courseBase){
        ResponseResult responseResult = courseService.addCourseBase(courseBase);
        return responseResult;
    }

    @Override
    @ApiOperation("根据课程id，获取课程基础信息")
    @GetMapping("/coursebase/{courseid}")
    public CourseBase getCourseBaseById(@PathVariable("courseid") String courseid) {
        CourseBase courseBase = courseService.getCourseBaseById(courseid);
        return courseBase;
    }

    @Override
    @ApiOperation("更新课程基础信息")
    @PostMapping("/coursebase/{id}")
    public ResponseResult updateCourseBase(@PathVariable("id") String id, @RequestBody CourseBase courseBase) {
        ResponseResult responseResult = courseService.updateCourseBase(id,courseBase);
        return responseResult;
    }

    @Override
    @ApiOperation("获取课程营销信息")
    @GetMapping("/coursemarket/{courseid}")
    public CourseMarket getCourseMarketById(@PathVariable("courseid") String courseId) {
        CourseMarket courseMarket = courseMarketService.getCourseMarketById(courseId);
        return courseMarket;
    }

    @Override
    @ApiOperation("更新课程营销信息")
    @PostMapping("/coursemarket/{id}")
    public ResponseResult updateCourseMarket(String id, @RequestBody  CourseMarket courseMarket) {
        ResponseResult responseResult= courseMarketService.updateCourseMarket(id,courseMarket);
        return responseResult;
    }
}
