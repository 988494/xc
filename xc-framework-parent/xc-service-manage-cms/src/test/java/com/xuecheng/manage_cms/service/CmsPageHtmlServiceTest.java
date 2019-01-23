package com.xuecheng.manage_cms.service;

import com.xuecheng.manage_cms.ManageCmsApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CmsPageHtmlServiceTest extends ManageCmsApplicationTest {

    @Autowired
    private CmsPageHtmlService cmsPageHtmlService;

    @Test
    public void getPageHtml() {
        String pageHtml = cmsPageHtmlService.getPageHtml("5c481d4d84ce7715c477a8cf");
        System.out.println(pageHtml);
    }
}