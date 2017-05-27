package com.zhangwy.http;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class HttpAsyncClient implements HttpClient {
    private final int DEFAULT_MAX_RETRY_COUNT = 1; //default max retry count
    private ThreadPoolExecutor executorService;
    private String userAgent;

    HttpAsyncClient(boolean single) {
        this.init(single);
    }

    HttpAsyncClient(int maximumPoolSize) {
        if (maximumPoolSize <= 1) {
            this.init(true);
        } else {
            this.init(maximumPoolSize / 2, maximumPoolSize);
        }
    }

    private void init(boolean single) {
        int corePoolSize = 1;
        int maximumPoolSize = 1;
        if (!single) {
            final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
            corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));
            maximumPoolSize = CPU_COUNT * 2 + 1;
        }
        this.init(corePoolSize, maximumPoolSize);
    }

    private void init(int corePoolSize, int maximumPoolSize) {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128), new MThreadFactory());
            this.executorService.allowCoreThreadTimeOut(true);
        }
    }

    @Override
    public Future<?> get(String url, HttpHandler handler) throws HttpException {
        return this.get(url, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> get(String hostPath, HttpParams params, HttpHandler handler) throws HttpException {
        return this.get(hostPath, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> get(String host, String path, HttpParams params, HttpHandler handler) throws HttpException {
        return this.get(host, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> get(String host, int port, String path, HttpParams params, HttpHandler handler) throws HttpException {
        return this.get(host, port, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> get(String remoteFile, String localDir, HttpHandler handler) throws HttpException {
        return this.get(remoteFile, localDir, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> get(String remoteFile, String localDir, String fileName, boolean append, HttpHandler handler) throws HttpException {
        return this.get(remoteFile, localDir, fileName, append, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> post(String url, HttpHandler handler) throws HttpException {
        return this.post(url, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> post(String hostPath, HttpParams params, HttpHandler handler) throws HttpException {
        return this.post(hostPath, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> post(String host, String path, HttpParams params, HttpHandler handler) throws HttpException {
        return this.post(host, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> post(String host, int port, String path, HttpParams params, HttpHandler handler) throws HttpException {
        return this.post(host, port, path, params, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> post(String remotePath, String localFile, HttpHandler handler) throws HttpException {
        return this.post(remotePath, localFile, DEFAULT_MAX_RETRY_COUNT, handler);
    }

    @Override
    public Future<?> get(String url, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(url, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> get(String hostPath, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(hostPath, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> get(String host, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(host, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> get(String host, int port, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpGetTask(host, port, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> get(String remoteFile, String localDir, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpFileGetTask(remoteFile, localDir, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> get(String remoteFile, String localDir, String fileName, boolean append, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpFileGetTask(remoteFile, localDir, fileName, append, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        Future<?> future = this.executorService.submit(task);
        task.setFuture(future);
        return future;
    }

    @Override
    public Future<?> get(String remoteFile, String localDir, String fileName, boolean append, boolean block, float blockSizeMb, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpFileGetTask(remoteFile, localDir, fileName, append, block, blockSizeMb, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        Future<?> future = this.executorService.submit(task);
        task.setFuture(future);
        return future;
    }

    @Override
    public Future<?> post(String url, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(url, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> post(String hostPath, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(hostPath, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> post(String host, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(host, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> post(String host, int port, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpPostTask(host, port, path, params, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
    }

    @Override
    public Future<?> post(String remotePath, String localFile, int maxRetryCount, HttpHandler handler) throws HttpException {
        HttpTask task = new HttpFilePostTask(remotePath, localFile, maxRetryCount, handler);
        this.setTaskUserAgent(task);
        return this.executorService.submit(task);
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
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "HttpAsyncClient #" + mCount.getAndIncrement());
        }
    }
}
