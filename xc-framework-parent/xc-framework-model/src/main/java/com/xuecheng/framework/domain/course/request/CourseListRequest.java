package com.xuecheng.framework.domain.course.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 208/4/3.
 */
@Data
public class CourseListRequest extends RequestData {
    //公司Id
    private String companyId;
}
