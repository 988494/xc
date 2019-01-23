package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class CmsPageHtmlService {

    @Autowired
    private CmsPageService cmsPageService;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    /**
     * 1、填写页面DataUrl 在编辑cms页面信息界面填写DataUrl，将此字段保存到cms_page集合中。
     * 2、静态化程序获取页面的DataUrl
     * 3、静态化程序远程请求DataUrl获取数据模型。
     * 4、静态化程序获取页面的模板信息
     * 5、执行页面静态化
     */
    //静态化页面
    public String getPageHtml(String pageId){
        //1.获取模型数据
        //根据页面id查询页面信息
        CmsPage cmsPage = cmsPageService.findById(pageId);
        if(cmsPage ==null){
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        Map model = getModelDateByPageId(cmsPage);
        if(model == null){
            //获取不到模型数据
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //2.获取页面模板
        String templateData = getTemplateByPageId(cmsPage);
        if(StringUtils.isEmpty(templateData)){
            ExceptionCast.cast(CmsCode.CMS_TEMPLATE_DATA_NULL);
        }
        //3.执行静态化
        String html = generateHtml(templateData,model);
        if(StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }
    //获取模型数据
    private Map getModelDateByPageId(CmsPage cmsPage){
        //取出页面的信息
        //取出页面的dataurl
        String dataUrl = cmsPage.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            //页面dataurl为null
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //通过getForEntity请求dataUrl获取数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map model = forEntity.getBody();
        return model;
    }

    private String getTemplateByPageId(CmsPage cmsPage){
        //获取页面的模板id
        String templateId = cmsPage.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_TEMPLATEID_NOTEXISTS);
        }
        //获取模板信息
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if(optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //获取模板文件数据
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource对象，获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            try {
                //从流中获取数据
                String data = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
                return data;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //执行静态化
    private String generateHtml(String templateData,Map model){
        //创建对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //创建模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateData);
        //向configuration中配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        try {
            Template template = configuration.getTemplate("template");
            //调用api进行静态化
            try {
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                return html;
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
