package com.tony.downloadlib.db.upgrade;

import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * Author: shishaojie
 * Date: 2017/11/29 0029 11:36
 * Description:
 */
class V7Upgrade implements Upgrade {
    @Override
    public void onUpgrade(Database db) {
        Log.d("=T=V7Upgrade", "onUpgrade: ");
    }
}
