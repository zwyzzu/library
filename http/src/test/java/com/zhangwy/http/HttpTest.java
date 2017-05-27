package com.zhangwy.http;

import com.zhangwy.util.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Author: zhangwy(张维亚)
 * 创建时间：2017/3/30 下午8:55
 * 修改时间：2017/3/30 下午8:55
 * Description: TODO
 */
public class HttpTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void defaultHttpClient() throws Exception {
        Http.defaultHttpClient().get("https://www.baidu.com/", new HttpHandler() {
            @Override
            public void onSuccess(HttpRequest req, HttpResponse resp) {
                Logger.d("success" + new String(resp.getContent()));
                System.out.println("success" + new String(resp.getContent()));
            }

            @Override
            public void onFailed(HttpRequest req, HttpResponse resp) {
                Logger.d("failed" + resp.getMsg());
                System.out.println("failed" + resp.getMsg());
            }

            @Override
            public void onError(HttpRequest req, String errMsg) {
                Logger.d("error" + errMsg);
                System.out.println("error" + errMsg);
            }

            @Override
            public void onRetry(HttpRequest req, String reason) {
                Logger.d("retry");
                System.out.println("retry");
            }
        });
        System.out.println("default");
    }

    @Test
    public void newHttpClient() throws Exception {
        Http.newHttpClient().get("https://www.baidu.com/", new HttpHandler() {
            @Override
            public void onSuccess(HttpRequest req, HttpResponse resp) {
                Logger.d("success" + new String(resp.getContent()));
                System.out.println("success" + new String(resp.getContent()));
            }

            @Override
            public void onFailed(HttpRequest req, HttpResponse resp) {
                Logger.d("failed" + resp.getMsg());
                System.out.println("failed" + resp.getMsg());
            }

            @Override
            public void onError(HttpRequest req, String errMsg) {
                Logger.d("error" + errMsg);
                System.out.println("error" + errMsg);
            }

            @Override
            public void onRetry(HttpRequest req, String reason) {
                Logger.d("retry");
                System.out.println("retry");
            }
        });
    }

}