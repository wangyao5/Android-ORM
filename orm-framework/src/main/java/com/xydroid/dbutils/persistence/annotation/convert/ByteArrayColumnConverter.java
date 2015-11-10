package com.xydroid.dbutils.persistence.annotation.convert;

import android.database.Cursor;
import com.xydroid.dbutils.persistence.annotation.convert.type.ColumnType;

public class ByteArrayColumnConverter implements ColumnConverter<byte[], byte[]> {

    @Override
    public byte[] getFieldValue(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getBlob(index);
    }

    @Override
    public byte[] getColumnValue(byte[] bytes) {
        return bytes;
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.BLOB;
    }
}
