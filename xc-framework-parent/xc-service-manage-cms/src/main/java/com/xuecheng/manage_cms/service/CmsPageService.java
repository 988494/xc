package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StringUtils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsPageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    public QueryResponseResult findList(int page,int size, QueryPageRequest queryPageRequest) {
        if(queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }
        //条件匹配器         
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());//包含,pageAliase值进行模糊查询
        //条件对象
        CmsPage cmsPage = new CmsPage();
        //站点id
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //页面别名
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //站点id
        if(page <= 0){
            page = 1;
        }
        //前端过来默认是从1页开始
        page = page -1;
        if(size <= 0){
            size = 10;
        }
        //分页对象
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> list = cmsPageRepository.findAll(example,pageable);
        QueryResult<CmsPage> queryResult = new QueryResult<CmsPage>();
        queryResult.setList(list.getContent());//数据列表
        queryResult.setTotal(list.getTotalElements());//数据总记录数
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    //新增页面
    public CmsPageResult add(CmsPage cmsPage) {
        if(cmsPage == null){
            //请求参数为空
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        //在cms_page集中上创建页面名称、站点Id、页面webpath为唯一索引 来校验页面唯一性
        //根据页面名称、站点id、页面webpath，查询cms_page集合，如果查询到说明页面已经存在，如果查询不到再继续添加
        CmsPage result = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (result != null){
            //添加页面失败
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //调用dao新增界面
        cmsPage.setPageId(null);//PageId主键，这里让mongodb自动生成对象
        cmsPage = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    //根据页面id查询页面信息
    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    //修改页面
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        //根据id从数据库中查询数据、
        CmsPage reslut = this.findById(id);
        if(reslut!=null){
            //准备更新数据
            //设置需要修改的数据
            if(StringUtils.isNotEmpty(cmsPage.getTemplateId())){
                //更新模板id
                reslut.setTemplateId(cmsPage.getTemplateId());
            }
            if(StringUtils.isNotEmpty(cmsPage.getSiteId())){
                //更新站点id
                reslut.setSiteId(cmsPage.getSiteId());
            }
            if(StringUtils.isNotEmpty(cmsPage.getPageAliase())){
                //更新页面别名
                reslut.setPageAliase(cmsPage.getPageAliase());
            }
            if(StringUtils.isNotEmpty(cmsPage.getPageName())){
                //更新页面名称
                reslut.setPageName(cmsPage.getPageName());
            }
            if(StringUtils.isNotEmpty(cmsPage.getPageWebPath())){
                //更新访问路径
                reslut.setPageWebPath(cmsPage.getPageWebPath());
            }
            if(StringUtils.isNotEmpty(cmsPage.getPagePhysicalPath())){
                //更新物理路径
                reslut.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            }
            CmsPage save = cmsPageRepository.save(reslut);
            return  new CmsPageResult(CommonCode.SUCCESS,save);
        }
        //修改失败
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    //根据id删除页面
    public ResponseResult delete(String id) {
        if(StringUtils.isNotEmpty(id)){
            //删除之前查一下
            CmsPage cmsPage = this.findById(id);
            if(cmsPage ==null){
                //删除失败
              return  new ResponseResult(CommonCode.FAIL);
            }
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }else {
            //删除失败
           return new ResponseResult(CommonCode.FAIL);
        }
    }
}
