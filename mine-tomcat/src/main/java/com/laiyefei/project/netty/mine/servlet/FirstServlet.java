package com.laiyefei.project.netty.mine.servlet;


import com.laiyefei.project.netty.mine.http.MineRequest;
import com.laiyefei.project.netty.mine.http.MineResponse;
import com.laiyefei.project.netty.mine.http.MineServlet;

/**
 * @Author : leaf.fly(?)
 * @Create : 2021-04-20 18:01
 * @Desc : the first servlet
 * @Version : v1.0.0
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public class FirstServlet extends MineServlet {
    protected void doGet(MineRequest mineRequest, MineResponse mineResponse) throws Exception {
        mineResponse.write("the first servlet is answer ! ");
    }

    protected void doPost(MineRequest mineRequest, MineResponse mineResponse) throws Exception {
        mineResponse.write("the first servlet is answer ! ");
    }
}
