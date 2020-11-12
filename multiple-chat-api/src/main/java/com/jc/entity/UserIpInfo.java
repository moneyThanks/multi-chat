package com.jc.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserIpInfo implements Serializable {
    private String ip;
    private String address;
}
