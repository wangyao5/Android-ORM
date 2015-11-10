package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class ByteColumnConverter implements ColumnConverter<Byte, Integer> {

    @Override
    public Byte getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : (byte) cursor.getInt(index);
    }

    @Override
    public Integer getColumnValue(Byte columnValue) {
        return Integer.valueOf(columnValue);
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.INTEGER;
    }
}
