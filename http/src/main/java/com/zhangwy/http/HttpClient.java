package com.zhangwy.http;

import java.util.concurrent.Future;

public interface HttpClient {

    Future<?> get(String url, HttpHandler handler) throws HttpException;

    Future<?> get(String hostPath, HttpParams params, HttpHandler handler) throws HttpException;

    Future<?> get(String host, String path, HttpParams params, HttpHandler handler) throws HttpException;

    Future<?> get(String host, int port, String path, HttpParams params, HttpHandler handler) throws HttpException;

    Future<?> get(String remoteFile, String localDir, HttpHandler handler) throws HttpException;

    Future<?> get(String remoteFile, String localDir, String fileName, boolean append, HttpHandler handler) throws HttpException;

    Future<?> post(String url, HttpHandler handler) throws HttpException;

    Future<?> post(String hostPath, HttpParams params, HttpHandler handler) throws HttpException;

    Future<?> post(String host, String path, HttpParams params, HttpHandler handler) throws HttpException;

    Future<?> post(String host, int port, String path, HttpParams params, HttpHandler handler) throws HttpException;

    Future<?> post(String remotePath, String localFile, HttpHandler handler) throws HttpException;

    Future<?> get(String url, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> get(String hostPath, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> get(String host, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> get(String host, int port, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> get(String remoteFile, String localDir, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> get(String remoteFile, String localDir, String fileName, boolean append, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> get(String remoteFile, String localDir, String fileName, boolean append, boolean block, float blockSizeMb, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> post(String url, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> post(String hostPath, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> post(String host, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> post(String host, int port, String path, HttpParams params, int maxRetryCount, HttpHandler handler) throws HttpException;

    Future<?> post(String remotePath, String localFile, int maxRetryCount, HttpHandler handler) throws HttpException;

    void setUserAgent(String userAgent);

    void destroy();
}
