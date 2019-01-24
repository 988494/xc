package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CmsPagePostService {

    @Autowired
    private CmsPageHtmlService cmsPageHtmlService;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private CmsPageService cmsPageService;
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //页面发布
    public ResponseResult post(String pageId){
        //执行静态化页面
        String pageHtml = cmsPageHtmlService.getPageHtml(pageId);
        //将页面储存到gridFs中
        CmsPage cmsPage = this.saveHtml(pageId, pageHtml);
        //站点id
        String siteId = cmsPage.getSiteId();
        //向mq发送消息
        this.sendMessage(pageId,siteId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //向mq发送消息
    private void sendMessage(String pageId,String siteId){
        //创建消息对象
        Map<Object, Object> msg = new HashMap<>();
        msg.put("pageId",pageId);
        //将map转换成json串
        String jsonString = JSON.toJSONString(msg);
        //发送mq
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,jsonString);
    }

    //保存html到GridFs中
    private CmsPage saveHtml(String pageId,String pageHtml){
        //得到页面的信息
        CmsPage cmsPage = cmsPageService.findById(pageId);
        if(StringUtils.isEmpty(cmsPage)){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        InputStream inputStream = null;
        ObjectId htmlFileId = null;
        try {
            //将html内容转换成输入流
            inputStream = IOUtils.toInputStream(pageHtml, "utf-8");
            //html内容存到gridFs中，返回Html文件id
            htmlFileId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将html文件id->htmlFileId更新到cmsPage中
        cmsPage.setHtmlFileId(htmlFileId.toHexString());
        CmsPage save = cmsPageRepository.save(cmsPage);
        return  save;
    }
}
