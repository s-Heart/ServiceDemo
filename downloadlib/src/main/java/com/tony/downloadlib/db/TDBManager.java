package com.tony.downloadlib.db;

import android.content.Context;

import com.tony.downloadlib.greendao.DaoMaster;
import com.tony.downloadlib.greendao.DaoSession;

/**
 * Created by tony on 2017/11/19.
 */

public class TDBManager {
    private static TDBManager instance;
    private DaoSession mDaoSession;

    private TDBManager() {

    }

    public static TDBManager getInstance() {
        if (instance == null) {
            synchronized (TDBManager.class) {
                if (instance == null) {
                    instance = new TDBManager();
                }
            }
        }
        return instance;
    }


    public void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "tdownload.db", null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = mDaoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
