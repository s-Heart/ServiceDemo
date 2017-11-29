package com.tony.downloadlib.db.upgrade;

import org.greenrobot.greendao.database.Database;

/**
 * Author: shishaojie
 * Date: 2017/11/29 0029 11:33
 * Description:
 */
interface Upgrade {
    void onUpgrade(Database db);
}
