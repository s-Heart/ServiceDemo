package com.tony.downloadlib.interfaces;

import com.tony.downloadlib.model.DownloadModel;

import java.util.List;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/11/19
 * Time: 下午8:37
 * Description:
 * 客户端主动调用接口
 */
public interface DownloadActions {
    void startDownload(DownloadModel model);

    void pauseDownload(DownloadModel model);

    void pauseAll();

    void startAll(List<DownloadModel> models);
}
