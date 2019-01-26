package com.xuecheng.framework.domain.course.response;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * Created by admin on 208/3/5.
 */

public enum CourseCode implements ResultCode {
    COURSE_DENIED_DELETE(false,300,"删除课程失败，只允许删除本机构的课程！"),
    COURSE_PUBLISH_PERVIEWISNULL(false,3002,"还没有进行课程预览！"),
    COURSE_PUBLISH_CDETAILERROR(false,3003,"创建课程详情页面出错！"),
    COURSE_PUBLISH_COURSEIDISNULL(false,3004,"课程Id为空！"),
    COURSE_PUBLISH_VIEWERROR(false,3005,"发布课程视图出错！"),
    COURSE_MEDIS_URLISNULL(false,30,"选择的媒资文件访问地址为空！"),
    COURSE_MEDIS_NAMEISNULL(false,302,"选择的媒资文件名称为空！"),
    COURSE_NOT_DATA(false,303,"选择的媒资文件名称为空！"),
    COURSEMARKET_NOT_DATA(false,304,"课程营销信息为空！");

    //操作代码
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "2200", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;
    private CourseCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, CourseCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, CourseCode> builder = ImmutableMap.builder();
        for (CourseCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
