package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.manage_cms.ManageCmsApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.Assert.*;

public class CmsConfigConfigTest extends ManageCmsApplicationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getModel() {
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a7bdd8bd019f1162c63ad32", Map.class);
        Map body = forEntity.getBody();
        System.out.println(body);
    }
}