package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.manage_cms.ManageCmsApplicationTest;;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CmsPageRepositoryTest extends ManageCmsApplicationTest {
    @Autowired
    private  CmsPageRepository cmsPageRepository;

    @Test
    public void fandAll(){
        List<CmsPage> list = cmsPageRepository.findAll();
        Assert.assertEquals(true,list.size()>0);
    }
    //分页
    @Test
    public void findPage(){
        PageRequest.of(1,2);
        List<CmsPage> list = cmsPageRepository.findAll();
        Assert.assertEquals(true,list.size()>0);
    }

    //分页
    @Test
    public void testInsert(){
        //定义实体类     
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        ArrayList<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        CmsPage save = cmsPageRepository.save(cmsPage);
        Assert.assertNotNull(save);
    }
    //修改
    @Test
    public void update(){
        Optional<CmsPage> optional = cmsPageRepository.findById("5c457a4984ce772008004f95");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageAliase("test123测试");
            CmsPage save = cmsPageRepository.save(cmsPage);
            Assert.assertNotNull(save);
        }
    }
    //删除
    @Test
    public void delete(){
      cmsPageRepository.deleteById("5c457a4984ce772008004f95");
    }
    //自定义查询
    @Test
    public void findAllByExample(){
      //分页参数
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        //要查询5a751fab6abb5044e0d19ea1站点的页面
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("5a962b52b00ffc514038faf7");
        //条件匹配器
        ExampleMatcher exampleMatcher =  ExampleMatcher.matching();
        //定义Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        List<CmsPage> list = cmsPageRepository.findAll(example);
        Assert.assertEquals(true,list.size()>0);
    }
}