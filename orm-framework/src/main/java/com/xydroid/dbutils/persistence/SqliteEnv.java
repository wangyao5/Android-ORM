package com.xydroid.dbutils.persistence;

import android.content.Context;
import com.xydroid.dbutils.Configurable;
import com.xydroid.dbutils.persistence.repository.Repository;

public class SqliteEnv {
    private boolean external;
    private String dbPath;
    private String dbName;
    private int version;
    private Context context;
    private Class<? extends Repository>[] repostorys;

    public SqliteEnv(Context context, Configurable config){
        this.context = context;
        external = config.external();
        dbPath = config.dbPath();
        dbName = config.dbName();
        version = config.version();
        repostorys = config.repository();
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Class<? extends Repository>[] getRepostorys() {
        return repostorys;
    }

    public void setRepostorys(Class<? extends Repository>[] repostorys) {
        this.repostorys = repostorys;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SqliteEnv){
            SqliteEnv config = (SqliteEnv)obj;
            if (this.external != config.external){
                return false;
            }

            if (!this.dbPath.equals(config.dbPath)){
                return false;
            }

            if (!this.dbName.equals(config.dbName)){
                return false;
            }

            if (this.version != config.version){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return external+dbPath+dbName;
    }
}
