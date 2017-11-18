package com.tony.downloadlib;

import com.tony.downloadlib.model.DownloadModel;

/**
 * Created by tony on 2017/11/18.
 */

public interface DownloadActions {
    void startDownload(DownloadModel model);

    void pauseDownload(DownloadModel model);

    void pauseAll();

    void startAll();
}
