package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
   //根据课程id和parentId查询teachplan
   //SELECT * FROM teachplan a WHERE a.courseid = "297e7c7c62b888f00162b8a965510001" AND a.parentid = "0"
   public List<Teachplan> findByParentidAndCourseid(String parentid,String courseid);
}
