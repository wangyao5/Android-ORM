package com.xydroid.dbutils.persistence.sqlite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Entity {
    private Annotation[] mEntityAnnotations;
    private Map<Field, Annotation[]> mFieldsAnnotations = new HashMap<>();

    public Entity(Annotation[] anotations){
        mEntityAnnotations = getEntityUsedAnnotations(anotations);
    }

    public Entity(Annotation[] mEntityAnnotations, Map<Field, Annotation[]> mFieldsAnnotations) {
        this.mEntityAnnotations = mEntityAnnotations;
        this.mFieldsAnnotations = mFieldsAnnotations;
    }

    public void addField(Field field, Annotation[] annotations) {
        mFieldsAnnotations.put(field, annotations);
    }

    private Annotation[] getEntityUsedAnnotations(Annotation[] anotations){
        return null;
    }

}
