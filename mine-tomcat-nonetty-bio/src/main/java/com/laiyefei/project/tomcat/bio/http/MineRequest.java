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

    public MineRequest(final InputStream is) {
        this.is = is;

        try {
            printInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printInfo() throws IOException {
        String content = "";
        byte[] buff = new byte[1024];
        int len = 0;
        if (0 < (len = this.is.read(buff))) {
            content = new String(buff, 0, len);
        }
        System.out.println(content);
    }

    public final String getURL() {
        return null;
    }

    public final String getMethod() {
        return null;
    }

}
