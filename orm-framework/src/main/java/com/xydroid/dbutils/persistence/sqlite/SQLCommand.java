package com.xydroid.dbutils.persistence.sqlite;


import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.List;

public interface SQLCommand {
    void initTable(String sql);
    boolean execSave(String table, ContentValues contentValues);
    boolean execSave(String table, List<ContentValues> contentValuesList);
    Cursor findById(String table, String idColumn, Serializable serializable);
    int count(String table);
    boolean deleteById(String table, String idColumn, Serializable serializable);
}
