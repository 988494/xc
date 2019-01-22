package com.xuecheng.framework.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by mrt on 208/5/2.
 */
@Data

public class UserToken{
    String userId;//用户id
    String utype;//用户类型
    String companyId;//用户所属企业信息
}
