package com.laiyefei.project.tomcat.bio;

import com.laiyefei.project.tomcat.bio.http.MineRequest;
import com.laiyefei.project.tomcat.bio.http.MineResponse;
import com.laiyefei.project.tomcat.bio.http.MineServlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("the MineTomcat is start. server port is " + this.port);
        while (true) {
            Socket client = this.serverSocket.accept();

            //process
            try (final OutputStream os = client.getOutputStream()) {
                try (final InputStream is = client.getInputStream()) {
                    //decorate
                    final MineRequest mineRequest = new MineRequest(is);
                    final MineResponse mineResponse = new MineResponse(os);

                    final String url = mineRequest.getURL();
                    if (container.containsKey(url)) {
                        container.get(url).service(mineRequest, mineResponse);
                    } else {
                        mineResponse.write("404 - Not Find This URL.");
                    }
                }
            }
        }
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
}
