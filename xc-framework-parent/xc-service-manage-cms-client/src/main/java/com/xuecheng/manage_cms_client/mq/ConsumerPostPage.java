package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
public class ConsumerPostPage {

    private static final Logger log = LoggerFactory.getLogger(PageService.class);

    @Autowired
    private PageService pageService;

    @RabbitListener(queues = {"${weifuwukt.mq.queue}"})
    public void postPage(String msg){
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        //得到消息中的页面id
        String pageId = (String) map.get("pageId");
        //检验页面是否合法
        CmsPage cmsPage = pageService.findCmsPagById(pageId);
        if(StringUtils.isEmpty(cmsPage)){
            log.error("接受的mq消息的cmsPage为空，pageId={}",pageId);
            return;
        }
        //将页面从GridFs中下载到服务器
        pageService.savePageToServerPath(pageId);

    }
}
