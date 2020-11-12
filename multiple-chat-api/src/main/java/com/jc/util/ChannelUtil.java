package com.jc.util;

import com.jc.entity.UserIpInfo;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelUtil {

    private static Map<UserIpInfo, Channel> map = new ConcurrentHashMap<>();

    public static void add(UserIpInfo user, Channel channel) {
        map.put(user, channel);
    }

    public static Channel getChannel(UserIpInfo user) {
        return map.get(user);
    }

    public static void removeChannel(UserIpInfo user) {
        map.remove(user);
    }

    public static UserIpInfo getUid(Channel channel) {
        for (Map.Entry<UserIpInfo, Channel> entry : map.entrySet()) {
            if (entry.getValue() == channel) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Map<UserIpInfo, Channel> map() {
        return map;
    }
}
