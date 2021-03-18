package com.ewallet.entity.response;

import lombok.Data;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Data
public class ServiceResponse {
    private int statusCode;
    private String statusMessage;
    private Object data;
}
