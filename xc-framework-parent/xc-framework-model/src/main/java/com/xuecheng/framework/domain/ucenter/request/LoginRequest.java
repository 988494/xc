package com.xuecheng.framework.domain.ucenter.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by admin on 208/3/5.
 */
@Data
public class LoginRequest extends RequestData {

    String username;
    String password;
    String verifycode;

}
