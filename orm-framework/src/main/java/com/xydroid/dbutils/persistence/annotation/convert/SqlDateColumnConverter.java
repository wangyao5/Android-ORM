package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class SqlDateColumnConverter implements ColumnConverter<java.sql.Date, Long> {

    @Override
    public java.sql.Date getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : new java.sql.Date(cursor.getLong(index));
    }

    @Override
    public Long getColumnValue(java.sql.Date columnValue) {
        if (columnValue == null) return null;
        return columnValue.getTime();
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.INTEGER;
    }
}
