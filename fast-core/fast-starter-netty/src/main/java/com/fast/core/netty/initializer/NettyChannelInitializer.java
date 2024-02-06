package com.fast.core.netty.initializer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Netty 通道初始值设定项
 *
 * @author 黄嘉浩
 * @date 2024/02/06
 */
@Component
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Resource
    private List<ChannelHandler> handlers;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        handlers.forEach(ch.pipeline()::addFirst);
    }
}