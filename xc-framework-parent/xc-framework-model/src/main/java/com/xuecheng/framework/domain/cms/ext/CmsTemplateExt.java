package com.xuecheng.framework.domain.cms.ext;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 208//24 0:04.
 * @Modified By:
 */
@Data
public class CmsTemplateExt extends CmsTemplate {

    //模版内容
    private String templateValue;

}
