package com.laiyefei.project.tomcat.bio.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-04-20 18:01
 * @Desc : the mine response
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class MineResponse {

    private final OutputStream os;

    public MineResponse(final OutputStream os) {
        this.os = os;
    }

    public void write(String content) throws IOException {
        final StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 ok\n")
                .append("Content-Type: text/html\n")
                .append("\r\n")
                .append(content);
        this.os.write(sb.toString().getBytes());
    }
}
