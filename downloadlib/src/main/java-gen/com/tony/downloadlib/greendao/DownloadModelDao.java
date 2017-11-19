package com.tony.downloadlib.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tony.downloadlib.model.DownloadModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DOWNLOAD_MODEL".
*/
public class DownloadModelDao extends AbstractDao<DownloadModel, String> {

    public static final String TABLENAME = "DOWNLOAD_MODEL";

    /**
     * Properties of entity DownloadModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Url = new Property(0, String.class, "url", true, "URL");
        public final static Property DownloadState = new Property(1, int.class, "downloadState", false, "DOWNLOAD_STATE");
        public final static Property TotalSize = new Property(2, int.class, "totalSize", false, "TOTAL_SIZE");
        public final static Property DownloadSize = new Property(3, int.class, "downloadSize", false, "DOWNLOAD_SIZE");
        public final static Property DownloadPath = new Property(4, String.class, "downloadPath", false, "DOWNLOAD_PATH");
    };


    public DownloadModelDao(DaoConfig config) {
        super(config);
    }
    
    public DownloadModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWNLOAD_MODEL\" (" + //
                "\"URL\" TEXT PRIMARY KEY NOT NULL ," + // 0: url
                "\"DOWNLOAD_STATE\" INTEGER NOT NULL ," + // 1: downloadState
                "\"TOTAL_SIZE\" INTEGER NOT NULL ," + // 2: totalSize
                "\"DOWNLOAD_SIZE\" INTEGER NOT NULL ," + // 3: downloadSize
                "\"DOWNLOAD_PATH\" TEXT);"); // 4: downloadPath
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWNLOAD_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DownloadModel entity) {
        stmt.clearBindings();
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(1, url);
        }
        stmt.bindLong(2, entity.getDownloadState());
        stmt.bindLong(3, entity.getTotalSize());
        stmt.bindLong(4, entity.getDownloadSize());
 
        String downloadPath = entity.getDownloadPath();
        if (downloadPath != null) {
            stmt.bindString(5, downloadPath);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DownloadModel entity) {
        stmt.clearBindings();
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(1, url);
        }
        stmt.bindLong(2, entity.getDownloadState());
        stmt.bindLong(3, entity.getTotalSize());
        stmt.bindLong(4, entity.getDownloadSize());
 
        String downloadPath = entity.getDownloadPath();
        if (downloadPath != null) {
            stmt.bindString(5, downloadPath);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public DownloadModel readEntity(Cursor cursor, int offset) {
        DownloadModel entity = new DownloadModel( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // url
            cursor.getInt(offset + 1), // downloadState
            cursor.getInt(offset + 2), // totalSize
            cursor.getInt(offset + 3), // downloadSize
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // downloadPath
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DownloadModel entity, int offset) {
        entity.setUrl(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDownloadState(cursor.getInt(offset + 1));
        entity.setTotalSize(cursor.getInt(offset + 2));
        entity.setDownloadSize(cursor.getInt(offset + 3));
        entity.setDownloadPath(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final String updateKeyAfterInsert(DownloadModel entity, long rowId) {
        return entity.getUrl();
    }
    
    @Override
    public String getKey(DownloadModel entity) {
        if(entity != null) {
            return entity.getUrl();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
