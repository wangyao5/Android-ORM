package com.xydroid.dbutils.persistence.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import com.xydroid.dbutils.persistence.annotation.convert.BooleanColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.ByteArrayColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.ByteColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.CharColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.ColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.DateColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.DoubleColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.FloatColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.IntegerColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.LongColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.ShortColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.SqlDateColumnConverter;
import com.xydroid.dbutils.persistence.annotation.convert.StringColumnConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entity {
    private String mTableName;
    private Class mEntityClazz;
    private Class mIdClazz;
    private Annotation[] mEntityAnnotation;
    private List<TableField> mTableFields = new ArrayList<>();
    private String mIdColumnName;

    public Entity(Class entityClazz, Class idClazz) {
        mEntityClazz = entityClazz;
        mIdClazz = idClazz;
        mEntityAnnotation = findEntityUsedAnnotation(entityClazz);
        initTableName();
        initTableFields(entityClazz);
    }

    private void initTableName() {
        for (Annotation tableAnnotation : mEntityAnnotation) {
            if (tableAnnotation instanceof com.xydroid.dbutils.persistence.annotation.Entity) {
                mTableName = ((com.xydroid.dbutils.persistence.annotation.Entity) tableAnnotation).name();
                break;
            }
        }
        if (mTableName == null) {
            throw new IllegalArgumentException("Entity must contains name!");
        }
    }

    private void initTableFields(Class entityClazz) {
        Field[] fields = entityClazz.getDeclaredFields();
        for (Field field : fields) {
            TableField tableField = new TableField(field);
            if (tableField.isIdColumn()) {
                mIdColumnName = tableField.getColumnName();
            }
            mTableFields.add(tableField);
        }
    }

    private Annotation[] findEntityUsedAnnotation(Class entityClazz) {
        List<Annotation> tmpAnnotation = new ArrayList<>();
        Annotation entityAnnotation = null;
        if (entityClazz.isAnnotationPresent(com.xydroid.dbutils.persistence.annotation.Entity.class)) {
            entityAnnotation = entityClazz.getAnnotation(com.xydroid.dbutils.persistence.annotation.Entity.class);
            tmpAnnotation.add(entityAnnotation);
        }

        Annotation[] retAnnotation = new Annotation[tmpAnnotation.size()];
        tmpAnnotation.toArray(retAnnotation);
        return retAnnotation;
    }

    public String createTableSql() {
        String sqlTmplate = "CREATE TABLE IF NOT EXISTS %s ( %s )";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mTableFields.size() - 1; i++) {
            sb.append(mTableFields.get(i).toCreateFieldString()).append(",");
        }
        sb.append(mTableFields.get(mTableFields.size() - 1).toCreateFieldString());
        return String.format(sqlTmplate, mTableName, sb.toString());
    }

    public String getTableName() {
        return mTableName;
    }

    public String getIdColumnName() {
        return mIdColumnName;
    }

    public ContentValues convertToCV(Object o) {
        ContentValues cv = new ContentValues();
        for (TableField tableField : mTableFields) {
            Field field = tableField.getField();
            field.setAccessible(true);
            ColumnConverter converter = tableField.getFieldConverter();
            Object value = null;
            try {
                value = field.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (value == null) {
                throw new IllegalStateException(String.format("%s can not attach value", field.getName()));
            }

            if (converter instanceof BooleanColumnConverter) {
                BooleanColumnConverter currentConverter = (BooleanColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Boolean) value));
            }

            if (converter instanceof ByteArrayColumnConverter) {
                ByteArrayColumnConverter currentConverter = (ByteArrayColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((byte[]) value));
            }
            if (converter instanceof ByteColumnConverter) {
                ByteColumnConverter currentConverter = (ByteColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((byte) value));
            }

            if (converter instanceof CharColumnConverter) {
                CharColumnConverter currentConverter = (CharColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Character) value));
            }
            if (converter instanceof DateColumnConverter) {
                DateColumnConverter currentConverter = (DateColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Date) value));
            }

            if (converter instanceof DoubleColumnConverter) {
                DoubleColumnConverter currentConverter = (DoubleColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Double) value));
            }

            if (converter instanceof FloatColumnConverter) {
                FloatColumnConverter currentConverter = (FloatColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Float) value));
            }

            if (converter instanceof IntegerColumnConverter) {
                IntegerColumnConverter currentConverter = (IntegerColumnConverter) converter;
                if (tableField.isIdColumn()){
                    int cvalue = currentConverter.getColumnValue((Integer) value);
                    if (cvalue == 0){
                        continue;
                    }
                }
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Integer) value));
            }

            if (converter instanceof LongColumnConverter) {
                LongColumnConverter currentConverter = (LongColumnConverter) converter;
                if (tableField.isIdColumn()){
                    long cvalue = currentConverter.getColumnValue((Long) value);
                    if (cvalue == 0){
                        continue;
                    }
                }
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Long) value));
            }

            if (converter instanceof ShortColumnConverter) {
                ShortColumnConverter currentConverter = (ShortColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((Short) value));
            }

            if (converter instanceof SqlDateColumnConverter) {
                SqlDateColumnConverter currentConverter = (SqlDateColumnConverter) converter;
                cv.put(tableField.getColumnName(), currentConverter.getColumnValue((java.sql.Date) value));
            }

            if (converter instanceof StringColumnConverter) {
                StringColumnConverter currentConverter = (StringColumnConverter) converter;
                String cvalue = currentConverter.getColumnValue((String) value);
                if (cvalue.isEmpty()){
                    continue;
                }
                cv.put(tableField.getColumnName(), cvalue);
            }
        }

        return cv;
    }

    public Object convertToOJ(Cursor cursor, int index) throws IllegalArgumentException, IllegalAccessException{
        if (null == cursor || cursor.getCount() < 0){
            return null;
        }

        Object o = null;
        try {
            o = mEntityClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (cursor.moveToPosition(index)){
            for (TableField tableField : mTableFields) {
                Field field = tableField.getField();
                field.setAccessible(true);
                ColumnConverter converter = tableField.getFieldConverter();
                if (converter instanceof BooleanColumnConverter) {
                    BooleanColumnConverter currentConverter = (BooleanColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof ByteArrayColumnConverter) {
                    ByteArrayColumnConverter currentConverter = (ByteArrayColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }
                if (converter instanceof ByteColumnConverter) {
                    ByteColumnConverter currentConverter = (ByteColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof CharColumnConverter) {
                    CharColumnConverter currentConverter = (CharColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }
                if (converter instanceof DateColumnConverter) {
                    DateColumnConverter currentConverter = (DateColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof DoubleColumnConverter) {
                    DoubleColumnConverter currentConverter = (DoubleColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof FloatColumnConverter) {
                    FloatColumnConverter currentConverter = (FloatColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof IntegerColumnConverter) {
                    IntegerColumnConverter currentConverter = (IntegerColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof LongColumnConverter) {
                    LongColumnConverter currentConverter = (LongColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof ShortColumnConverter) {
                    ShortColumnConverter currentConverter = (ShortColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof SqlDateColumnConverter) {
                    SqlDateColumnConverter currentConverter = (SqlDateColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }

                if (converter instanceof StringColumnConverter) {
                    StringColumnConverter currentConverter = (StringColumnConverter) converter;
                    field.set(o, currentConverter.getFieldValue(cursor, tableField.getColumnName()));
                }
            }
            return o;
        } else {
            return null;
        }
    }
}
