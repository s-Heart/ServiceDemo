package com.tony.downloadlib.db.upgrade;

import org.greenrobot.greendao.database.Database;

/**
 * <br/>Author: tony(shishaojie@koolearn.com)
 * <br/>Date: 2017/11/29 0029
 * <br/>Time: 10:41
 * <br/>Description:
 * <br/>FIXME
 */

interface Upgrade {

    void onUpgrade(Database db);
}
