package com.jc.handler;

import com.alibaba.fastjson.JSONObject;
import com.jc.entity.UserIpInfo;
import com.jc.entity.WsMsgEntity;
import com.jc.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * 初始化消息处理器
 */
@Component
@ChannelHandler.Sharable
public class WebSocketInitHandler extends SimpleChannelInboundHandler<WsMsgEntity> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsMsgEntity wsMsgEntity) throws Exception {
        if (wsMsgEntity.getType() == 1) {
            //初始化消息
            //UserIpInfo user = (UserIpInfo) wsMsgEntity.getData();
            JSONObject jsonObject = (JSONObject) wsMsgEntity.getData();
            UserIpInfo user = jsonObject.toJavaObject(UserIpInfo.class);
            //获得客户端对应的Channel
            Channel channel = ctx.channel();
            //管理当前用户id - channel的映射关系
            ChannelUtil.add(user, channel);
        } else {
            //如果不是初始化消息就继续往后传递
            ctx.fireChannelRead(wsMsgEntity);
        }
    }
}
