package com.tony.downloadlib.db;

import com.tony.downloadlib.greendao.DownloadModelDao;
import com.tony.downloadlib.model.DownloadModel;

import java.io.File;

/**
 * Author: shishaojie
 * Date: 2017/11/20 0020 15:12
 * Description:
 */
public class DBProxy {
    private static DownloadModelDao modelDao = TDBManager.getInstance().getDaoSession().getDownloadModelDao();

    public static void updateModelDownloadPath(DownloadModel model, String absPath, String fileName) {
        model.setDownloadPath(absPath);
        model.setFileName(fileName);
        DownloadModel modelResult = modelDao.queryBuilder().where(DownloadModelDao.Properties.Url.eq(model.getUrl())).build().unique();
        modelResult.setDownloadPath(absPath);
        modelResult.setFileName(fileName);
        modelDao.insertOrReplace(modelResult);
    }

    public static void updateModelTotalSize(DownloadModel model, long contentLength) {
        model.setTotalSize(contentLength);
        DownloadModel modelResult = modelDao.queryBuilder().where(DownloadModelDao.Properties.Url.eq(model.getUrl())).build().unique();
        modelResult.setTotalSize(contentLength);
        modelDao.insertOrReplace(modelResult);
    }

    public static void updateModelDownloadSize(DownloadModel model, long downloadSize) {
        model.setDownloadSize(downloadSize);
        DownloadModel modelResult = modelDao.queryBuilder().where(DownloadModelDao.Properties.Url.eq(model.getUrl())).build().unique();
        modelResult.setDownloadSize(downloadSize);
        modelDao.insertOrReplace(modelResult);
    }

    public static void deleteModel(DownloadModel model) {
        DownloadModel modelResult = modelDao.queryBuilder().where(DownloadModelDao.Properties.Url.eq(model.getUrl())).build().unique();
        if (modelResult != null && modelResult.getDownloadPath() != null) {
            deleteFile(modelResult.getDownloadPath());
            modelDao.deleteByKey(modelResult.getUrl());
        }
    }

    private static void deleteFile(String downloadPath) {
        File file = new File(downloadPath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
