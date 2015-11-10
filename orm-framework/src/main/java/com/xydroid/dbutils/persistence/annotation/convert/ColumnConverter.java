package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;

import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public interface ColumnConverter<T, C> {

    T getFieldValue(final Cursor cursor, String columnName);

    C getColumnValue(T t);

    ColumnType getColumnType();

}
