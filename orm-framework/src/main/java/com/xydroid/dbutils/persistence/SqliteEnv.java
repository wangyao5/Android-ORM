package com.xydroid.dbutils.persistence;

import com.xydroid.dbutils.Configurable;
import com.xydroid.dbutils.persistence.repository.Repository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SQLiteEnv {
    private boolean external;
    private String dbPath;
    private String dbName;
    private int version;
    private Set<Class<? extends Repository>> repositorys;

    public SQLiteEnv(Configurable config) {
        external = config.external();
        dbPath = config.dbPath();
        dbName = config.dbName();
        version = config.version();
        repositorys = new HashSet<>();
        repositorys.addAll(Arrays.asList(config.repository()));
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

    public Set<Class<? extends Repository>> getRepositorys() {
        return repositorys;
    }

    public void setRepositorys(Set<Class<? extends Repository>> repositorys) {
        this.repositorys = repositorys;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SQLiteEnv) {
            SQLiteEnv config = (SQLiteEnv) obj;
            if (this.external != config.external) {
                return false;
            }

            if (!this.dbPath.equals(config.dbPath)) {
                return false;
            }

            if (!this.dbName.equals(config.dbName)) {
                return false;
            }

            if (this.version != config.version) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return external + dbPath + dbName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((external) ? 0 : 1);
        result = prime * result
                + ((dbPath == null) ? 0 : dbPath.hashCode());
        result = prime * result
                + ((dbName == null) ? 0 : dbName.hashCode());
        return result;
    }
}
