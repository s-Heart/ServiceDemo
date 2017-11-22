package com.tony.downloadlib.interfaces;

import com.tony.downloadlib.model.DownloadModel;

import java.io.Serializable;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/11/19
 * Time: 下午8:37
 * Description:
 * 1.客户端被动接受响应接口；
 * 2.对应页面需要实现该接口接受回调；
 * 3.页面创建时需要注册，销毁时需要取消注册；
 */
public interface DownloadCallbacks extends Serializable {
    interface CallbackType {
        int METHOD_ON_COMPLETE = 0;
        int METHOD_ON_FAILED = 1;
        int METHOD_ON_CANCELED = 2;
        int METHOD_ON_WAIT = 3;
        int METHOD_ON_PROGRESS = 4;
    }

    void onComplete(DownloadModel model, String... args);

    void onFailed(DownloadModel model, Exception e);

    void onCanceled(DownloadModel model);

    void onWait(DownloadModel model);

    void onProgress(DownloadModel model, String progress);
}
