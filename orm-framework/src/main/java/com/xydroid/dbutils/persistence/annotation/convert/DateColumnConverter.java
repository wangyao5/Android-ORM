package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;


public class DateColumnConverter implements ColumnConverter<java.util.Date, Long> {

    @Override
    public java.util.Date getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : new java.util.Date(cursor.getLong(index));
    }

    @Override
    public Long getColumnValue(java.util.Date fieldValue) {
        if (fieldValue == null) return null;
        return fieldValue.getTime();
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.INTEGER;
    }
}
