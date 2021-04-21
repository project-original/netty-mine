package com.laiyefei.project.netty.mine.http;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-04-20 18:01
 * @Desc : the mine servlet
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class MineServlet {

    protected abstract void doGet(MineRequest mineRequest, MineResponse mineResponse) throws Exception;

    protected abstract void doPost(MineRequest mineRequest, MineResponse mineResponse) throws Exception;

    public void service(MineRequest mineRequest, MineResponse mineResponse) throws Exception {
        if ("get".equalsIgnoreCase(mineRequest.getMethod())) {
            doGet(mineRequest, mineResponse);
        } else {
            doPost(mineRequest, mineResponse);
        }
    }
}
