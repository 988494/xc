package com.xuecheng.manage_course.sevice;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CourseMarketService {

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    public CourseMarket getCourseMarketById(String courseId) {
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if(optional.isPresent()){
            CourseMarket courseMarket = optional.get();
            return courseMarket;
        }
        return null;
    }

    @Transactional
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        if(StringUtils.isEmpty(courseMarket)){
            ExceptionCast.cast(CourseCode.COURSEMARKET_NOT_DATA);
        }
        courseMarketRepository.save(courseMarket);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
