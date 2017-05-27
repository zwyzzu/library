package com.zhangwy.http;

/**
 * Author: zhangwy(张维亚)
 * Email:  zhangweiya@yixia.com
 * 创建时间:2017/5/18 下午4:10
 * 修改时间:2017/5/18 下午4:10
 * Description:recall handler for download request after the request has executed
 */

public abstract class DownloadHandler extends HttpHandler {
    /**
     * Notification that the progress level has changed.
     * Clients can use the taskId or taskUrl parameter to distinguish task changes
     *
     * @param req: the http request
     * @param hasDownloadSize has download file size
     */
    public abstract void onProgressChanged(HttpRequest req, long hasDownloadSize);
}
