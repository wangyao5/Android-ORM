package com.xydroid.dbutils.persistence.sqlite;

import android.database.sqlite.SQLiteOpenHelper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SQLExecutor {
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private Class mEntityClazz;
    private Class mIdClazz;
    private Entity mEntity;
    public SQLExecutor(SQLiteOpenHelper helper, Class entityClazz, Class idClazz){
        mSQLiteOpenHelper = helper;
        init(entityClazz, idClazz);
    }

    private void init(Class entityClazz, Class idClazz){
        Field[] fields = mEntityClazz.getDeclaredFields();
        mEntityClazz.getAnnotations();
        for (Field field : fields){
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations){

            }
        }
    }

    public void createTable(){

    }

}
