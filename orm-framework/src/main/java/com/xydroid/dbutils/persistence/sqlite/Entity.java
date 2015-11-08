package com.xydroid.dbutils.persistence.sqlite;

import com.xydroid.dbutils.persistence.annotation.AutoIncrement;
import com.xydroid.dbutils.persistence.annotation.Check;
import com.xydroid.dbutils.persistence.annotation.Column;
import com.xydroid.dbutils.persistence.annotation.Foreign;
import com.xydroid.dbutils.persistence.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {
    private Annotation mEntityAnnotation;
    private Map<Field, Annotation[]> mFieldsAnnotations = new HashMap<>();
    private Class mIdClazz;

    public Entity(Class entityClazz, Class idClazz) {
        mIdClazz = idClazz;
        mEntityAnnotation = getEntityUsedAnnotation(entityClazz);
        initField(entityClazz);
    }

    public Entity(Annotation mEntityAnnotations, Map<Field, Annotation[]> mFieldsAnnotations) {
        this.mEntityAnnotation = mEntityAnnotations;
        this.mFieldsAnnotations = mFieldsAnnotations;
    }

    private void initField(Class entityClazz) {
        Field[] fields = entityClazz.getDeclaredFields();
        for (Field field : fields) {
            mFieldsAnnotations.put(field, getFieldUsedAnnotations(field));
        }
    }

    private Annotation getEntityUsedAnnotation(Class entityClazz) {
        Annotation entityAnnotation = null;
        if (entityClazz.isAnnotationPresent(com.xydroid.dbutils.persistence.annotation.Entity.class)) {
            entityAnnotation = entityClazz.getAnnotation(com.xydroid.dbutils.persistence.annotation.Entity.class);
        }
        return entityAnnotation;
    }

    private Annotation[] getFieldUsedAnnotations(Field field) {
        List<Annotation> tmpAnnotation = new ArrayList<>();

        if (field.isAnnotationPresent(AutoIncrement.class)) {
            tmpAnnotation.add(field.getAnnotation(AutoIncrement.class));
        }

        if (field.isAnnotationPresent(Check.class)) {
            tmpAnnotation.add(field.getAnnotation(Check.class));
        }

        if (field.isAnnotationPresent(Column.class)) {
            tmpAnnotation.add(field.getAnnotation(Column.class));
        }

        if (field.isAnnotationPresent(Foreign.class)) {
            tmpAnnotation.add(field.getAnnotation(Foreign.class));
        }

        if (field.isAnnotationPresent(Id.class)) {
            tmpAnnotation.add(field.getAnnotation(Id.class));
        }

        Annotation[] retAnnotation = new Annotation[tmpAnnotation.size()];
        tmpAnnotation.toArray(retAnnotation);
        return retAnnotation;
    }

}
