package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class BooleanColumnConverter implements ColumnConverter<Boolean, Integer> {
    @Override
    public Boolean getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getInt(index) == 1;
    }

    @Override
    public Integer getColumnValue(Boolean fieldValue) {
        if (fieldValue == null) return null;
        return fieldValue ? 1 : 0;
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.INTEGER;
    }


}
