package com.jc.handler;

import com.alibaba.fastjson.JSON;
import com.jc.entity.WsMsgEntity;
import com.jc.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * 自定义消息处理器
 */
@Component
@ChannelHandler.Sharable
public class WebSocketMsgHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端连接");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端断开连接");
        Channel channel = ctx.channel();
        ChannelUtil.removeChannel(ChannelUtil.getUid(channel));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //System.out.println("接收到WebSocket的数据：" + textWebSocketFrame.text());
        String msg = textWebSocketFrame.text();
        //将消息字符串转换成实体类
        WsMsgEntity wsMsgEntity = JSON.parseObject(msg, WsMsgEntity.class);
        //往后传递
        ctx.fireChannelRead(wsMsgEntity);
    }
}
