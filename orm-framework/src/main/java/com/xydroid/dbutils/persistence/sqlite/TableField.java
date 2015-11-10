package com.xydroid.dbutils.persistence.sqlite;

import com.xydroid.dbutils.persistence.annotation.AutoIncrement;
import com.xydroid.dbutils.persistence.annotation.Check;
import com.xydroid.dbutils.persistence.annotation.Column;
import com.xydroid.dbutils.persistence.annotation.Foreign;
import com.xydroid.dbutils.persistence.annotation.Id;
import com.xydroid.dbutils.persistence.annotation.convert.ColumnConvertFactory;
import com.xydroid.dbutils.persistence.annotation.convert.ColumnConverter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableField {
    private String columnName = "";
    private boolean isId = false;
    private boolean autoIncrement = false;
    private boolean unique = false;
    private boolean nullable = true;
    private boolean insertable = true;
    private boolean updatable = true;
    private String checkValue = "";
    private Class mFieldType;
    private ColumnConverter mFieldConverter;

    public TableField(Field field){
        Annotation[] fieldAnnotations = findFieldUsedAnnotations(field);
        mFieldType = field.getType();
        mFieldConverter = ColumnConvertFactory.getFieldConverter(field);
        for (Annotation annotation : fieldAnnotations){
            if(annotation instanceof Id){
                isId = true;
            }

            if(annotation instanceof AutoIncrement){
                autoIncrement = true;
            }

            if (annotation instanceof Column){
                Column columnAnnotation = (Column) annotation;
                columnName = columnAnnotation.name();
                unique = columnAnnotation.unique();
                nullable = columnAnnotation.nullable();
                insertable = columnAnnotation.insertable();
                updatable = columnAnnotation.updatable();
            }

            if (annotation instanceof Check){
                Check checkAnnotation = (Check) annotation;
                checkValue = checkAnnotation.check();
            }
        }
    }

    public Annotation[] findFieldUsedAnnotations(Field field) {
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

    public String toCreateFieldString(){
        if (null == columnName || "".equals(columnName)){
            throw new IllegalArgumentException("Column name is empty!");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(columnName);
        sb.append("\" ");
        sb.append(mFieldConverter.getColumnType());
        if (isId){
            sb.append(" PRIMARY KEY");
        }
        if (autoIncrement){
            sb.append(" AUTOINCREMENT");
        }
        if (unique){
            sb.append(" UNIQUE");
        }
        if (!nullable){
            sb.append(" NOT NULL");
        }
        boolean check = null == checkValue || "".equals(checkValue);
        if (!check){
            sb.append("CHECK(");
            sb.append(checkValue);
            sb.append(")");
        }
        return sb.toString();
    }
}
