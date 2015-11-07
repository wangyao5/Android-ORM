package com.xydroid.dbutils.persistence.sqlite.query;


import android.database.Cursor;

public class CursorWrapper {
    private Cursor mCursor;
    private Class entity;

    public CursorWrapper(Cursor mCursor, Class entity) {
        this.mCursor = mCursor;
        this.entity = entity;
    }

    public Cursor getmCursor() {
        return mCursor;
    }

    public void setmCursor(Cursor mCursor) {
        this.mCursor = mCursor;
    }

    public Class getEntity() {
        return entity;
    }

    public void setEntity(Class entity) {
        this.entity = entity;
    }
}
