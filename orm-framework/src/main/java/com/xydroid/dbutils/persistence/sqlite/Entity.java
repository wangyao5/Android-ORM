package com.xydroid.dbutils.persistence.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    private String mTableName;
    private Class mIdClazz;
    private Annotation[] mEntityAnnotation;
    private List<TableField> mTableFields = new ArrayList<>();
    private String mIdColumnName;

    public Entity(Class entityClazz, Class idClazz) {
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
            if (tableField.isIdColumn()){
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

    public Class getIdClazz() {
        return mIdClazz;
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

    public ContentValues convertToCV(Object o){
        return null;
    }

    public Object convertToOJ(Cursor cursor){
        return null;
    }
}
