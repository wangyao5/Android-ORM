package com.xydroid.dbutils.persistence.sqlite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    private String mTableName;
    private Annotation[] mEntityAnnotation;
    private List<TableField> mTableFields = new ArrayList<>();

    public Entity(Class entityClazz, Class idClazz) {
        mEntityAnnotation = findEntityUsedAnnotation(entityClazz);
        initTableName();
        initTableFields(entityClazz);
    }

    private void initTableName(){
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
            mTableFields.add(new TableField(field));
        }
    }

    public Annotation[] getEntityAnnotation() {
        return mEntityAnnotation;
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

    public String createTableSql(){
        String sqlTmplate = "CREATE TABLE IF NOT EXISTS %s ( %s )";

        StringBuilder sb = new StringBuilder();
        for (int i =0; i < mTableFields.size() - 1 ; i++){
            sb.append(mTableFields.get(i).toCreateFieldString()).append(",");
        }
        sb.append(mTableFields.get(mTableFields.size() - 1).toCreateFieldString());
        return String.format(sqlTmplate, mTableName, sb.toString());
    }
}
