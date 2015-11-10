package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class StringColumnConverter implements ColumnConverter<String, String> {

    @Override
    public String getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getString(index);
    }

    @Override
    public String getColumnValue(String columnValue) {
        return columnValue;
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.TEXT;
    }
}
