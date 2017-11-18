package com.tony.downloadlib.interfaces;

import com.tony.downloadlib.model.DownloadModel;

import java.util.List;

/**
 * Created by tony on 2017/11/18.
 */

public interface DownloadActions {
    void startDownload(DownloadModel model);

    void pauseDownload(DownloadModel model);

    void pauseAll();

    void startAll(List<DownloadModel> models);
}
