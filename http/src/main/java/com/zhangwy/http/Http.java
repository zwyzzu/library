package com.zhangwy.http;

public class Http {
    private static HttpClient defaultClient = null;

    public static synchronized HttpClient defaultHttpClient() {
        if (defaultClient == null)
            defaultClient = new HttpAsyncClient(false);
        return defaultClient;
    }

    public static HttpClient newHttpClient() {
        return new HttpAsyncClient(false);
    }

    public static HttpClient newSingleHttpClient() {
        return new HttpAsyncClient(true);
    }

    public static HttpClient newHttpClient(int maxPoolSize) {
        return new HttpAsyncClient(maxPoolSize);
    }
}
