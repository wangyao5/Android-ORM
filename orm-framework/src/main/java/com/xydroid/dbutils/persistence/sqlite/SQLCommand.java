package com.xydroid.dbutils.persistence.sqlite;

import android.content.ContentValues;
import java.io.Serializable;
import java.util.List;

public interface SQLCommand {
    void initTable(String sql);
    boolean execSave(String table, ContentValues contentValues);
    boolean execSave(String table, List<ContentValues> contentValuesList);
    int count(String table);
    boolean deleteById(String table, String idColumn, Serializable serializable);
    boolean deleteAll(String table);
    boolean dropTable(String table);
}
