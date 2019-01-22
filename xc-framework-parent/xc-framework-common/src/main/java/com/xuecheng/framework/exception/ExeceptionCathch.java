package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExeceptionCathch {

    //捕获CustomException此异常
    @ExceptionHandler(CustomException.class)
    public ResponseResult customException(CustomException customException){
        //记录日志
        log.error("catch exception :{}",customException.getMessage());
        ResultCode resultCode= customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    //不可预知异常
    @ExceptionHandler(Exception.class)
    public ResponseResult customException(Exception e){
        //记录日志
        log.error("catch exception :{}",e.getMessage());
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}
