package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class ShortColumnConverter implements ColumnConverter<Short, Short> {

    @Override
    public Short getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getShort(index);
    }

    @Override
    public Short getColumnValue(Short columnValue) {
        return columnValue;
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.INTEGER;
    }
}
