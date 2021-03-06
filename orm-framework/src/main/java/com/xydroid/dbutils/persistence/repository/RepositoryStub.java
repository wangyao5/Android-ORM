package com.xydroid.dbutils.persistence.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xydroid.dbutils.persistence.sqlite.Entity;
import com.xydroid.dbutils.persistence.sqlite.SQLExecutor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepositoryStub implements CrudRepository {

    private SQLExecutor mExecutor;
    private SQLiteOpenHelper mHelper;
    private Entity mEntity;
    private String table;

    public RepositoryStub(SQLExecutor executor, SQLiteOpenHelper helper, Entity entity) {
        mExecutor = executor;
        mHelper = helper;
        mEntity = entity;
        table = mEntity.getTableName();
    }

    @Override
    public boolean save(Object entity) {
        return mExecutor.execSave(table, mEntity.convertToCV(entity));
    }

    @Override
    public boolean save(Iterable entities) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        for (Object entity : entities) {
            contentValuesList.add(mEntity.convertToCV(entity));
        }
        return mExecutor.execSave(table, contentValuesList);
    }

    @Override
    public Object findOne(Serializable serializable) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mHelper.getReadableDatabase();
            cursor = db.query(table, null, mEntity.getIdColumnName() + " = ?", new String[]{serializable.toString()}, null, null, null);
            return mEntity.convertToOJ(cursor, 0);
        } catch (Exception e) {

        } finally {
            if (null != db) {
                db.close();
            }
        }

        return null;
    }

    @Override
    public boolean exists(Serializable serializable) {
        return null != findOne(serializable);
    }

    @Override
    public List findAll() {
        List allObject = new ArrayList();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mHelper.getReadableDatabase();
            cursor = db.query(table, null, null, null, null, null, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                allObject.add(mEntity.convertToOJ(cursor, i));
            }
        } catch (Exception e) {

        } finally {
            if (null != db) {
                db.close();
            }
        }

        return allObject;
    }

    @Override
    public long count() {
        return mExecutor.count(table);
    }

    @Override
    public boolean delete(Serializable serializable) {
        return mExecutor.deleteById(table, mEntity.getIdColumnName(), serializable);
    }

    @Override
    public boolean deleteAll() {
        return mExecutor.deleteAll(table);
    }

    @Override
    public boolean dropTable() {
        return mExecutor.dropTable(table);
    }
}
