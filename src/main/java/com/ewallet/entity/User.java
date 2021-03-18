package com.ewallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by karan.uppal on 18/03/21
 **/
@Data
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String phoneNo;
}
