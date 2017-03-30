package com.zhangwy.http;

import android.support.annotation.NonNull;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpAsyncClient implements HttpClient {
    private final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    private final int DEFAULT_MAX_RETRY_COUNT = 1; //default max retry count

    private ThreadPoolExecutor executorService;
    private String userAgent;

    public HttpAsyncClient() {
        this.init();
    }

    @Override
    public void init() {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new MThreadFactory());
            this.executorService.allowCoreThreadTimeOut(true);
        }
    }

    @Override
    public void get(String url, HttpHandler handler) throws HttpException {
        this.get(url, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void get(String hostPath, HttpParams params, HttpHandler handler) throws HttpException {
        this.get(hostPath, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void get(String host, String path, HttpParams params, HttpHandler handler) throws HttpException {
        this.get(host, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void get(String host, int port, String path, HttpParams params, HttpHandler handler) throws HttpException {
        this.get(host, port, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void get(String remoteFile, String localDir, HttpHandler handler) throws HttpException {
        this.get(remoteFile, localDir, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void get(String remoteFile, String localDir, String fileName, boolean append, HttpHandler handler) throws HttpException {
        this.get(remoteFile, localDir, fileName, append, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void post(String url, HttpHandler handler) throws HttpException {
        this.post(url, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void post(String hostPath, HttpParams params, HttpHandler handler) throws HttpException {
        this.post(hostPath, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void post(String host, String path, HttpParams params, HttpHandler handler) throws HttpException {
        this.post(host, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void post(String host, int port, String path, HttpParams params, HttpHandler handler) throws HttpException {
        this.post(host, port, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void post(String remotePath, String localFile, HttpHandler handler) throws HttpException {
        this.post(remotePath, localFile, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public void get(String url, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(url, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void get(String hostPath, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(hostPath, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void get(String host, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(host, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void get(String host, int port, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(host, port, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void get(String remoteFile, String localDir, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpFileGetTask(remoteFile, localDir, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void get(String remoteFile, String localDir, String fileName, boolean append, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpFileGetTask(remoteFile, localDir, fileName, append, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void post(String url, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(url, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void post(String hostPath, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(hostPath, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void post(String host, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(host, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void post(String host, int port, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(host, port, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    @Override
    public void post(String remotePath, String localFile, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpFilePostTask(remotePath, localFile, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        this.executorService.execute(task);
    }

    private void setTaskUserAgent(HttpTask task) {
        if (userAgent != null)
            task.setUserAgent(userAgent);
    }

    @Override
    public void destroy() {
        if (this.executorService != null) {
            this.executorService.shutdown();
            this.executorService = null;
        }
    }

    @Override
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private class MThreadFactory implements ThreadFactory {

        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "HttpAsyncClient #" + mCount.getAndIncrement());
        }
    }
}
