package com.xydroid.dbutils.env;

import android.database.sqlite.SQLiteOpenHelper;

import com.xydroid.dbutils.SQLiteContext;
import com.xydroid.dbutils.env.helper.SQLiteDatabaseContext;
import com.xydroid.dbutils.env.helper.SQLiteDatabaseHelper;
import com.xydroid.dbutils.persistence.SQLiteEnv;
import com.xydroid.dbutils.persistence.proxy.Proxy;
import com.xydroid.dbutils.persistence.repository.Repository;
import java.util.HashMap;
import java.util.Map;

public class RepositoryManager {
    private SQLiteContext mSQLiteContext;
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private Map<Class, Repository> repositoryMap = new HashMap<>();

    public RepositoryManager(SQLiteContext sqliteContext) {
        mSQLiteContext = sqliteContext;
        initSQLiteOpenHelper();
    }

    private void initSQLiteOpenHelper() {
        SQLiteEnv env = mSQLiteContext.getSQLiteEnv();
        SQLiteDatabaseContext sqliteDatabaseContext = new SQLiteDatabaseContext(mSQLiteContext);
        mSQLiteOpenHelper = new SQLiteDatabaseHelper(sqliteDatabaseContext, env.getDbName(), env.getVersion());
    }

    public Repository getRepostory(Class<? extends Repository> clazz) {
        if (repositoryMap.containsKey(clazz)){
            return repositoryMap.get(clazz);
        }
        Repository repository = initRepostoryProxy(clazz);
        repositoryMap.put(clazz,repository);
        return repository;
    }

    private Repository initRepostoryProxy(Class<? extends Repository> clazz) {
        return (Repository)java.lang.reflect.Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new Proxy(mSQLiteOpenHelper, clazz));
    }
}
