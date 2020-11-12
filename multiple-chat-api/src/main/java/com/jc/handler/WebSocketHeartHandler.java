package com.jc.handler;

import com.jc.entity.UserIpInfo;
import com.jc.entity.WsMsgEntity;
import com.jc.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 心跳消息处理器
 */
@Component
@ChannelHandler.Sharable
public class WebSocketHeartHandler extends SimpleChannelInboundHandler<WsMsgEntity> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsMsgEntity wsMsgEntity) throws Exception {
        if (wsMsgEntity.getType() == 2) {
            //心跳消息
            //返回一个消息
            ctx.writeAndFlush(wsMsgEntity);
        } else {
            //如果不是心跳消息就继续往后传递 （也返回）
            Map<UserIpInfo, Channel> map = ChannelUtil.map();
            for (UserIpInfo userIpInfo : map.keySet()) {
                Channel channel = map.get(userIpInfo);
                channel.writeAndFlush(wsMsgEntity);
            }
            //map.forEach();
            //ctx.writeAndFlush(wsMsgEntity);
            //ctx.fireChannelRead(wsMsgEntity);
        }
    }
}
