package com.tony.downloadlib.db.upgrade;

import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * <br/>Author: tony(shishaojie@koolearn.com)
 * <br/>Date: 2017/11/29 0029
 * <br/>Time: 11:07
 * <br/>Description:
 * <br/>FIXME
 */

class V2Upgrade implements Upgrade {
    @Override
    public void onUpgrade(Database db) {
        Log.d("=T=V2Upgrade", "onUpgrade: ");
    }
}
