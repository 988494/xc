package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysDicthinaryService {

    @Autowired
    private SysDicthinaryRepository sysDicthinaryRepository;

    public SysDictionary getByType(String dType) {
        if(StringUtils.isEmpty(dType)){
            //字典传入参数为null
            ExceptionCast.cast(CmsCode.CMS_SYS_DICTIONARY_DTYPE_NULL);
        }
        SysDictionary sysDictionary = sysDicthinaryRepository.findByDType(dType);
        return sysDictionary;
    }
}
