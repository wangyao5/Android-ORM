package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class CharColumnConverter implements ColumnConverter<Character, Integer> {

    @Override
    public Character getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : (char) cursor.getInt(index);
    }

    @Override
    public Integer getColumnValue(Character fieldValue) {
        if (fieldValue == null) return null;
        return (int)fieldValue;
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.INTEGER;
    }
}
