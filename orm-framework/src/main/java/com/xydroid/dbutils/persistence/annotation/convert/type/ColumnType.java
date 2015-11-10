package com.xydroid.dbutils.persistence.annotation.convert.type;

public enum ColumnType {
    INTEGER("INTEGER"), REAL("REAL"), TEXT("TEXT"), BLOB("BLOB");

    private String value;

    ColumnType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
