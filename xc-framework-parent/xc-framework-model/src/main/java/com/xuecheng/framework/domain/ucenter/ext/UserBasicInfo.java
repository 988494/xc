package com.xuecheng.framework.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by mrt on 208/5/2.
 */
@Data

public class UserBasicInfo {

    private String id;
    private String username;
    private String userpic;
    private String name;
    private String utype;
    //所属企业信息
    private String companyId;
    //jwt令牌
    private String jwt_token;

}
