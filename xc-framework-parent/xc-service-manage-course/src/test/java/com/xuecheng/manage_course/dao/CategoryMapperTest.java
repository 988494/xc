package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryMapperTest extends TestDao {

    @Autowired
    private CategoryMapper categoryMapper;
    @Test
    public void findAll() {
        CategoryNode all = categoryMapper.findAll();
        System.out.println(all);
    }
}