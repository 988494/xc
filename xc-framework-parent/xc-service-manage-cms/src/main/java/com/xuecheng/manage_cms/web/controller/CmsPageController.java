package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杨郑兴
 * @Date 209//20 22:07
 * @官网 www.weifuwukt.com
 */
@RestController
@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="页码", required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value="每页记录数",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        QueryResponseResult list = cmsPageService.findList(page, size, queryPageRequest);
        return list;
    }

    @Override
    @ApiOperation("新增页面")
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {//@RequestBody把请求过来的json数据转换成对象
        CmsPageResult cmsPageResult = cmsPageService.add(cmsPage);
        return cmsPageResult;
    }

    @Override
    @ApiOperation("根据页面id查询页面信息")
    @GetMapping("/get/{id}")
    public CmsPageResult findById(@PathVariable("id") String id) {
        CmsPage cmsPage = cmsPageService.findById(id);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    @Override
    @ApiOperation("修改页面")
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody  CmsPage cmsPage) {
        CmsPageResult cmsPageResult = cmsPageService.edit(id, cmsPage);
        return cmsPageResult;
    }

    @Override
    @ApiOperation("根据id删除页面")
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        ResponseResult responseResult = cmsPageService.delete(id);
        return responseResult;
    }
}
