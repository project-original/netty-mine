package com.laiyefei.project.tomcat.bio.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-04-20 18:01
 * @Desc : the mine request
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class MineRequest {

    private final InputStream is;
    private final String url;
    private final String method;

    public MineRequest(final InputStream is) {
        this.is = is;
        try {
            final String content = getContent();
            final String lineFirst = content.split("\\n")[0];
            final String[] lineFirstArr = lineFirst.split("\\s");
            this.url = lineFirstArr[1];
            this.method = lineFirstArr[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContent() throws IOException {
        String content = "";
        byte[] buff = new byte[1024];
        int len = 0;
        if (0 < (len = this.is.read(buff))) {
            content = new String(buff, 0, len);
            System.out.println(content);
        }

        return content;
    }

    public final String getURL() {
        return this.url;
    }

    public final String getMethod() {
        return this.method;
    }

}
