package com.fast.core.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf byteBuf) {
            String text = byteBuf.toString(CharsetUtil.UTF_8); // 将ByteBuf转换为字符串
            log.info("Netty接收到的消息: {}", text);
            // 在这里处理文本消息
        } else {
            // 如果消息不是ByteBuf类型，可以根据需要进行其他处理
            log.warn("Netty接收到的消息不是ByteBuf类型: {}", msg);
        }
    }
}

