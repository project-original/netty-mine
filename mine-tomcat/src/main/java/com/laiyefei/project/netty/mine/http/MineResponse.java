package com.laiyefei.project.netty.mine.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-04-20 18:01
 * @Desc : the mine response
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class MineResponse {

    private final ChannelHandlerContext context;
    private final HttpRequest httpRequest;

    public MineResponse(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.context = ctx;
        this.httpRequest = httpRequest;
    }

    public void write(String content) throws Exception {
        if (null == content || content.length() <= 0) {
            return;
        }
        final FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
        fullHttpResponse.headers().set("Content-Type", "text/html");
        this.context.write(fullHttpResponse);
        this.context.flush();
        this.context.close();
    }
}
