package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * cms页面查询api
 * @author 杨郑兴
 * @Date 209//20 6:27
 * @官网 www.weifuwukt.com
 */
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询页面列表")
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //新增页面
    @ApiOperation("新增页面")
    public CmsPageResult add(CmsPage cmsPage);

    //查询页面
    @ApiOperation("根据页面id查询页面信息")
    public CmsPageResult findById(String id);

    //修改页面
    @ApiOperation("修改页面")
    public CmsPageResult edit(String id,CmsPage cmsPage);

    //删除页面
    @ApiOperation("根据id删除页面")
    public ResponseResult delete(String id);
}
