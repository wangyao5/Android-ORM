package com.xydroid.dbutils.persistence.sqlite;

import android.database.sqlite.SQLiteOpenHelper;

import com.xydroid.dbutils.persistence.repository.CrudRepository;
import com.xydroid.dbutils.persistence.sqlite.query.CursorIterable;

import java.io.Serializable;

public class SQLExecutor implements CrudRepository{
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private Class mEntityClazz;
    private Class mIdClazz;
    private Entity mEntity;
    public SQLExecutor(SQLiteOpenHelper helper, Class entityClazz, Class idClazz){
        mSQLiteOpenHelper = helper;
        mEntityClazz = entityClazz;
        mIdClazz = idClazz;
        init(entityClazz, idClazz);
    }

    private void init(Class entityClazz, Class idClazz){
        mEntity = new Entity(entityClazz, idClazz);
    }

    public void createTable(){
        mSQLiteOpenHelper.getWritableDatabase().execSQL(mEntity.createTableSql());
    }


    @Override
    public void save(Object entity) {
//        mSQLiteOpenHelper.getWritableDatabase().
        System.out.println("xxxx");
    }

    @Override
    public void save(Iterable entities) {

    }

    @Override
    public Object findOne(Serializable serializable) {
        return null;
    }

    @Override
    public boolean exists(Serializable serializable) {
        return false;
    }

    @Override
    public CursorIterable findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Serializable serializable) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public void delete(Iterable entities) {

    }

    @Override
    public void deleteAll() {

    }
}
