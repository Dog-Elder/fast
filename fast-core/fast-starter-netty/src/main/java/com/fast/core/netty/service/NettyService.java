package com.fast.core.netty.service;

import com.fast.core.netty.initializer.NettyChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * Netty服务
 *
 * @author 黄嘉浩
 * @date 2024/02/03
 */
@Slf4j
@Component
public class NettyService {
    /**
     * NioEventLoop并不是一个纯粹的I/O线程，它除了负责I/O的读写之外
     * 创建了两个NioEventLoopGroup，
     * 它们实际是两个独立的Reactor线程池。
     * 一个用于接收客户端的TCP连接，
     * 另一个用于处理I/O相关的读写操作，或者执行系统Task、定时任务Task等。
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Value("${yjjk.configure.socket.server.ip:0.0.0.0}")
    private String socketServerIp;

    @Value("${yjjk.configure.socket.server.port:15426}")
    private int socketServerPort;

    @Resource
    private NettyChannelInitializer initializer;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 停止服务
     */
    @PreDestroy
    public void close() {
        log.info("Shutdown Netty Server...");
        if (cf != null && cf.channel() != null) {
            cf.channel().close();
        }
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }

    private ChannelFuture cf = null;

    @PostConstruct
    public void start() {
        taskExecutor.execute(() -> {
            InetSocketAddress address = new InetSocketAddress(socketServerIp, socketServerPort);
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                        .channel(NioServerSocketChannel.class)
                        .localAddress(socketServerPort)
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .childHandler(initializer);

                cf = bootstrap.bind().sync();
                log.info(String.format("Netty server listening %s on port %s and ready for connections...", address.getHostName(), address.getPort()));
                //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
                cf.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        });
    }
}
