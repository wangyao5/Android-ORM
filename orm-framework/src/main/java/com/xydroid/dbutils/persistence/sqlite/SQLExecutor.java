package com.xydroid.dbutils.persistence.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.List;

public class SQLExecutor implements SQLCommand {
    private SQLiteOpenHelper mSQLiteOpenHelper;

    public SQLExecutor(SQLiteOpenHelper helper) {
        mSQLiteOpenHelper = helper;
    }

    @Override
    public void initTable(String sql) {
        mSQLiteOpenHelper.getWritableDatabase().execSQL(sql);
    }

    @Override
    public void execSave(String table, ContentValues contentValues) {
        mSQLiteOpenHelper.getWritableDatabase().insert(table, null, contentValues);
    }

    @Override
    public void execSave(String table, List<ContentValues> contentValuesList) {
        mSQLiteOpenHelper.getWritableDatabase().beginTransaction();
        for (ContentValues contentValues : contentValuesList) {
            execSave(table, contentValues);
        }
        mSQLiteOpenHelper.getWritableDatabase().endTransaction();
    }

    @Override
    public Object findById(String table, String idColumn, Serializable serializable) {
        mSQLiteOpenHelper.getReadableDatabase().query(table, null, idColumn + " = ?", new String[]{serializable.toString()}, null, null, null);
        return null;
    }

    @Override
    public int count(String table) {
        Cursor cursor = mSQLiteOpenHelper.getReadableDatabase().query(table, null, null, null, null, null, null);
        return cursor.getCount();
    }

    @Override
    public void deleteById(String table, String idColumn, Serializable serializable) {
        mSQLiteOpenHelper.getWritableDatabase().delete(table, idColumn + " = ?", new String[]{serializable.toString()});
    }
}
