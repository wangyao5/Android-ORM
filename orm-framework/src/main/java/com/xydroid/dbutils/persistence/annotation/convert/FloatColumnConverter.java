package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class FloatColumnConverter implements ColumnConverter<Float, Float> {

    @Override
    public Float getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getFloat(index);
    }

    @Override
    public Float getColumnValue(Float columnValue) {
        return columnValue;
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.REAL;
    }
}
