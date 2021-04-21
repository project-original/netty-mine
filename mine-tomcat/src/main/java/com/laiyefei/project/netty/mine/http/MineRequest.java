package com.laiyefei.project.netty.mine.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-04-20 18:01
 * @Desc : the mine request
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class MineRequest {

    private final ChannelHandlerContext context;
    private final HttpRequest httpRequest;


    public MineRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.context = ctx;
        this.httpRequest = httpRequest;
    }

    public final String getURL() {
        return this.httpRequest.uri();
    }

    public final String getMethod() {
        return this.httpRequest.method().name();
    }

    public final Map<String, List<String>> getParameters() {
        final QueryStringDecoder queryStringDecoder = new QueryStringDecoder(getURL());
        return queryStringDecoder.parameters();
    }

    public final String getParameterBy(final String name) {
        final Map<String, List<String>> parameters = getParameters();
        final List<String> parameter = parameters.get(name);
        if (null == parameter) {
            return null;
        }
        return parameter.get(0);
    }

}
