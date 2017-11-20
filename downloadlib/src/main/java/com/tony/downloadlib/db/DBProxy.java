package com.tony.downloadlib.db;

import com.tony.downloadlib.greendao.DownloadModelDao;
import com.tony.downloadlib.model.DownloadModel;

/**
 * Author: shishaojie
 * Date: 2017/11/20 0020 15:12
 * Description:
 */
public class DBProxy {
    public static void updateModelDownloadPath(DownloadModel model, String absPath) {
        model.setDownloadPath(absPath);
        DownloadModelDao modelDao = TDBManager.getInstance().getDaoSession().getDownloadModelDao();
        DownloadModel modelResult = modelDao.queryBuilder().where(DownloadModelDao.Properties.Url.eq(model.getUrl())).build().unique();
        modelResult.setDownloadPath(absPath);
        modelDao.insertOrReplace(modelResult);
    }

    public static void updateModelTotalSize(DownloadModel model, long contentLength) {
        model.setTotalSize(contentLength);
        DownloadModelDao modelDao = TDBManager.getInstance().getDaoSession().getDownloadModelDao();
        DownloadModel modelResult = modelDao.queryBuilder().where(DownloadModelDao.Properties.Url.eq(model.getUrl())).build().unique();
        modelResult.setTotalSize(contentLength);
        modelDao.insertOrReplace(modelResult);
    }

    public static void updateModelDownloadSize(DownloadModel model, long downloadSize) {
        model.setDownloadSize(downloadSize);
        DownloadModelDao modelDao = TDBManager.getInstance().getDaoSession().getDownloadModelDao();
        DownloadModel modelResult = modelDao.queryBuilder().where(DownloadModelDao.Properties.Url.eq(model.getUrl())).build().unique();
        modelResult.setDownloadSize(downloadSize);
        modelDao.insertOrReplace(modelResult);
    }
}
