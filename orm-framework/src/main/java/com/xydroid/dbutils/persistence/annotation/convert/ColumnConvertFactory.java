package com.xydroid.dbutils.persistence.annotation.convert;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ColumnConvertFactory {
    private static final ConcurrentHashMap<String, ColumnConverter> columnConverter_map;

    static {
        columnConverter_map = new ConcurrentHashMap<>();

        BooleanColumnConverter booleanColumnConverter = new BooleanColumnConverter();
        columnConverter_map.put(boolean.class.getName(), booleanColumnConverter);
        columnConverter_map.put(Boolean.class.getName(), booleanColumnConverter);

        ByteArrayColumnConverter byteArrayColumnConverter = new ByteArrayColumnConverter();
        columnConverter_map.put(byte[].class.getName(), byteArrayColumnConverter);

        ByteColumnConverter byteColumnConverter = new ByteColumnConverter();
        columnConverter_map.put(byte.class.getName(), byteColumnConverter);
        columnConverter_map.put(Byte.class.getName(), byteColumnConverter);

        CharColumnConverter charColumnConverter = new CharColumnConverter();
        columnConverter_map.put(char.class.getName(), charColumnConverter);
        columnConverter_map.put(Character.class.getName(), charColumnConverter);

        DateColumnConverter dateColumnConverter = new DateColumnConverter();
        columnConverter_map.put(Date.class.getName(), dateColumnConverter);

        DoubleColumnConverter doubleColumnConverter = new DoubleColumnConverter();
        columnConverter_map.put(double.class.getName(), doubleColumnConverter);
        columnConverter_map.put(Double.class.getName(), doubleColumnConverter);

        FloatColumnConverter floatColumnConverter = new FloatColumnConverter();
        columnConverter_map.put(float.class.getName(), floatColumnConverter);
        columnConverter_map.put(Float.class.getName(), floatColumnConverter);

        IntegerColumnConverter integerColumnConverter = new IntegerColumnConverter();
        columnConverter_map.put(int.class.getName(), integerColumnConverter);
        columnConverter_map.put(Integer.class.getName(), integerColumnConverter);

        LongColumnConverter longColumnConverter = new LongColumnConverter();
        columnConverter_map.put(long.class.getName(), longColumnConverter);
        columnConverter_map.put(Long.class.getName(), longColumnConverter);

        ShortColumnConverter shortColumnConverter = new ShortColumnConverter();
        columnConverter_map.put(short.class.getName(), shortColumnConverter);
        columnConverter_map.put(Short.class.getName(), shortColumnConverter);

        SqlDateColumnConverter sqlDateColumnConverter = new SqlDateColumnConverter();
        columnConverter_map.put(java.sql.Date.class.getName(), sqlDateColumnConverter);

        StringColumnConverter stringColumnConverter = new StringColumnConverter();
        columnConverter_map.put(String.class.getName(), stringColumnConverter);
    }
    private ColumnConvertFactory() {
    }

    public static ColumnConverter getFieldConverter(Field field) {
        Class fieldClazz = field.getType();
        if (columnConverter_map.containsKey(fieldClazz.getName())) {
            return columnConverter_map.get(fieldClazz.getName());
        } else if (ColumnConverter.class.isAssignableFrom(fieldClazz)) {
            try {
                ColumnConverter columnConverter = (ColumnConverter) fieldClazz.newInstance();
                if (columnConverter != null) {
                    columnConverter_map.put(fieldClazz.getName(), columnConverter);
                }
                return columnConverter;
            } catch (Throwable e) {
            }
        }
        return null;
    }

    public static void registerClazzConverter(Class columnType, ColumnConverter columnConverter) {
        columnConverter_map.put(columnType.getName(), columnConverter);
    }

    public static boolean isSupportColumnConverter(Class columnType) {
        if (columnConverter_map.containsKey(columnType.getName())) {
            return true;
        } else if (ColumnConverter.class.isAssignableFrom(columnType)) {
            try {
                ColumnConverter columnConverter = (ColumnConverter) columnType.newInstance();
                if (columnConverter != null) {
                    columnConverter_map.put(columnType.getName(), columnConverter);
                }
                return columnConverter == null;
            } catch (Throwable e) {
            }
        }
        return false;
    }

}
