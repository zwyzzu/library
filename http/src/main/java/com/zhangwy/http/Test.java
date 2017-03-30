package com.zhangwy.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void testURL() {
        try {
            String strurl = "https://www.baidu.com/abc?a=1&b=2";
            URL url = new URL(strurl);
            System.out.println(url.getHost());
            System.out.println(url.getPath());
            System.out.println(url.getQuery());
            System.out.println(url.getPort());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int gid = 0;

    public static class TestTask implements Runnable {
        private int idd = 0;

        public TestTask() {
            idd = gid;
            gid++;
        }

        public void run() {
            while (true) {
                System.out.println("task:" + idd + ", thread:" + Thread.currentThread().getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void testES() {
        //ExecutorService es = Executors.newCachedThreadPool();
        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++)
            es.execute(new TestTask());
    }

    public static class TestHandler extends HttpHandler {

        public void onSuccess(HttpRequest req, HttpResponse resp) {
            System.out.println("success, code: " + resp.getCode());
            System.out.println("content:" + resp.getContent());
            System.out.println("time used:" + resp.getTimeUsed());
        }

        public void onFailed(HttpRequest req, HttpResponse resp) {
            System.out.println("response code: " + resp.getCode() + ", response msg:" + resp.getMsg());
        }

        public void onError(HttpRequest req, String errMsg) {
            System.out.println("error: " + errMsg);
        }

        @Override
        public void onRetry(HttpRequest req, String reason) {
            // TODO Auto-generated method stub

        }

    }

    public static void testHttp() {
        try {
            HttpClient client = Http.newHttpClient();
            client.get("http://po.funshion.com/v5/config/homepage", new TestHandler());
            //client.get("http://www.sohu.com/", new TestHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testHttpUrl() {
        try {
            URL url = new URL("http://192.168.12.12");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            int a = conn.getResponseCode();
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testHttp();
    }
}
