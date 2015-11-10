package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class DoubleColumnConverter implements ColumnConverter<Double, Double> {

    @Override
    public Double getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getDouble(index);
    }

    @Override
    public Double getColumnValue(Double columnValue) {
        return columnValue;
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.REAL;
    }
}
