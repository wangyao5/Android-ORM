package com.xydroid.dbutils.persistence.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        SQLiteDatabase db = null;
        try {
            db = mSQLiteOpenHelper.getWritableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {
        } finally {
            if (null != db) {
                db.close();
            }
        }
    }

    @Override
    public boolean execSave(String table, ContentValues contentValues) {
        SQLiteDatabase db = null;
        try {
            db = mSQLiteOpenHelper.getWritableDatabase();
            return db.insert(table, null, contentValues) >= 0;
        } catch (Exception e) {
            return false;
        } finally {
            if (null != db) {
                db.close();
            }
        }
    }

    @Override
    public boolean execSave(String table, List<ContentValues> contentValuesList) {
        boolean result = true;
        SQLiteDatabase db = null;
        try {
            db = mSQLiteOpenHelper.getWritableDatabase();
            db.beginTransaction();
            for (ContentValues contentValues : contentValuesList) {
                if (db.insert(table, null, contentValues) < 0) {
                    result = false;
                    break;
                }
            }
            if (result) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (null != db) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int count(String table) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mSQLiteOpenHelper.getReadableDatabase();
            cursor = db.query(table, null, null, null, null, null, null);
            return cursor.getCount();
        } catch (Exception e) {

        } finally {
            if (null != db) {
                db.close();
            }
        }
        return 0;
    }

    @Override
    public boolean deleteById(String table, String idColumn, Serializable serializable) {
        SQLiteDatabase db = null;
        try {
            db = mSQLiteOpenHelper.getWritableDatabase();
            return db.delete(table, idColumn + " = ?", new String[]{serializable.toString()}) >= 0;
        } catch (Exception e) {

        } finally {
            if (null != db) {
                db.close();
            }
        }
        return false;
    }

    @Override
    public boolean deleteAll(String table) {
        SQLiteDatabase db = null;
        try {
            db = mSQLiteOpenHelper.getWritableDatabase();
            return db.delete(table, null, null) >= 0;
        } catch (Exception e) {

        } finally {
            if (null != db) {
                db.close();
            }
        }
        return false;
    }

    @Override
    public boolean dropTable(String table) {
        SQLiteDatabase db = null;
        try {
            db = mSQLiteOpenHelper.getWritableDatabase();
            db.execSQL(String.format("DROP TABLE IF EXISTS %s",table));
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (null != db) {
                db.close();
            }
        }
    }
}
