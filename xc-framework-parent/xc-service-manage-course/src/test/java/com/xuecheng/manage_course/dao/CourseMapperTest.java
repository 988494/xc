package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CourseMapperTest extends TestDao {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void findCourseListPage() {
        PageHelper.startPage(1,10);
        CourseListRequest courseListRequest = new CourseListRequest();
        Page<CourseInfo> page = courseMapper.findCourseListPage(courseListRequest);
        List<CourseInfo> list = page.getResult();
        System.out.println(list);
    }
}