package com.xuecheng.manage_course.sevice;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachplanRepository teachplanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMapper courseMapper;

    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }
    //添加课程计划
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        if(StringUtils.isEmpty(teachplan)||
            StringUtils.isEmpty(teachplan.getCourseid())||
            StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //课程id
        String courseid = teachplan.getCourseid();
        //parentId
        String parentid = teachplan.getParentid();
        //处理parentId
        if(StringUtils.isEmpty(parentid)){
            parentid = getTeachplanList(courseid);
        }
        Optional<Teachplan> optionalTeachplan = teachplanRepository.findById(parentid);
        Teachplan parentNode = optionalTeachplan.get();
        //父节点的级别
        String grade = parentNode.getGrade();
        Teachplan teachplanNew = new Teachplan();
        //将页面提交的teachplan信息拷贝到teachplanNew中去
        BeanUtils.copyProperties(teachplan,teachplanNew);
        teachplanNew.setParentid(parentid);
        if(grade.equals("1")){
            teachplanNew.setGrade("2");
        }else{
            teachplanNew.setGrade("3");
        }
        Teachplan save = teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }
        //查询课程的根节点，如果查询不到，要自动添加跟节点
    private String getTeachplanList(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        //课程信息
        CourseBase courseBase = optional.get();
        //查询课程的根节点
        List<Teachplan> list = teachplanRepository.findByParentidAndCourseid(courseId, "0");
        if(list == null ||list.size()<=0){
            //查询不到，要自动添加根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");//跟节点默认是0
            teachplan.setGrade("1");//级别:根节点是1，跟节点下的节点是2，跟节点下的节点的节点是3
            teachplan.setPname(courseBase.getName());//课程名称
            teachplan.setStatus("0");//0为未发布，1为发布
            Teachplan save = teachplanRepository.save(teachplan);
            return save.getId();
        }
        return list.get(1).getId();
    }
    //查询我的课程
    public QueryResponseResult findCourseListPage(int page, int size, CourseListRequest courseListRequest){
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        List<CourseInfo> result = courseListPage.getResult();
        QueryResult<CourseInfo> queryResult = new QueryResult<CourseInfo>();
        queryResult.setList(courseListPage.getResult());
        queryResult.setTotal(courseListPage.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    //新增课程
    public ResponseResult addCourseBase(CourseBase courseBase) {
        if(StringUtils.isEmpty(courseBase)){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_CDETAILERROR);
        }
        CourseBase courseBaseNew = new CourseBase();
        BeanUtils.copyProperties(courseBase,courseBaseNew);
        CourseBase save = courseBaseRepository.save(courseBaseNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CourseBase getCourseBaseById(String courseid) {
        if(StringUtils.isEmpty(courseid)){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            return  courseBase;
        }
        return null;
    }

    // 更新课程基础信息
    public ResponseResult updateCourseBase(String id, CourseBase courseBase) {
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        //根据课程id，从数据库中查询，是否有数据
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        if(!optional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_NOT_DATA);
        }
//        CourseBase courseBaseOld = optional.get();
//        BeanUtils.copyProperties(courseBase,courseBaseOld);
        courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //查询课程分类

}
