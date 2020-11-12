package com.jc.entity;

import java.util.HashMap;
import java.util.Map;

public class DemoTest {
    public static void main(String[] args) {
        Map<UserIpInfo, String> map = new HashMap();
        UserIpInfo userIpInfo = new UserIpInfo();
        userIpInfo.setIp("1");
        map.put(userIpInfo, "1");
        UserIpInfo userIpInfo2 = new UserIpInfo();
        userIpInfo2.setIp("1");
        map.put(userIpInfo2, "1");
        for(Map.Entry<UserIpInfo, String> entry : map.entrySet()){
            UserIpInfo mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey+":"+mapValue);
        }
    }
}
