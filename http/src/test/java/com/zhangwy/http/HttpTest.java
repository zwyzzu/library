package com.zhangwy.http;

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

    }

    @Test
    public void newHttpClient() throws Exception {
        Http.newHttpClient().get("", new HttpHandler() {
            @Override
            public void onSuccess(HttpRequest req, HttpResponse resp) {

            }

            @Override
            public void onFailed(HttpRequest req, HttpResponse resp) {

            }

            @Override
            public void onError(HttpRequest req, String errMsg) {

            }

            @Override
            public void onRetry(HttpRequest req, String reason) {

            }
        });
    }

}