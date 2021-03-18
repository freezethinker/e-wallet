package com.ewallet.entity.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Data
public class CreateWalletRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNo;

}
