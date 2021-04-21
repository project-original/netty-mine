package com.laiyefei.project.netty.mine;

import com.laiyefei.project.netty.mine.http.MineRequest;
import com.laiyefei.project.netty.mine.http.MineResponse;
import com.laiyefei.project.netty.mine.http.MineServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-04-20 18:01
 * @Desc : bio tomcat
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class MineTomcat {

    private Integer port = 8080;
    private Properties webConfig = new Properties();
    private ServerSocket serverSocket;
    private Map<String, MineServlet> container = new HashMap<String, MineServlet>();


    public static void main(String[] args) {
        MineTomcat.start();
    }

    public static final void start() {
        try {
            new MineTomcat().doStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void doStart() throws Exception {
        //1. 加载配置
        init();
        //2. 启动服务
        server();
    }

    private final void server() throws Exception {

        //boss 线程
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        //work 线程
        final EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建Netty服务端对象
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                //配置主线程的处理逻辑
                .channel(NioServerSocketChannel.class)
                //子线程的回调处理
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        //处理回调逻辑
                        channel.pipeline()
                                .addLast(new HttpResponseEncoder())
                                .addLast(new HttpRequestDecoder())
                                .addLast(new MineTomcatHandler());
                    }
                })
                //配置主线程分配的最大线程数
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = serverBootstrap.bind(this.port).sync();
        System.out.println("the netty tomcat is start, server in port " + this.port);

        future.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    private final void init() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        webConfig.load(MineTomcat.class.getResourceAsStream("/web.properties"));
        for (final Object item : webConfig.keySet()) {
            final String key = item.toString();
            if (!key.endsWith(".url")) {
                continue;
            }
            final String theServletPreFix = key.replaceAll("\\.url$", "");
            final String url = webConfig.getProperty(key);
            final String theServletClassName = webConfig.getProperty(theServletPreFix.concat(".className"));
            container.put(url, (MineServlet) Class.forName(theServletClassName).newInstance());
        }
    }

    public final class MineTomcatHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //责任分配
            if (msg instanceof HttpRequest) {
                final HttpRequest httpRequest = (HttpRequest) msg;
                final MineRequest mineRequest = new MineRequest(ctx, httpRequest);
                final MineResponse mineResponse = new MineResponse(ctx, httpRequest);

                final String url = mineRequest.getURL();
                if (container.containsKey(url)) {
                    container.get(url).service(mineRequest, mineResponse);
                } else {
                    mineResponse.write("404 Not Found");
                }
            }
        }
    }
}
