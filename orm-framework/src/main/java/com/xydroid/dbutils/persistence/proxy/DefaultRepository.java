package com.xydroid.dbutils.persistence.proxy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xydroid.dbutils.persistence.sqlite.Entity;

import java.util.ArrayList;
import java.util.List;

public class DefaultRepository {
    private SQLiteOpenHelper mHelper;
    private Entity mEntity;

    public DefaultRepository(SQLiteOpenHelper helper, Entity entity) {
        mHelper = helper;
        mEntity = entity;
    }

    List execQuery(String sql) {
        List allObject = new ArrayList();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, null);
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

    void execSql(String sql){
        SQLiteDatabase db = null;
        try {
            db = mHelper.getReadableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {

        } finally {
            if (null != db) {
                db.close();
            }
        }
    }
}
