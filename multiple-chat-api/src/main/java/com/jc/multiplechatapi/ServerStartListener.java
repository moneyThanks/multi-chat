package com.jc.multiplechatapi;

import com.jc.handler.WebSocketHeartHandler;
import com.jc.handler.WebSocketInitHandler;
import com.jc.handler.WebSocketMsgHandler;
import com.jc.handler.WebSocketOutMsgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ServerStartListener implements CommandLineRunner {

    private EventLoopGroup master = new NioEventLoopGroup();
    private EventLoopGroup salve = new NioEventLoopGroup();

    @Value("${server.port}")
    private int port;

    @Autowired
    private WebSocketMsgHandler webSocketMsgHandler;
    @Autowired
    private WebSocketHeartHandler webSocketHeartHandler;
    @Autowired
    private WebSocketOutMsgHandler webSocketOutMsgHandler;
    @Autowired
    private WebSocketInitHandler webSocketInitHandler;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("消息中心已经启动！");
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(master, salve)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();

                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
                        pipeline.addLast(new WebSocketServerProtocolHandler("/msg"));
                        //消息超时的处理器
                        pipeline.addLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS));
                        pipeline.addLast(webSocketOutMsgHandler);
                        pipeline.addLast(webSocketMsgHandler);
                        pipeline.addLast(webSocketInitHandler);
                        pipeline.addLast(webSocketHeartHandler);

                    }
                });
        bootstrap.bind(port).sync();
        System.out.println("消息中心已经启动，端口为：" + port);
    }
}
