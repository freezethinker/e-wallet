package com.ewallet.controller;

import com.ewallet.entity.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Slf4j
public abstract class BaseController {

    public static ServiceResponse createErrorResponse(Exception exception) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(1);
        serviceResponse.setStatusMessage(exception.getMessage());
        serviceResponse.setData(null);
        log.info("Response: {}", serviceResponse.toString());
        return serviceResponse;
    }

    public static ServiceResponse createSuccessResponse(Object result) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setStatusCode(0);
        serviceResponse.setStatusMessage("Successful");
        serviceResponse.setData(result);
        log.info("Response: {}", serviceResponse.toString());
        return serviceResponse;
    }
}
