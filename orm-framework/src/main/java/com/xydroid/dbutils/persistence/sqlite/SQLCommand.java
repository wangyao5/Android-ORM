package com.xydroid.dbutils.persistence.sqlite;


import android.content.ContentValues;

import java.io.Serializable;
import java.util.List;

public interface SQLCommand {
    void initTable(String sql);
    void execSave(String table, ContentValues contentValues);
    void execSave(String table, List<ContentValues> contentValuesList);
    Object findById(String table, String idColumn, Serializable serializable);
    int count(String table);
    void deleteById(String table, String idColumn, Serializable serializable);
}
