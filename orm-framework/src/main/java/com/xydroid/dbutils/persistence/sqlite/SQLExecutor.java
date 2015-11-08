package com.xydroid.dbutils.persistence.sqlite;

import android.database.sqlite.SQLiteOpenHelper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SQLExecutor {
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private Entity mEntity;
    public SQLExecutor(SQLiteOpenHelper helper, Class entityClazz, Class idClazz){
        mSQLiteOpenHelper = helper;
        init(entityClazz, idClazz);
    }

    private void init(Class entityClazz, Class idClazz){
        mEntity = new Entity(entityClazz, idClazz);
    }

    public void createTable(){

    }
}
