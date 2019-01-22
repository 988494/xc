package com.xuecheng.framework.domain.order.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 208/3/26.
 */
@Data
public class CreateOrderRequest extends RequestData {

    String courseId;

}
