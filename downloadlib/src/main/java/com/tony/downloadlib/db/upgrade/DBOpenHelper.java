package com.tony.downloadlib.db.upgrade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tony.downloadlib.greendao.DaoMaster;

import org.greenrobot.greendao.database.Database;

import java.util.HashMap;
import java.util.Map;

public class DBOpenHelper extends DaoMaster.DevOpenHelper {

    private Map<Integer, Upgrade> upgradeMap = new HashMap<>();

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        initUpgradeMap();
    }

    /**
     * 初始化数据库升级所需的映射关系
     */
    private void initUpgradeMap() {
        //todo <schemaVersion>,<对应操作>
        upgradeMap.put(2, new V2Upgrade());
        upgradeMap.put(3, new V3Upgrade());
        upgradeMap.put(4, new V4Upgrade());
        upgradeMap.put(5, new V5Upgrade());
        upgradeMap.put(6, new V6Upgrade());

    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
        Log.d("=T=DBOpenHelper", "onCreate: =====");
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        for (int schemaVersion = oldVersion + 1; schemaVersion <= newVersion; schemaVersion++) {
            if (upgradeMap.get(schemaVersion) != null) {
                upgradeMap.get(schemaVersion).onUpgrade(db);
                Log.d("=T=DBOpenHelper", "onUpgrade: " + oldVersion + " to " + schemaVersion);
            }
        }
    }
}
